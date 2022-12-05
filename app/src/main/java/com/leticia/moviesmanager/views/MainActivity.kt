package com.leticia.moviesmanager.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.leticia.moviesmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
    }

}