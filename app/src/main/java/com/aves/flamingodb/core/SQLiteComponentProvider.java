// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.aves.flamingodb.core;

import com.aves.flamingodb.local.IndexBackfiller;
import com.aves.flamingodb.local.LocalSerializer;
import com.aves.flamingodb.local.LruDelegate;
import com.aves.flamingodb.local.LruGarbageCollector;
import com.aves.flamingodb.local.Persistence;
import com.aves.flamingodb.local.SQLitePersistence;
import com.aves.flamingodb.local.Scheduler;
import com.aves.flamingodb.remote.RemoteSerializer;

/** Provides all components needed for Firestore with SQLite persistence. */
public class SQLiteComponentProvider extends MemoryComponentProvider {

  @Override
  protected Scheduler createGarbageCollectionScheduler(Configuration configuration) {
    LruDelegate lruDelegate = ((SQLitePersistence) getPersistence()).getReferenceDelegate();
    LruGarbageCollector gc = lruDelegate.getGarbageCollector();
    return gc.newScheduler(configuration.getAsyncQueue(), getLocalStore());
  }

  @Override
  protected IndexBackfiller createIndexBackfiller(Configuration configuration) {
    return new IndexBackfiller(getPersistence(), configuration.getAsyncQueue(), getLocalStore());
  }

  @Override
  protected Persistence createPersistence(Configuration configuration) {
    LocalSerializer serializer =
        new LocalSerializer(new RemoteSerializer(configuration.getDatabaseInfo().getDatabaseId()));
    LruGarbageCollector.Params params =
        LruGarbageCollector.Params.WithCacheSizeBytes(
            configuration.getSettings().getCacheSizeBytes());
    return new SQLitePersistence(
        configuration.getContext(),
        configuration.getDatabaseInfo().getPersistenceKey(),
        configuration.getDatabaseInfo().getDatabaseId(),
        serializer,
        params);
  }
}
