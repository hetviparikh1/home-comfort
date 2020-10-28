package com.example.homecomfort

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_identity.*
import kotlinx.android.synthetic.main.fragment_identity.*
import kotlinx.android.synthetic.main.fragment_identity.btnsub
import kotlinx.android.synthetic.main.fragment_identity.imageView
import kotlinx.android.synthetic.main.fragment_identity.spidprof
import kotlinx.android.synthetic.main.fragment_identity.txtadharno
import kotlinx.android.synthetic.main.fragment_identity.txtidno
import kotlinx.android.synthetic.main.fragment_identity.txtname
import java.util.*

class IdentityActivity : AppCompatActivity() {


    var imaguri: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identity)
        imageView.setOnClickListener {
            CropImage.activity().setAspectRatio(1, 1).start(this)
        }

        btnsub.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("serviceProvider")
            var uniq = FirebaseAuth.getInstance().currentUser?.uid
            var postStorage = FirebaseStorage.getInstance().reference.child("serviceProvider")

            if (imaguri != null) {
                val fileref = postStorage!!.child(UUID.randomUUID().toString())
                val uploadTask = fileref.putFile(imaguri!!)
                if (uploadTask != null) {
                    uploadTask.addOnCompleteListener {
                        if (it.isSuccessful) {
                            var downloadurl = fileref
                            var ar = Spuser()
                            if (uniq != null) {
                                myRef.child(uniq).addListenerForSingleValueEvent(object :
                                    ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        // This method is called once with the initial value and again

                                        ar = dataSnapshot.getValue(Spuser::class.java)!!
                                        ar.idprof = spidprof.selectedItem.toString()
                                        ar.img = downloadurl.toString()
                                        ar.idno = txtidno.text.toString()
                                        ar.adname = txtname.text.toString()
                                        ar.adno = txtadharno.text.toString()

                                        myRef.child(uniq.toString()).setValue(ar)
                                            .addOnCompleteListener {
                                                Toast.makeText(
                                                    this@IdentityActivity,
                                                    "saved",
                                                    Toast.LENGTH_LONG
                                                ).show()


                                            }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }
                                })

                            }
                        }
                    }

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode== Activity.RESULT_OK && data!=null){
            var result=CropImage.getActivityResult(data)
            imaguri=result.uri
            //     var bitmap= MediaStore.Images.Media.getBitmap(contentResolver,data.data)
            imageView.setImageURI(imaguri)
        }
    }
}