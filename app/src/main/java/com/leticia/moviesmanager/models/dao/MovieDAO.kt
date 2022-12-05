package com.leticia.moviesmanager.models.dao

import com.leticia.moviesmanager.models.Movie

interface MovieDAO {
    fun createMovie(movie: Movie): Int
    fun retrieveMovie(id: Int): Movie?
    fun retrieveMovies(): MutableList<Movie>
    fun updateMovie(movie: Movie): Int
    fun deleteMovie(id: Int): Int
}