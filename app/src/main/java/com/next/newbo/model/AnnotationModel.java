package com.next.newbo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NeXT on 15-4-10.
 */
public class AnnotationModel implements Parcelable {

    //TODO BL_VERSION ???
    public String newbo_version = "";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(newbo_version);
    }

    public static final Creator<AnnotationModel> CREATOR = new Creator<AnnotationModel>() {

        @Override
        public AnnotationModel createFromParcel(Parcel parcel) {
            AnnotationModel annotationModel = new AnnotationModel();
            annotationModel.newbo_version = parcel.readString();
            return annotationModel;
        }

        @Override
        public AnnotationModel[] newArray(int size) {
            return new AnnotationModel[size];
        }
    };

}
