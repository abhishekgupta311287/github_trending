package com.abhishekgupta.trending.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abhishekgupta.trending.R

class TrendingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trending_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TrendingFragment.newInstance())
                    .commitNow()
        }
    }
}