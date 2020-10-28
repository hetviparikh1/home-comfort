package com.example.homecomfort

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_identity.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Identity.newInstance] factory method to
 * create an instance of this fragment.
 */
class Identity : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var imaguri: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_identity, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageView.setOnClickListener {
            CropImage.activity().setAspectRatio(1, 1).start(activity!!)
        }

        btnsub.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("serviceProvider")
            var uniq= FirebaseAuth.getInstance().currentUser?.uid
            var ar= Spuser()
            if (uniq != null) {
                myRef.child(uniq).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This method is called once with the initial value and again

                        ar = dataSnapshot.getValue(Spuser::class.java)!!
                        ar.idprof = spidprof.selectedItem.toString()
                        ar.img = ""
                        ar.idno = txtidno.text.toString()
                        ar.adname = txtname.text.toString()
                        ar.adno = txtadharno.text.toString()

                        myRef.child(uniq.toString()).setValue(ar)
                            .addOnCompleteListener {
                                Toast.makeText(
                                    context!!.applicationContext,
                                    "saved",
                                    Toast.LENGTH_LONG
                                ).show()
                                (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                                    .replace(
                                        R.id.fragmentContainer,
                                        Identity()
                                    ).commit()


                            }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Identity.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Identity().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}