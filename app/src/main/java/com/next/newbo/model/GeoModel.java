package com.next.newbo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NeXT on 15-4-10.
 */
public class GeoModel implements Parcelable {

    public String type;
    public double[] coordinates = new double[]{0.0, 0.0};   //坐标

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeDoubleArray(coordinates);
    }

    public static final Creator<GeoModel> CREATOR = new Parcelable.Creator<GeoModel>() {

        @Override
        public GeoModel createFromParcel(Parcel parcel) {
            GeoModel geoModel = new GeoModel();
            geoModel.type = parcel.readString();
            parcel.readDoubleArray(geoModel.coordinates);
            return geoModel;
        }

        @Override
        public GeoModel[] newArray(int size) {
            return new GeoModel[size];
        }
    };

}
