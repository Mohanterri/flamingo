// Copyright 2022 Google LLC
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

package com.aves.flamingodb.local;

import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.mutation.FieldMask;
import javax.annotation.Nullable;

/** Represents a local view (overlay) of a document, and the fields that are locally mutated. */
public class OverlayedDocument {
  private Document overlayedDocument;
  @Nullable private FieldMask mutatedFields;

  OverlayedDocument(Document overlayedDocument, FieldMask mutatedFields) {
    this.overlayedDocument = overlayedDocument;
    this.mutatedFields = mutatedFields;
  }

  public Document getDocument() {
    return overlayedDocument;
  }

  /**
   * The fields that are locally mutated by patch mutations.
   *
   * <p>If the overlayed document is from set or delete mutations, returns null.
   *
   * <p>If there is no overlay (mutation) for the document, returns FieldMask.EMPTY.
   */
  public @Nullable FieldMask getMutatedFields() {
    return mutatedFields;
  }
}
