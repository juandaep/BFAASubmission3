package com.example.submission3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailUser (
    var username_detail: String? = "",
    var avatar_detail: String? = "",
    var company_detail: String? = "",
    var location_detail: String? = "",
    var repository_detail: Int = 0,
    var followers_detail: Int = 0,
    var following_detail: Int = 0
): Parcelable