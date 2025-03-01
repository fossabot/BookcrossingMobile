package com.bookcrossing.mobile.util.adapters;

import androidx.recyclerview.widget.RecyclerView;

import moxy.MvpDelegate;

/**
 * Created by fobo66 on 14.1.17.
 */

public abstract class MvpBaseAdapter extends RecyclerView.Adapter {
  private MvpDelegate<? extends MvpBaseAdapter> mMvpDelegate;
  private MvpDelegate<?> mParentDelegate;
  private String mChildId;

  public MvpBaseAdapter(MvpDelegate<?> parentDelegate, String childId) {
    mParentDelegate = parentDelegate;
    mChildId = childId;

    getMvpDelegate().onCreate();
  }

  public MvpDelegate getMvpDelegate() {
    if (mMvpDelegate == null) {
      mMvpDelegate = new MvpDelegate<>(this);
      mMvpDelegate.setParentDelegate(mParentDelegate, mChildId);
    }
    return mMvpDelegate;
  }
}