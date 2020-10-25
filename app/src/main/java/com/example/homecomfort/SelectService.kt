package com.example.homecomfort

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_select_service.*
import kotlinx.android.synthetic.main.fragment_select_service.txtemail


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectService.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectService : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_select_service, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("services")
        var arlist = ArrayList<String>()

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //   var o = KeyFatch()
                for (data in dataSnapshot.children)
                {
                    progressBar.visibility=View.VISIBLE
                    val value = data.getValue(String::class.java)
                    if (value != null) {
                        arlist.add(value)
                    }
                    progressBar.visibility=View.INVISIBLE
                }
                selservice.adapter= ArrayAdapter(context!!.applicationContext,android.R.layout.simple_list_item_1,arlist)

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })


        btnnxt.setOnClickListener {
            var mauth= FirebaseAuth.getInstance()
            mauth.createUserWithEmailAndPassword(txtemail.text.toString(),txtpass.text.toString()).addOnCompleteListener {it->
                if (it.isSuccessful){
                    var d=Spuser(txtnm.text.toString(),txtcno.text.toString(),txtaddress.text.toString(),txtbdate.text.toString(),selservice.selectedItem.toString(),txtemail.text.toString())
                    val database = FirebaseDatabase.getInstance()
                    val myRef = database.getReference("serviceProvider")
                    var uniq=FirebaseAuth.getInstance().currentUser?.uid

                    myRef.child(uniq.toString()).setValue(d).addOnCompleteListener {
                        Toast.makeText(context!!.applicationContext,"saved",Toast.LENGTH_LONG).show()

                        (activity as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                            workPrefrences()
                        ).commit()
                    }


                }
                else{

                }
            }
                .addOnCanceledListener {
                    Toast.makeText(context!!.applicationContext,"error try after some time",Toast.LENGTH_LONG).show()
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
         * @return A new instance of fragment SelectService.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectService().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}