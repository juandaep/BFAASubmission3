package com.example.consummerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var id: Int = 0,
    var username: String? = "",
    var avatar: String? = "",
): Parcelable