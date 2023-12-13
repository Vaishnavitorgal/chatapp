package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPass = findViewById(R.id.edt_pass)
        btnLogin = findViewById(R.id.login)
        btnSignup = findViewById(R.id.signup)

        btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPass.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                // Handle case where email or password is empty
                Toast.makeText(
                    this@LoginActivity,
                    "Please enter both email and password",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            login(email,password);
        }

    }


    private fun login(email:String, password:String) {

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, handle the error
                    val exception = task.exception
                    if (exception is FirebaseAuthInvalidUserException) {
                        // User doesn't exist or is disabled
                        Toast.makeText(
                            this@LoginActivity,
                            "User doesn't exist or is disabled",
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (exception is FirebaseAuthInvalidCredentialsException) {
                        // Invalid email or password
                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid email or password",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        // Other errors
                        Toast.makeText(
                            this@LoginActivity,
                            "Some error occurred while connecting to Firebase",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
    }
}