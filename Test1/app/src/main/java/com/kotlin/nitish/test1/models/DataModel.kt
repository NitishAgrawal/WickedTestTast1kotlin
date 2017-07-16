package com.kotlin.nitish.test1.models

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by nitish on 7/12/2017.
 */
data class DataModel(var imagePath: Uri, var  userName: String, var  email: String,
                     var  phoneNumber: String, var  dob: String, var  gender: String) : AppBaseModel() {

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Uri::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    constructor() : this(Uri.EMPTY, "", "", "", "", "");
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(imagePath, flags)
        parcel.writeString(userName)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeString(dob)
        parcel.writeString(gender)
    }

    companion object CREATOR : Parcelable.Creator<DataModel> {
        override fun createFromParcel(parcel: Parcel): DataModel {
            return DataModel(parcel)
        }

        override fun newArray(size: Int): Array<DataModel?> {
            return arrayOfNulls(size)
        }
    }

}