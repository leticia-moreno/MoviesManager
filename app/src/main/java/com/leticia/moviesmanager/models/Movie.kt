package com.leticia.moviesmanager.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id : Int?,
    val name: String,
    val releaseYear: Int,
    val producer: String,
    val watched : Boolean,
    val duration: Int,
    val score: Int?,
    val genre : String
) : Parcelable