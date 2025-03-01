package com.bookcrossing.mobile.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties public class Coordinates implements Parcelable {
  public double lat;
  public double lng;

  public Coordinates() {
  }

  public Coordinates(LatLng latLng) {
    this.lat = latLng.latitude;

    this.lng = latLng.longitude;
  }

  protected Coordinates(Parcel in) {
    lat = in.readDouble();
    lng = in.readDouble();
  }

  public static final Creator<Coordinates> CREATOR = new Creator<Coordinates>() {
    @Override public Coordinates createFromParcel(Parcel in) {
      return new Coordinates(in);
    }

    @Override public Coordinates[] newArray(int size) {
      return new Coordinates[size];
    }
  };

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Coordinates that = (Coordinates) o;

    if (Double.compare(that.lat, lat) != 0) return false;
    return Double.compare(that.lng, lng) == 0;
  }

  @Override public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(lat);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(lng);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeDouble(lat);
    parcel.writeDouble(lng);
  }
}
