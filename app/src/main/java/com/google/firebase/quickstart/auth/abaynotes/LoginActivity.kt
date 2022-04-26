package com.google.firebase.quickstart.auth.abaynotes

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        mynote.text = "MY NOTE"
        login.setOnClickListener {
            loginUser()
        }
    }
        public override fun onStart() {
            super.onStart()
            // Check if user is signed in (non-null) and update UI accordingly.
            val currentUser = auth.currentUser
            if(currentUser != null){
                reload();
            }
        }
        private fun reload() {
            auth.currentUser!!.reload().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this,MainActivity::class.java))

                    Toast.makeText(this,"Reload successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(ContentValues.TAG, "reload", task.exception)
                    Toast.makeText(this, "Failed to reload user.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        fun loginUser() {
            var email: String = email.text.toString()
            var password: String = password.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"Login successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Toast.makeText(this,
                            "Unable to login. Check your input or try again later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }}
