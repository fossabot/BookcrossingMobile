/*
 *    Copyright  2019 Andrey Mukamolov
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.bookcrossing.mobile.presenters;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.bookcrossing.mobile.R;
import com.bookcrossing.mobile.modules.App;
import com.bookcrossing.mobile.util.Constants;
import com.bookcrossing.mobile.util.FirebaseWrapper;
import com.bookcrossing.mobile.util.SystemServicesWrapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * (c) 2016 Andrey Mukamolov aka fobo66 <fobo66@protonmail.com>
 * Created by fobo66 on 29.12.2016.
 */

public class BasePresenter<V extends MvpView> extends MvpPresenter<V> {
  private CompositeDisposable compositeSubscription = new CompositeDisposable();
  protected String city = "undefined";
  protected FirebaseWrapper firebaseWrapper;
  protected SystemServicesWrapper systemServicesWrapper;

  public BasePresenter() {
    firebaseWrapper = new FirebaseWrapper();
    systemServicesWrapper = new SystemServicesWrapper();
    App.getComponent().inject(firebaseWrapper);
    App.getComponent().inject(systemServicesWrapper);
  }

  protected void unsubscribeOnDestroy(@NonNull Disposable subscription) {
    compositeSubscription.add(subscription);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    compositeSubscription.clear();
  }

  protected DatabaseReference books() {
    return firebaseWrapper.getDatabase().getReference("books");
  }

  protected DatabaseReference stash() {
    return firebaseWrapper.getDatabase().getReference("stash").child(getUserId());
  }

  protected DatabaseReference acquiredBooks() {
    return firebaseWrapper.getDatabase().getReference("acquiredBooks").child(getUserId());
  }

  protected DatabaseReference places() {
    return firebaseWrapper.getDatabase().getReference("places");
  }

  protected DatabaseReference placesHistory(String key) {
    return firebaseWrapper.getDatabase().getReference("placesHistory").child(key);
  }

  private String getUserId() {
    if (isAuthenticated()) {
      return firebaseWrapper.getAuth().getCurrentUser().getUid();
    }

    return Constants.DEFAULT_USER;
  }

  public StorageReference resolveCover(String key) {
    return firebaseWrapper.getStorage().getReference(key + ".jpg");
  }

  public Uri buildBookUri(String key) {
    return new Uri.Builder().scheme("bookcrossing")
            .authority(Constants.PACKAGE_NAME)
            .path("book")
            .appendQueryParameter(Constants.EXTRA_KEY, key)
            .build();
  }

  protected String getCity() {
    return systemServicesWrapper.getPreferences().getString(Constants.EXTRA_CITY, getDefaultCity());
  }

  public String getDefaultCity() {
    return systemServicesWrapper.getPreferences()
            .getString(Constants.EXTRA_DEFAULT_CITY,
                    systemServicesWrapper.getApp().getString(R.string.default_city));
  }

  public Maybe<String> resolveUserCity() {
    return systemServicesWrapper.getLocationRepository().getLastKnownUserLocation()
            .flatMapMaybe(location -> systemServicesWrapper.getLocationRepository().resolveUserCity(location))
            .observeOn(AndroidSchedulers.mainThread());
  }

  public void saveCity(String city) {
    SharedPreferences.Editor editor = systemServicesWrapper.getPreferences().edit();
    editor.putString(Constants.EXTRA_CITY, city);
    editor.putString(Constants.EXTRA_DEFAULT_CITY, city);
    editor.apply();
  }

  public boolean isAuthenticated() {
    return firebaseWrapper.getAuth().getCurrentUser() != null;
  }
}
