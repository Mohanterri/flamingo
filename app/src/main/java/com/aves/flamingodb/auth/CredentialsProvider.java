package com.aves.flamingodb.auth;

import com.google.android.gms.tasks.Task;
import com.aves.flamingodb.util.Listener;


public abstract class CredentialsProvider<T> {

  public abstract Task<String> getToken();

  public abstract void invalidateToken();

  public abstract void setChangeListener(Listener<T> changeListener);

  public abstract void removeChangeListener();
}
