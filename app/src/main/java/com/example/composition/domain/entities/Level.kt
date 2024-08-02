package com.example.composition.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Level: Parcelable {
    TEST, EASY, NORMAL, HARD
}