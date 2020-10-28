package com.example.homecomfort

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.otpdlg.view.*

class login : AppCompatActivity() {

    lateinit var mAuth : FirebaseAuth
    lateinit var cd : String
   private var temp:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

        mAuth = FirebaseAuth.getInstance()

        btnskp.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                Home()
            ).commit()
        }

        sotp.setOnClickListener {
            verify()
            var dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.otpdlg)
            val body = dialog.findViewById(R.id.votp) as EditText
            val yesBtn = dialog.findViewById(R.id.cotp) as Button

            yesBtn.setOnClickListener {
                check(body.text.toString())
                dialog.dismiss()
            }
            dialog.show()

        }

    }

    private fun check(n:String) {

        val credential = PhoneAuthProvider.getCredential(cd,n)
        signInWithPhoneAuthCredential(credential)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   startActivity(Intent(this,HomeActivity::class.java))

                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
    }


    private fun verify() {

            var phn = login_no.text.toString()
            Toast.makeText(this,phn, Toast.LENGTH_LONG).show()
            if((phn.isEmpty()) or (phn.length!=10))
            {
                login_no.setError("Phone number error")
                login_no.requestFocus()
            }
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phn, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                this, // Activity (for callback binding)
                callbacks) // OnVerificationStateChangedCallbacks

        }
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(this@login,"Invalid number",Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            cd = p0


        }
    }
}