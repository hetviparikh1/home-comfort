package com.example.homecomfort

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(topAppBar)
        //creating hamburger icon
        var toggler= ActionBarDrawerToggle(this,dr,topAppBar,0,0)

        dr.addDrawerListener(toggler)
        toggler.syncState()

      


    }

    override fun onBackPressed() {
        if(dr.isDrawerOpen(GravityCompat.START))
            dr.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }
}