package com.example.homecomfort

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
        movetoFragment(SelectService())

        navigation.setNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.bsp->{
                    movetoFragment(SelectService()
                    )
                }

            }
            dr.closeDrawer(GravityCompat.START)
            true
        }

    }

    private fun movetoFragment(fragment: Fragment){
        val fragmrntTrans=supportFragmentManager.beginTransaction()
        fragmrntTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmrntTrans.replace(R.id.fragmentContainer,fragment,"back")
        fragmrntTrans.addToBackStack("back")
        fragmrntTrans.commit()
    }

    override fun onBackPressed() {
        if(dr.isDrawerOpen(GravityCompat.START))
            dr.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }
}