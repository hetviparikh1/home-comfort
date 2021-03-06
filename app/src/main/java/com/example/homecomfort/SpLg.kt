package com.example.homecomfort

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sp_lg.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SpLg.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpLg : Fragment() {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        spRg.setOnClickListener {
            (activity as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                SelectService()
            ).commit()
        }

        spSn.setOnClickListener {

            var uniq= FirebaseAuth.getInstance()
            uniq.signInWithEmailAndPassword(spEm.text.toString(),spPs.text.toString()).addOnCompleteListener {
                if(it.isSuccessful) {
                    var user = uniq.currentUser
                    startActivity(Intent(context,SpProfileActivity::class.java) )


                }
                else

                {
                    var progressDialog= ProgressDialog(context)
                    progressDialog.setTitle("Incorrect Email Or Password!!")
                    progressDialog.show()

                }
        }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sp_lg, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SpLg.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SpLg().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}