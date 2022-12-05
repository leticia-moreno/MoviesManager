package com.leticia.moviesmanager.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int?,
    val name: String,
    val releaseYear: String,
    val producer: String,
    val watched: String,
    val duration: String,
    val rating: String,
    val genre: String
) : Parcelable