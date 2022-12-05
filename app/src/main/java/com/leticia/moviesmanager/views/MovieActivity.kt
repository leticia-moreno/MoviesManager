package com.leticia.moviesmanager.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.leticia.moviesmanager.databinding.ActivityMovieBinding
import com.leticia.moviesmanager.models.Constants.EXTRA_LIST_MOVIE_NAMES
import com.leticia.moviesmanager.models.Constants.EXTRA_MOVIE
import com.leticia.moviesmanager.models.Constants.INVALID_MOVIE_ID
import com.leticia.moviesmanager.models.Constants.VIEW_MOVIE
import com.leticia.moviesmanager.models.Movie
import com.leticia.moviesmanager.utils.Genre

class MovieActivity : AppCompatActivity() {
    private val amb: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        val listMovies = intent.getStringArrayListExtra(EXTRA_LIST_MOVIE_NAMES)
        val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        receivedMovie?.let{ _receiveMovie ->
            with(amb) {
                with(_receiveMovie) {
                    nameEt.isEnabled = false
                    nameEt.setText(name)
                    durationEt.setText(duration)
                    releaseYearEt.setText(releaseYear)
                    if(watched == "checked") watchedSw.toggle()
                    studioEt.setText(producer)
                    ratingEt.setText(rating)
                    for (i in 0 until Genre.values().size){
                        if(genre == Genre.values()[i].toString()) {
                            genreSp.setSelection(i)
                        }
                    }
                }
            }
        }
        val viewMovie = intent.getBooleanExtra(VIEW_MOVIE, false)
        if (viewMovie) {
            amb.nameEt.isEnabled = false
            amb.releaseYearEt.isEnabled = false
            amb.durationEt.isEnabled = false
            amb.studioEt.isEnabled = false
            amb.watchedSw.isEnabled = false
            amb.ratingEt.isEnabled = false
            amb.genreSp.isEnabled = false
            amb.saveBt.visibility = View.GONE
        }
        amb.saveBt.setOnClickListener {
            val watchedValue : String = if(amb.watchedSw.isChecked) "checked" else "unchecked"
            if (listMovies != null) {
                for (i in 0 until listMovies.size){
                    if(listMovies[i] == amb.nameEt.text.toString()) {
                        Toast.makeText(this, "Nome já cadastrado", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
            val movie = Movie(
                id = receivedMovie?.id?: INVALID_MOVIE_ID,
                name = amb.nameEt.text.toString(),
                duration = amb.durationEt.text.toString(),
                releaseYear = amb.releaseYearEt.text.toString(),
                producer = amb.studioEt.text.toString(),
                watched = watchedValue,
                rating = amb.ratingEt.text.toString(),
                genre = amb.genreSp.selectedItem.toString()
            )
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_MOVIE, movie)
            setResult(AppCompatActivity.RESULT_OK, resultIntent)
            finish()
        }
    }
}