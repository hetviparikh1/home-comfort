package com.example.homecomfort

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_select_service.*
import kotlinx.android.synthetic.main.fragment_select_service.btnnxt
import kotlinx.android.synthetic.main.fragment_work_prefrences.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [workPrefrences.newInstance] factory method to
 * create an instance of this fragment.
 */
class workPrefrences : Fragment() {
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
        return inflater.inflate(R.layout.fragment_work_prefrences, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnnxt.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("serviceProvider")
            var uniq= FirebaseAuth.getInstance().currentUser?.uid
            var ar=Spuser()
            if (uniq != null) {
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This method is called once with the initial value and again

                        for (data in dataSnapshot.children) {

                            progressBar.visibility=View.VISIBLE
                            val value = data.getValue(Spuser::class.java)
                            if (value != null) {
                                ar=value
                            }
                            progressBar.visibility=View.INVISIBLE
                        }

                        ar.loc=txtloc.text.toString()
                        ar.shift=spshift.selectedItem.toString()
                        ar.time=txttime.toString()
                        ar.exp=txtexp.toString()
                        ar.disc=txtdec.toString()
                        myRef.child(uniq.toString()).setValue(ar).addOnCompleteListener {
                            Toast.makeText(context!!.applicationContext,"saved", Toast.LENGTH_LONG).show()

                            (activity as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                                Identity()
                            ).commit()
                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
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
         * @return A new instance of fragment workPrefrences.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            workPrefrences().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}