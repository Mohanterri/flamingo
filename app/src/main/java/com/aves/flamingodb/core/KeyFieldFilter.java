// Copyright 2019 Google LLC
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

import static com.aves.flamingodb.util.Assert.hardAssert;

import com.aves.flamingodb.model.Document;
import com.aves.flamingodb.model.DocumentKey;
import com.aves.flamingodb.model.FieldPath;
import com.aves.flamingodb.model.Values;
import com.google.firestore.v1.Value;

/** Filter that matches on key fields (specifically, '__name__'). */
public class KeyFieldFilter extends FieldFilter {
  private final DocumentKey key;

  KeyFieldFilter(FieldPath field, Operator operator, Value value) {
    super(field, operator, value);
    hardAssert(Values.isReferenceValue(value), "KeyFieldFilter expects a ReferenceValue");
    key = DocumentKey.fromName(getValue().getReferenceValue());
  }

  @Override
  public boolean matches(Document doc) {
    int comparator = doc.getKey().compareTo(key);
    return this.matchesComparison(comparator);
  }
}
