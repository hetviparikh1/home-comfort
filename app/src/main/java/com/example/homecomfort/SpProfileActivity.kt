package com.example.homecomfort

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_sp_profile.*

class SpProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_profile)
        var ar = Spuser()
        var uniq= FirebaseAuth.getInstance().currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("serviceProvider")
        myRef.child(uniq!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                var arlist = ArrayList<Spuser>()
                //   var o = KeyFatch()

                val value =
                    dataSnapshot.getValue(Spuser::class.java)



                if (value != null) {

                        ar = value

                    tNm.text = value.name.toString()
                    edAdd.setText(value.address.toString())
                   edPn.setText(value.cno.toString())
                    editTextTextPersonName3.setText(value.time.toString())


                }




            }


            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })
        btnup.setOnClickListener {
            ar.cno = edPn.text.toString()
            ar.address = edAdd.text.toString()
            ar.time = editTextTextPersonName3.text.toString()
            ar.shift = spSf.selectedItem.toString()
            ar.exp = spEp.selectedItem.toString()
            myRef.child(uniq!!).setValue(ar).addOnCompleteListener {
                Toast.makeText(this, "Record Updated", Toast.LENGTH_LONG).show()
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mLg -> {
                var uniq= FirebaseAuth.getInstance().signOut()

                startActivity(Intent(this, HomeActivity::class.java))
                true
            }

            else ->super.onOptionsItemSelected(item)
        }
    }
}