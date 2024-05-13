// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/firebase/firestore/proto/target.proto

package com.aves.flamingodb.proto;

/**
 * <pre>
 * Global state tracked across all Targets, tracked separately to avoid the
 * need for extra indexes.
 * </pre>
 *
 * Protobuf type {@code firestore.client.TargetGlobal}
 */
public  final class TargetGlobal extends
    com.google.protobuf.GeneratedMessageLite<
        TargetGlobal, TargetGlobal.Builder> implements
    // @@protoc_insertion_point(message_implements:firestore.client.TargetGlobal)
    TargetGlobalOrBuilder {
  private TargetGlobal() {
  }
  public static final int HIGHEST_TARGET_ID_FIELD_NUMBER = 1;
  private int highestTargetId_;
  /**
   * <pre>
   * The highest numbered target id across all Targets.
   * See Target.target_id.
   * </pre>
   *
   * <code>int32 highest_target_id = 1;</code>
   * @return The highestTargetId.
   */
  @Override
  public int getHighestTargetId() {
    return highestTargetId_;
  }
  /**
   * <pre>
   * The highest numbered target id across all Targets.
   * See Target.target_id.
   * </pre>
   *
   * <code>int32 highest_target_id = 1;</code>
   * @param value The highestTargetId to set.
   */
  private void setHighestTargetId(int value) {
    
    highestTargetId_ = value;
  }
  /**
   * <pre>
   * The highest numbered target id across all Targets.
   * See Target.target_id.
   * </pre>
   *
   * <code>int32 highest_target_id = 1;</code>
   */
  private void clearHighestTargetId() {
    
    highestTargetId_ = 0;
  }

  public static final int HIGHEST_LISTEN_SEQUENCE_NUMBER_FIELD_NUMBER = 2;
  private long highestListenSequenceNumber_;
  /**
   * <pre>
   * The highest numbered last_listen_sequence_number across all Targets.
   * See Target.last_listen_sequence_number.
   * </pre>
   *
   * <code>int64 highest_listen_sequence_number = 2;</code>
   * @return The highestListenSequenceNumber.
   */
  @Override
  public long getHighestListenSequenceNumber() {
    return highestListenSequenceNumber_;
  }
  /**
   * <pre>
   * The highest numbered last_listen_sequence_number across all Targets.
   * See Target.last_listen_sequence_number.
   * </pre>
   *
   * <code>int64 highest_listen_sequence_number = 2;</code>
   * @param value The highestListenSequenceNumber to set.
   */
  private void setHighestListenSequenceNumber(long value) {
    
    highestListenSequenceNumber_ = value;
  }
  /**
   * <pre>
   * The highest numbered last_listen_sequence_number across all Targets.
   * See Target.last_listen_sequence_number.
   * </pre>
   *
   * <code>int64 highest_listen_sequence_number = 2;</code>
   */
  private void clearHighestListenSequenceNumber() {
    
    highestListenSequenceNumber_ = 0L;
  }

  public static final int LAST_REMOTE_SNAPSHOT_VERSION_FIELD_NUMBER = 3;
  private com.google.protobuf.Timestamp lastRemoteSnapshotVersion_;
  /**
   * <pre>
   * A global snapshot version representing the last consistent snapshot we
   * received from the backend. This is monotonically increasing and any
   * snapshots received from the backend prior to this version (e.g. for
   * targets resumed with a resume_token) should be suppressed (buffered) until
   * the backend has caught up to this snapshot_version again. This prevents
   * our cache from ever going backwards in time.
   * This is updated whenever our we get a TargetChange with a read_time and
   * empty target_ids.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_remote_snapshot_version = 3;</code>
   */
  @Override
  public boolean hasLastRemoteSnapshotVersion() {
    return lastRemoteSnapshotVersion_ != null;
  }
  /**
   * <pre>
   * A global snapshot version representing the last consistent snapshot we
   * received from the backend. This is monotonically increasing and any
   * snapshots received from the backend prior to this version (e.g. for
   * targets resumed with a resume_token) should be suppressed (buffered) until
   * the backend has caught up to this snapshot_version again. This prevents
   * our cache from ever going backwards in time.
   * This is updated whenever our we get a TargetChange with a read_time and
   * empty target_ids.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_remote_snapshot_version = 3;</code>
   */
  @Override
  public com.google.protobuf.Timestamp getLastRemoteSnapshotVersion() {
    return lastRemoteSnapshotVersion_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : lastRemoteSnapshotVersion_;
  }
  /**
   * <pre>
   * A global snapshot version representing the last consistent snapshot we
   * received from the backend. This is monotonically increasing and any
   * snapshots received from the backend prior to this version (e.g. for
   * targets resumed with a resume_token) should be suppressed (buffered) until
   * the backend has caught up to this snapshot_version again. This prevents
   * our cache from ever going backwards in time.
   * This is updated whenever our we get a TargetChange with a read_time and
   * empty target_ids.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_remote_snapshot_version = 3;</code>
   */
  private void setLastRemoteSnapshotVersion(com.google.protobuf.Timestamp value) {
    value.getClass();
  lastRemoteSnapshotVersion_ = value;
    
    }
  /**
   * <pre>
   * A global snapshot version representing the last consistent snapshot we
   * received from the backend. This is monotonically increasing and any
   * snapshots received from the backend prior to this version (e.g. for
   * targets resumed with a resume_token) should be suppressed (buffered) until
   * the backend has caught up to this snapshot_version again. This prevents
   * our cache from ever going backwards in time.
   * This is updated whenever our we get a TargetChange with a read_time and
   * empty target_ids.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_remote_snapshot_version = 3;</code>
   */
  @SuppressWarnings({"ReferenceEquality"})
  private void mergeLastRemoteSnapshotVersion(com.google.protobuf.Timestamp value) {
    value.getClass();
  if (lastRemoteSnapshotVersion_ != null &&
        lastRemoteSnapshotVersion_ != com.google.protobuf.Timestamp.getDefaultInstance()) {
      lastRemoteSnapshotVersion_ =
        com.google.protobuf.Timestamp.newBuilder(lastRemoteSnapshotVersion_).mergeFrom(value).buildPartial();
    } else {
      lastRemoteSnapshotVersion_ = value;
    }
    
  }
  /**
   * <pre>
   * A global snapshot version representing the last consistent snapshot we
   * received from the backend. This is monotonically increasing and any
   * snapshots received from the backend prior to this version (e.g. for
   * targets resumed with a resume_token) should be suppressed (buffered) until
   * the backend has caught up to this snapshot_version again. This prevents
   * our cache from ever going backwards in time.
   * This is updated whenever our we get a TargetChange with a read_time and
   * empty target_ids.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_remote_snapshot_version = 3;</code>
   */
  private void clearLastRemoteSnapshotVersion() {  lastRemoteSnapshotVersion_ = null;
    
  }

  public static final int TARGET_COUNT_FIELD_NUMBER = 4;
  private int targetCount_;
  /**
   * <pre>
   * On platforms that need it, holds the number of targets persisted.
   * </pre>
   *
   * <code>int32 target_count = 4;</code>
   * @return The targetCount.
   */
  @Override
  public int getTargetCount() {
    return targetCount_;
  }
  /**
   * <pre>
   * On platforms that need it, holds the number of targets persisted.
   * </pre>
   *
   * <code>int32 target_count = 4;</code>
   * @param value The targetCount to set.
   */
  private void setTargetCount(int value) {
    
    targetCount_ = value;
  }
  /**
   * <pre>
   * On platforms that need it, holds the number of targets persisted.
   * </pre>
   *
   * <code>int32 target_count = 4;</code>
   */
  private void clearTargetCount() {
    
    targetCount_ = 0;
  }

  public static com.google.firebase.firestore.proto.TargetGlobal parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.google.firebase.firestore.proto.TargetGlobal parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.google.firebase.firestore.proto.TargetGlobal parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.google.firebase.firestore.proto.TargetGlobal parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.google.firebase.firestore.proto.TargetGlobal parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.google.firebase.firestore.proto.TargetGlobal parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.google.firebase.firestore.proto.TargetGlobal parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.google.firebase.firestore.proto.TargetGlobal parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.google.firebase.firestore.proto.TargetGlobal parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static com.google.firebase.firestore.proto.TargetGlobal parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.google.firebase.firestore.proto.TargetGlobal parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.google.firebase.firestore.proto.TargetGlobal parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }
  public static Builder newBuilder(com.google.firebase.firestore.proto.TargetGlobal prototype) {
    return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   * <pre>
   * Global state tracked across all Targets, tracked separately to avoid the
   * need for extra indexes.
   * </pre>
   *
   * Protobuf type {@code firestore.client.TargetGlobal}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        com.google.firebase.firestore.proto.TargetGlobal, Builder> implements
      // @@protoc_insertion_point(builder_implements:firestore.client.TargetGlobal)
      com.google.firebase.firestore.proto.TargetGlobalOrBuilder {
    // Construct using com.google.firebase.firestore.proto.TargetGlobal.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <pre>
     * The highest numbered target id across all Targets.
     * See Target.target_id.
     * </pre>
     *
     * <code>int32 highest_target_id = 1;</code>
     * @return The highestTargetId.
     */
    @Override
    public int getHighestTargetId() {
      return instance.getHighestTargetId();
    }
    /**
     * <pre>
     * The highest numbered target id across all Targets.
     * See Target.target_id.
     * </pre>
     *
     * <code>int32 highest_target_id = 1;</code>
     * @param value The highestTargetId to set.
     * @return This builder for chaining.
     */
    public Builder setHighestTargetId(int value) {
      copyOnWrite();
      instance.setHighestTargetId(value);
      return this;
    }
    /**
     * <pre>
     * The highest numbered target id across all Targets.
     * See Target.target_id.
     * </pre>
     *
     * <code>int32 highest_target_id = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearHighestTargetId() {
      copyOnWrite();
      instance.clearHighestTargetId();
      return this;
    }

    /**
     * <pre>
     * The highest numbered last_listen_sequence_number across all Targets.
     * See Target.last_listen_sequence_number.
     * </pre>
     *
     * <code>int64 highest_listen_sequence_number = 2;</code>
     * @return The highestListenSequenceNumber.
     */
    @Override
    public long getHighestListenSequenceNumber() {
      return instance.getHighestListenSequenceNumber();
    }
    /**
     * <pre>
     * The highest numbered last_listen_sequence_number across all Targets.
     * See Target.last_listen_sequence_number.
     * </pre>
     *
     * <code>int64 highest_listen_sequence_number = 2;</code>
     * @param value The highestListenSequenceNumber to set.
     * @return This builder for chaining.
     */
    public Builder setHighestListenSequenceNumber(long value) {
      copyOnWrite();
      instance.setHighestListenSequenceNumber(value);
      return this;
    }
    /**
     * <pre>
     * The highest numbered last_listen_sequence_number across all Targets.
     * See Target.last_listen_sequence_number.
     * </pre>
     *
     * <code>int64 highest_listen_sequence_number = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearHighestListenSequenceNumber() {
      copyOnWrite();
      instance.clearHighestListenSequenceNumber();
      return this;
    }

    /**
     * <pre>
     * A global snapshot version representing the last consistent snapshot we
     * received from the backend. This is monotonically increasing and any
     * snapshots received from the backend prior to this version (e.g. for
     * targets resumed with a resume_token) should be suppressed (buffered) until
     * the backend has caught up to this snapshot_version again. This prevents
     * our cache from ever going backwards in time.
     * This is updated whenever our we get a TargetChange with a read_time and
     * empty target_ids.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp last_remote_snapshot_version = 3;</code>
     */
    @Override
    public boolean hasLastRemoteSnapshotVersion() {
      return instance.hasLastRemoteSnapshotVersion();
    }
    /**
     * <pre>
     * A global snapshot version representing the last consistent snapshot we
     * received from the backend. This is monotonically increasing and any
     * snapshots received from the backend prior to this version (e.g. for
     * targets resumed with a resume_token) should be suppressed (buffered) until
     * the backend has caught up to this snapshot_version again. This prevents
     * our cache from ever going backwards in time.
     * This is updated whenever our we get a TargetChange with a read_time and
     * empty target_ids.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp last_remote_snapshot_version = 3;</code>
     */
    @Override
    public com.google.protobuf.Timestamp getLastRemoteSnapshotVersion() {
      return instance.getLastRemoteSnapshotVersion();
    }
    /**
     * <pre>
     * A global snapshot version representing the last consistent snapshot we
     * received from the backend. This is monotonically increasing and any
     * snapshots received from the backend prior to this version (e.g. for
     * targets resumed with a resume_token) should be suppressed (buffered) until
     * the backend has caught up to this snapshot_version again. This prevents
     * our cache from ever going backwards in time.
     * This is updated whenever our we get a TargetChange with a read_time and
     * empty target_ids.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp last_remote_snapshot_version = 3;</code>
     */
    public Builder setLastRemoteSnapshotVersion(com.google.protobuf.Timestamp value) {
      copyOnWrite();
      instance.setLastRemoteSnapshotVersion(value);
      return this;
      }
    /**
     * <pre>
     * A global snapshot version representing the last consistent snapshot we
     * received from the backend. This is monotonically increasing and any
     * snapshots received from the backend prior to this version (e.g. for
     * targets resumed with a resume_token) should be suppressed (buffered) until
     * the backend has caught up to this snapshot_version again. This prevents
     * our cache from ever going backwards in time.
     * This is updated whenever our we get a TargetChange with a read_time and
     * empty target_ids.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp last_remote_snapshot_version = 3;</code>
     */
    public Builder setLastRemoteSnapshotVersion(
        com.google.protobuf.Timestamp.Builder builderForValue) {
      copyOnWrite();
      instance.setLastRemoteSnapshotVersion(builderForValue.build());
      return this;
    }
    /**
     * <pre>
     * A global snapshot version representing the last consistent snapshot we
     * received from the backend. This is monotonically increasing and any
     * snapshots received from the backend prior to this version (e.g. for
     * targets resumed with a resume_token) should be suppressed (buffered) until
     * the backend has caught up to this snapshot_version again. This prevents
     * our cache from ever going backwards in time.
     * This is updated whenever our we get a TargetChange with a read_time and
     * empty target_ids.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp last_remote_snapshot_version = 3;</code>
     */
    public Builder mergeLastRemoteSnapshotVersion(com.google.protobuf.Timestamp value) {
      copyOnWrite();
      instance.mergeLastRemoteSnapshotVersion(value);
      return this;
    }
    /**
     * <pre>
     * A global snapshot version representing the last consistent snapshot we
     * received from the backend. This is monotonically increasing and any
     * snapshots received from the backend prior to this version (e.g. for
     * targets resumed with a resume_token) should be suppressed (buffered) until
     * the backend has caught up to this snapshot_version again. This prevents
     * our cache from ever going backwards in time.
     * This is updated whenever our we get a TargetChange with a read_time and
     * empty target_ids.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp last_remote_snapshot_version = 3;</code>
     */
    public Builder clearLastRemoteSnapshotVersion() {  copyOnWrite();
      instance.clearLastRemoteSnapshotVersion();
      return this;
    }

    /**
     * <pre>
     * On platforms that need it, holds the number of targets persisted.
     * </pre>
     *
     * <code>int32 target_count = 4;</code>
     * @return The targetCount.
     */
    @Override
    public int getTargetCount() {
      return instance.getTargetCount();
    }
    /**
     * <pre>
     * On platforms that need it, holds the number of targets persisted.
     * </pre>
     *
     * <code>int32 target_count = 4;</code>
     * @param value The targetCount to set.
     * @return This builder for chaining.
     */
    public Builder setTargetCount(int value) {
      copyOnWrite();
      instance.setTargetCount(value);
      return this;
    }
    /**
     * <pre>
     * On platforms that need it, holds the number of targets persisted.
     * </pre>
     *
     * <code>int32 target_count = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearTargetCount() {
      copyOnWrite();
      instance.clearTargetCount();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:firestore.client.TargetGlobal)
  }
  @Override
  @SuppressWarnings({"unchecked", "fallthrough"})
  protected final Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      Object arg0, Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new com.google.firebase.firestore.proto.TargetGlobal();
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case BUILD_MESSAGE_INFO: {
          Object[] objects = new Object[] {
            "highestTargetId_",
            "highestListenSequenceNumber_",
            "lastRemoteSnapshotVersion_",
            "targetCount_",
          };
          String info =
              "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0002\u0002" +
              "\u0003\t\u0004\u0004";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
      }
      // fall through
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        com.google.protobuf.Parser<com.google.firebase.firestore.proto.TargetGlobal> parser = PARSER;
        if (parser == null) {
          synchronized (com.google.firebase.firestore.proto.TargetGlobal.class) {
            parser = PARSER;
            if (parser == null) {
              parser =
                  new DefaultInstanceBasedParser<com.google.firebase.firestore.proto.TargetGlobal>(
                      DEFAULT_INSTANCE);
              PARSER = parser;
            }
          }
        }
        return parser;
    }
    case GET_MEMOIZED_IS_INITIALIZED: {
      return (byte) 1;
    }
    case SET_MEMOIZED_IS_INITIALIZED: {
      return null;
    }
    }
    throw new UnsupportedOperationException();
  }


  // @@protoc_insertion_point(class_scope:firestore.client.TargetGlobal)
  private static final com.google.firebase.firestore.proto.TargetGlobal DEFAULT_INSTANCE;
  static {
    TargetGlobal defaultInstance = new TargetGlobal();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
      TargetGlobal.class, defaultInstance);
  }

  public static com.google.firebase.firestore.proto.TargetGlobal getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<TargetGlobal> PARSER;

  public static com.google.protobuf.Parser<TargetGlobal> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}

