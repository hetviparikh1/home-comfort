package com.example.homecomfort

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_identity.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(topAppBar)
        //creating hamburger icon
        var toggler= ActionBarDrawerToggle(this,dr,topAppBar,0,0)

        dr.addDrawerListener(toggler)
        toggler.syncState()
        movetoFragment(Home())
        var uniq= FirebaseAuth.getInstance().currentUser

        navigation.setNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.bsp->{
                    if (uniq!=null)
                    {
                       startActivity(Intent(this,SpProfileActivity::class.java))
                    }
                    else {
                        movetoFragment(
                            SpLg()
                        )
                    }
                }

                R.id.rt->{

                    if (uniq!=null)
                    {
                        movetoFragment(
                            RateUs()
                        )
                    }
                    else {

                            startActivity(Intent(this,login::class.java))

                    }

                }

                R.id.lg->{
                    startActivity(Intent(this,login::class.java))
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