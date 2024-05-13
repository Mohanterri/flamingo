package com.aves.flamingodb;

import static com.aves.flamingodb.util.Assert.hardAssert;
import static com.aves.flamingodb.util.Preconditions.checkNotNull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.annotations.PreviewApi;
import com.google.firebase.appcheck.interop.InteropAppCheckTokenProvider;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.emulators.EmulatedServiceSettings;
import com.aves.flamingodb.FirebaseFirestoreException;
import com.aves.flamingodb.FirebaseFirestoreException.Code;
import com.aves.flamingodb.FirebaseFirestoreSettings;
import com.aves.flamingodb.FirestoreMultiDbComponent;
import com.aves.flamingodb.ListenerRegistration;
import com.aves.flamingodb.LoadBundleTask;
import com.aves.flamingodb.PersistentCacheIndexManager;
import com.aves.flamingodb.PersistentCacheSettings;
import com.aves.flamingodb.Query;
import com.aves.flamingodb.Transaction;
import com.aves.flamingodb.TransactionOptions;
import com.aves.flamingodb.UserDataReader;
import com.aves.flamingodb.WriteBatch;
import com.aves.flamingodb.auth.CredentialsProvider;
import com.aves.flamingodb.auth.FirebaseAppCheckTokenProvider;
import com.aves.flamingodb.auth.FirebaseAuthCredentialsProvider;
import com.aves.flamingodb.auth.User;
import com.aves.flamingodb.core.ActivityScope;
import com.aves.flamingodb.core.AsyncEventListener;
import com.aves.flamingodb.core.DatabaseInfo;
import com.aves.flamingodb.core.FirestoreClient;
import com.aves.flamingodb.local.SQLitePersistence;
import com.aves.flamingodb.model.DatabaseId;
import com.aves.flamingodb.model.FieldIndex;
import com.aves.flamingodb.model.FieldPath;
import com.aves.flamingodb.model.ResourcePath;
import com.aves.flamingodb.remote.FirestoreChannel;
import com.aves.flamingodb.remote.GrpcMetadataProvider;
import com.aves.flamingodb.util.AsyncQueue;
import com.aves.flamingodb.util.ByteBufferInputStream;
import com.aves.flamingodb.util.Executors;
import com.aves.flamingodb.util.Function;
import com.aves.flamingodb.util.Logger;
import com.aves.flamingodb.util.Logger.Level;
import com.aves.flamingodb.util.Preconditions;
import com.google.firebase.inject.Deferred;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseFirestore {

  public interface InstanceRegistry {
    void remove(@NonNull String databaseId);
  }

  private static final String TAG = "FirebaseFirestore";
  private final Context context;
  private final DatabaseId databaseId;
  private final String persistenceKey;
  private final CredentialsProvider<User> authProvider;
  private final CredentialsProvider<String> appCheckProvider;
  private final AsyncQueue asyncQueue;
  private final FirebaseApp firebaseApp;
  private final UserDataReader userDataReader;
  // When user requests to terminate, use this to notify `FirestoreMultiDbComponent` to deregister
  // this instance.
  private final InstanceRegistry instanceRegistry;
  @Nullable private EmulatedServiceSettings emulatorSettings;
  private FirebaseFirestoreSettings settings;
  private volatile FirestoreClient client;
  private final GrpcMetadataProvider metadataProvider;

  @Nullable private PersistentCacheIndexManager persistentCacheIndexManager;

  @NonNull
  private static FirebaseApp getDefaultFirebaseApp() {
    FirebaseApp app = FirebaseApp.getInstance();
    if (app == null) {
      throw new IllegalStateException("You must call FirebaseApp.initializeApp first.");
    }
    return app;
  }

  @NonNull
  public static FirebaseFirestore getInstance() {
    return getInstance(getDefaultFirebaseApp(), DatabaseId.DEFAULT_DATABASE_ID);
  }

  @NonNull
  public static FirebaseFirestore getInstance(@NonNull FirebaseApp app) {
    return getInstance(app, DatabaseId.DEFAULT_DATABASE_ID);
  }

  @NonNull
  public static FirebaseFirestore getInstance(@NonNull String database) {
    return getInstance(getDefaultFirebaseApp(), database);
  }

  @NonNull
  public static FirebaseFirestore getInstance(@NonNull FirebaseApp app, @NonNull String database) {
    checkNotNull(app, "Provided FirebaseApp must not be null.");
    checkNotNull(database, "Provided database name must not be null.");
    FirestoreMultiDbComponent component = app.get(FirestoreMultiDbComponent.class);
    checkNotNull(component, "Firestore component is not present.");
    return component.get(database);
  }

  @NonNull
  static FirebaseFirestore newInstance(
      @NonNull Context context,
      @NonNull FirebaseApp app,
      @NonNull Deferred<InternalAuthProvider> deferredAuthProvider,
      @NonNull Deferred<InteropAppCheckTokenProvider> deferredAppCheckTokenProvider,
      @NonNull String database,
      @NonNull InstanceRegistry instanceRegistry,
      @Nullable GrpcMetadataProvider metadataProvider) {
    String projectId = app.getOptions().getProjectId();
    if (projectId == null) {
      throw new IllegalArgumentException("FirebaseOptions.getProjectId() cannot be null");
    }
    DatabaseId databaseId = DatabaseId.forDatabase(projectId, database);

    AsyncQueue queue = new AsyncQueue();

    CredentialsProvider<User> authProvider =
        new FirebaseAuthCredentialsProvider(deferredAuthProvider);
    CredentialsProvider<String> appCheckProvider =
        new FirebaseAppCheckTokenProvider(deferredAppCheckTokenProvider);

    // Firestore uses a different database for each app name. Note that we don't use
    // app.getPersistenceKey() here because it includes the application ID which is related
    // to the project ID. We already include the project ID when resolving the database,
    // so there is no need to include it in the persistence key.
    String persistenceKey = app.getName();

    FirebaseFirestore firestore =
        new FirebaseFirestore(
            context,
            databaseId,
            persistenceKey,
            authProvider,
            appCheckProvider,
            queue,
            app,
            instanceRegistry,
            metadataProvider);
    return firestore;
  }

  @VisibleForTesting
  FirebaseFirestore(
      Context context,
      DatabaseId databaseId,
      String persistenceKey,
      CredentialsProvider<User> authProvider,
      CredentialsProvider<String> appCheckProvider,
      AsyncQueue asyncQueue,
      @Nullable FirebaseApp firebaseApp,
      InstanceRegistry instanceRegistry,
      @Nullable GrpcMetadataProvider metadataProvider) {
    this.context = checkNotNull(context);
    this.databaseId = checkNotNull(checkNotNull(databaseId));
    this.userDataReader = new UserDataReader(databaseId);
    this.persistenceKey = checkNotNull(persistenceKey);
    this.authProvider = checkNotNull(authProvider);
    this.appCheckProvider = checkNotNull(appCheckProvider);
    this.asyncQueue = checkNotNull(asyncQueue);
    // NOTE: We allow firebaseApp to be null in tests only.
    this.firebaseApp = firebaseApp;
    this.instanceRegistry = instanceRegistry;
    this.metadataProvider = metadataProvider;

    this.settings = new FirebaseFirestoreSettings.Builder().build();
  }

  /** Returns the settings used by this {@code FirebaseFirestore} object. */
  @NonNull
  public FirebaseFirestoreSettings getFirestoreSettings() {
    return settings;
  }

  /**
   * Sets any custom settings used to configure this {@code FirebaseFirestore} object. This method
   * can only be called before calling any other methods on this object.
   */
  public void setFirestoreSettings(@NonNull FirebaseFirestoreSettings settings) {
    settings = mergeEmulatorSettings(settings, this.emulatorSettings);

    synchronized (databaseId) {
      checkNotNull(settings, "Provided settings must not be null.");

      // As a special exception, don't throw if the same settings are passed repeatedly. This
      // should make it simpler to get a Firestore instance in an activity.
      if (client != null && !this.settings.equals(settings)) {
        throw new IllegalStateException(
            "FirebaseFirestore has already been started and its settings can no longer be changed. "
                + "You can only call setFirestoreSettings() before calling any other methods on a "
                + "FirebaseFirestore object.");
      }

      this.settings = settings;
    }
  }

  /**
   * Modifies this FirebaseDatabase instance to communicate with the Cloud Firestore emulator.
   *
   * <p>Note: Call this method before using the instance to do any database operations.
   *
   * @param host the emulator host (for example, 10.0.2.2)
   * @param port the emulator port (for example, 8080)
   */
  public void useEmulator(@NonNull String host, int port) {
    if (this.client != null) {
      throw new IllegalStateException(
          "Cannot call useEmulator() after instance has already been initialized.");
    }

    this.emulatorSettings = new EmulatedServiceSettings(host, port);
    this.settings = mergeEmulatorSettings(this.settings, this.emulatorSettings);
  }

  private void ensureClientConfigured() {
    if (client != null) {
      return;
    }

    synchronized (databaseId) {
      if (client != null) {
        return;
      }
      DatabaseInfo databaseInfo =
          new DatabaseInfo(databaseId, persistenceKey, settings.getHost(), settings.isSslEnabled());

      client =
          new FirestoreClient(
              context,
              databaseInfo,
              settings,
              authProvider,
              appCheckProvider,
              asyncQueue,
              metadataProvider);
    }
  }

  private FirebaseFirestoreSettings mergeEmulatorSettings(
      @NonNull FirebaseFirestoreSettings settings,
      @Nullable EmulatedServiceSettings emulatorSettings) {
    if (emulatorSettings == null) {
      return settings;
    }

    if (!FirebaseFirestoreSettings.DEFAULT_HOST.equals(settings.getHost())) {
      Logger.warn(
          TAG,
          "Host has been set in FirebaseFirestoreSettings and useEmulator, emulator host will be used.");
    }

    return new FirebaseFirestoreSettings.Builder(settings)
        .setHost(emulatorSettings.getHost() + ":" + emulatorSettings.getPort())
        .setSslEnabled(false)
        .build();
  }

  /** Returns the FirebaseApp instance to which this {@code FirebaseFirestore} belongs. */
  @NonNull
  public FirebaseApp getApp() {
    return firebaseApp;
  }

  /**
   * Configures indexing for local query execution. Any previous index configuration is overridden.
   * The Task resolves once the index configuration has been persisted.
   *
   * <p>The index entries themselves are created asynchronously. You can continue to use queries
   * that require indexing even if the indices are not yet available. Query execution will
   * automatically start using the index once the index entries have been written.
   *
   * <p>The method accepts the JSON format exported by the Firebase CLI (`firebase
   * firestore:indexes`). If the JSON format is invalid, this method throws an exception.
   *
   * @param json The JSON format exported by the Firebase CLI.
   * @return A task that resolves once all indices are successfully configured.
   * @throws IllegalArgumentException if the JSON format is invalid
   * @deprecated Instead of creating cache indexes manually, consider using {@link
   *     PersistentCacheIndexManager#enableIndexAutoCreation()} to let the SDK decide whether to
   *     create cache indexes for queries running locally.
   */
  @Deprecated
  @PreviewApi
  @NonNull
  public Task<Void> setIndexConfiguration(@NonNull String json) {
    ensureClientConfigured();
    Preconditions.checkState(
        settings.isPersistenceEnabled(), "Cannot enable indexes when persistence is disabled");

    List<FieldIndex> parsedIndexes = new ArrayList<>();

    try {
      JSONObject jsonObject = new JSONObject(json);

      if (jsonObject.has("indexes")) {
        JSONArray indexes = jsonObject.getJSONArray("indexes");
        for (int i = 0; i < indexes.length(); ++i) {
          JSONObject definition = indexes.getJSONObject(i);
          String collectionGroup = definition.getString("collectionGroup");
          List<FieldIndex.Segment> segments = new ArrayList<>();

          JSONArray fields = definition.optJSONArray("fields");
          for (int f = 0; fields != null && f < fields.length(); ++f) {
            JSONObject field = fields.getJSONObject(f);
            FieldPath fieldPath = FieldPath.fromServerFormat(field.getString("fieldPath"));
            if ("CONTAINS".equals(field.optString("arrayConfig"))) {
              segments.add(FieldIndex.Segment.create(fieldPath, FieldIndex.Segment.Kind.CONTAINS));
            } else if ("ASCENDING".equals(field.optString("order"))) {
              segments.add(FieldIndex.Segment.create(fieldPath, FieldIndex.Segment.Kind.ASCENDING));
            } else {
              segments.add(
                  FieldIndex.Segment.create(fieldPath, FieldIndex.Segment.Kind.DESCENDING));
            }
          }

          parsedIndexes.add(
              FieldIndex.create(
                  FieldIndex.UNKNOWN_ID, collectionGroup, segments, FieldIndex.INITIAL_STATE));
        }
      }
    } catch (JSONException e) {
      throw new IllegalArgumentException("Failed to parse index configuration", e);
    }

    return client.configureFieldIndexes(parsedIndexes);
  }

  @Nullable
  public synchronized PersistentCacheIndexManager getPersistentCacheIndexManager() {
    ensureClientConfigured();
    if (persistentCacheIndexManager == null
        && (settings.isPersistenceEnabled()
            || settings.getCacheSettings() instanceof PersistentCacheSettings)) {
      persistentCacheIndexManager = new PersistentCacheIndexManager(client);
    }
    return persistentCacheIndexManager;
  }

  @NonNull
  public CollectionReference collection(@NonNull String collectionPath) {
    checkNotNull(collectionPath, "Provided collection path must not be null.");
    ensureClientConfigured();
    return new CollectionReference(ResourcePath.fromString(collectionPath), this);
  }

  @NonNull
  public DocumentReference document(@NonNull String documentPath) {
    checkNotNull(documentPath, "Provided document path must not be null.");
    ensureClientConfigured();
    return DocumentReference.forPath(ResourcePath.fromString(documentPath), this);
  }

  @NonNull
  public Query collectionGroup(@NonNull String collectionId) {
    checkNotNull(collectionId, "Provided collection ID must not be null.");
    if (collectionId.contains("/")) {
      throw new IllegalArgumentException(
          String.format(
              "Invalid collectionId '%s'. Collection IDs must not contain '/'.", collectionId));
    }

    ensureClientConfigured();
    return new Query(
        new com.aves.flamingodb.core.Query(ResourcePath.EMPTY, collectionId), this);
  }

  private <ResultT> Task<ResultT> runTransaction(
          TransactionOptions options, Transaction.Function<ResultT> updateFunction, Executor executor) {
    ensureClientConfigured();

    Function<com.aves.flamingodb.core.Transaction, Task<ResultT>> wrappedUpdateFunction =
        internalTransaction ->
            Tasks.call(
                executor,
                () ->
                    updateFunction.apply(
                        new Transaction(internalTransaction, FirebaseFirestore.this)));

    return client.transaction(options, wrappedUpdateFunction);
  }

  @NonNull
  public <TResult> Task<TResult> runTransaction(
      @NonNull Transaction.Function<TResult> updateFunction) {
    return runTransaction(TransactionOptions.DEFAULT, updateFunction);
  }

  @NonNull
  public <TResult> Task<TResult> runTransaction(
      @NonNull TransactionOptions options, @NonNull Transaction.Function<TResult> updateFunction) {
    checkNotNull(updateFunction, "Provided transaction update function must not be null.");
    return runTransaction(
        options,
        updateFunction,
        com.aves.flamingodb.core.Transaction.getDefaultExecutor());
  }

  @NonNull
  public WriteBatch batch() {
    ensureClientConfigured();

    return new WriteBatch(this);
  }

  @NonNull
  public Task<Void> runBatch(@NonNull WriteBatch.Function batchFunction) {
    WriteBatch batch = batch();
    batchFunction.apply(batch);
    return batch.commit();
  }

  @NonNull
  public Task<Void> terminate() {
    instanceRegistry.remove(this.getDatabaseId().getDatabaseId());

    // The client must be initialized to ensure that all subsequent API usage throws an exception.
    this.ensureClientConfigured();
    return client.terminate();
  }

  @NonNull
  public Task<Void> waitForPendingWrites() {
    ensureClientConfigured();
    return client.waitForPendingWrites();
  }

  @VisibleForTesting
  AsyncQueue getAsyncQueue() {
    return asyncQueue;
  }

  @NonNull
  public Task<Void> enableNetwork() {
    ensureClientConfigured();
    return client.enableNetwork();
  }

  @NonNull
  public Task<Void> disableNetwork() {
    ensureClientConfigured();
    return client.disableNetwork();
  }

  public static void setLoggingEnabled(boolean loggingEnabled) {
    if (loggingEnabled) {
      Logger.setLogLevel(Level.DEBUG);
    } else {
      Logger.setLogLevel(Level.WARN);
    }
  }

  @NonNull
  public Task<Void> clearPersistence() {
    final TaskCompletionSource<Void> source = new TaskCompletionSource<>();
    asyncQueue.enqueueAndForgetEvenAfterShutdown(
        () -> {
          try {
            if (client != null && !client.isTerminated()) {
              throw new FirebaseFirestoreException(
                  "Persistence cannot be cleared while the firestore instance is running.",
                  Code.FAILED_PRECONDITION);
            }
            SQLitePersistence.clearPersistence(context, databaseId, persistenceKey);
            source.setResult(null);
          } catch (FirebaseFirestoreException e) {
            source.setException(e);
          }
        });
    return source.getTask();
  }

  @NonNull
  public ListenerRegistration addSnapshotsInSyncListener(@NonNull Runnable runnable) {
    return addSnapshotsInSyncListener(Executors.DEFAULT_CALLBACK_EXECUTOR, runnable);
  }
  @NonNull
  public ListenerRegistration addSnapshotsInSyncListener(
      @NonNull Activity activity, @NonNull Runnable runnable) {
    return addSnapshotsInSyncListener(Executors.DEFAULT_CALLBACK_EXECUTOR, activity, runnable);
  }

  @NonNull
  public ListenerRegistration addSnapshotsInSyncListener(
      @NonNull Executor executor, @NonNull Runnable runnable) {
    return addSnapshotsInSyncListener(executor, null, runnable);
  }

  @NonNull
  public LoadBundleTask loadBundle(@NonNull InputStream bundleData) {
    ensureClientConfigured();
    LoadBundleTask resultTask = new LoadBundleTask();
    client.loadBundle(bundleData, resultTask);
    return resultTask;
  }

  @NonNull
  public LoadBundleTask loadBundle(@NonNull byte[] bundleData) {
    return loadBundle(new ByteArrayInputStream(bundleData));
  }

  @NonNull
  public LoadBundleTask loadBundle(@NonNull ByteBuffer bundleData) {
    return loadBundle(new ByteBufferInputStream(bundleData));
  }

  @SuppressLint("TaskMainThread")
  public @NonNull Task<Query> getNamedQuery(@NonNull String name) {
    ensureClientConfigured();
    return client
        .getNamedQuery(name)
        .continueWith(
            task -> {
              com.aves.flamingodb.core.Query query = task.getResult();
              if (query != null) {
                return new Query(query, FirebaseFirestore.this);
              } else {
                return null;
              }
            });
  }

  private ListenerRegistration addSnapshotsInSyncListener(
      Executor userExecutor, @Nullable Activity activity, @NonNull Runnable runnable) {
    ensureClientConfigured();
    EventListener<Void> eventListener =
        (Void v, FirebaseFirestoreException error) -> {
          hardAssert(error == null, "snapshots-in-sync listeners should never get errors.");
          runnable.run();
        };
    AsyncEventListener<Void> asyncListener =
        new AsyncEventListener<Void>(userExecutor, eventListener);
    client.addSnapshotsInSyncListener(asyncListener);
    return ActivityScope.bind(
        activity,
        () -> {
          asyncListener.mute();
          client.removeSnapshotsInSyncListener(asyncListener);
        });
  }

  FirestoreClient getClient() {
    return client;
  }

  DatabaseId getDatabaseId() {
    return databaseId;
  }

  UserDataReader getUserDataReader() {
    return userDataReader;
  }

  /**
   * Helper to validate a {@code DocumentReference}. Used by {@link WriteBatch} and {@link
   * Transaction}.
   */
  void validateReference(DocumentReference docRef) {
    checkNotNull(docRef, "Provided DocumentReference must not be null.");
    if (docRef.getFirestore() != this) {
      throw new IllegalArgumentException(
          "Provided document reference is from a different Cloud Firestore instance.");
    }
  }

  @Keep
  static void setClientLanguage(@NonNull String languageToken) {
    FirestoreChannel.setClientLanguage(languageToken);
  }
}
