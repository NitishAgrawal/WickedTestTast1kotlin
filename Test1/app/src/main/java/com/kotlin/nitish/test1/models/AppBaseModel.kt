package com.kotlin.nitish.test1.models

import android.os.Parcelable

abstract class AppBaseModel : Parcelable {

    constructor()

    override fun describeContents(): Int {
        return 0
    }
}