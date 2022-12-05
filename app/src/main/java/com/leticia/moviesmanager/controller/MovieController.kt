package com.leticia.moviesmanager.controller

import com.leticia.moviesmanager.models.Movie
import com.leticia.moviesmanager.models.dao.MovieDAO
import com.leticia.moviesmanager.models.dao.MovieDAOSQLite
import com.leticia.moviesmanager.views.MainActivity

class MovieController(mainActivity: MainActivity) {
    private val movieDAOImp: MovieDAO = MovieDAOSQLite(mainActivity)
    fun insertMovie(movie: Movie) = movieDAOImp.createMovie(movie)
    fun getMovie(id: Int) = movieDAOImp.retrieveMovie(id)
    fun getMovies() = movieDAOImp.retrieveMovies()
    fun editMovie(movie: Movie) = movieDAOImp.updateMovie(movie)
    fun removeMovie(id: Int) = movieDAOImp.deleteMovie(id)
}