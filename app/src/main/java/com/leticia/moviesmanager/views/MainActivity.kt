package com.leticia.moviesmanager.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.leticia.moviesmanager.R
import com.leticia.moviesmanager.adapter.MoviesAdapter
import com.leticia.moviesmanager.controller.MovieController
import com.leticia.moviesmanager.databinding.ActivityMainBinding
import com.leticia.moviesmanager.models.Constants.EXTRA_LIST_MOVIE_NAMES
import com.leticia.moviesmanager.models.Constants.EXTRA_MOVIE
import com.leticia.moviesmanager.models.Constants.VIEW_MOVIE
import com.leticia.moviesmanager.models.Movie

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val movieList: MutableList<Movie> by lazy{
        movieController.getMovies()
    }

    private lateinit var movieAdapter: MoviesAdapter

    private val movieController : MovieController by lazy {
        MovieController(this)
    }

    private lateinit var marl: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        movieAdapter = MoviesAdapter(this, movieList)
        amb.movieLv.adapter = movieAdapter

        movieAdapter.notifyDataSetChanged()

        marl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
            if (result.resultCode == RESULT_OK) {
                val movie = result.data?.getParcelableExtra<Movie>(EXTRA_MOVIE)

                movie?.let { _movie->
                    val position = movieList.indexOfFirst { it.id == _movie.id }
                    if (position != -1) {
                        movieList[position] = _movie
                        movieController.editMovie(_movie)
                    }
                    else {
                        _movie.id = movieController.insertMovie(_movie)
                        movieList.add(movie)
                    }
                    movieList.sortBy { it.name }
                    movieAdapter.notifyDataSetChanged()
                }
            }
        }
        registerForContextMenu(amb.movieLv)

        amb.movieLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val movie = movieList[position]
                val movieIntent = Intent(this@MainActivity, MovieActivity::class.java)
                movieIntent.putExtra(EXTRA_MOVIE, movie)
                movieIntent.putExtra(VIEW_MOVIE, true)
                startActivity(movieIntent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addMovieMi -> {
                val movieIntent = Intent(this@MainActivity, MovieActivity::class.java)
                val nameList = movieList.map { it.name }
                movieIntent.putStringArrayListExtra(EXTRA_LIST_MOVIE_NAMES, ArrayList(nameList))
                marl.launch(movieIntent)
                true
            }
            R.id.orderByName -> {
                orderListByName()
                true
            }
            R.id.orderByRate -> {
                orderListByRate()
                true
            }

            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        return when(item.itemId) {
            R.id.removeMovieMi -> {
                movieList[position].id?.let { movieController.removeMovie(it) }
                movieList.removeAt(position)
                movieAdapter.notifyDataSetChanged()
                true
            }
            R.id.editMovieMi -> {
                val movie = movieList[position]
                val movieIntent = Intent(this, MovieActivity::class.java)
                movieIntent.putExtra(EXTRA_MOVIE, movie)
                movieIntent.putExtra(VIEW_MOVIE, false)
                marl.launch(movieIntent)
                true
            }
            else -> { false }
        }
    }

    private fun orderListByName(){
        movieList.sortBy { it.name }
        movieAdapter.notifyDataSetChanged()
    }

    private fun orderListByRate(){
        movieList.sortBy { it.rating.toDouble() }
        movieList.reverse()
        movieAdapter.notifyDataSetChanged()
    }

}