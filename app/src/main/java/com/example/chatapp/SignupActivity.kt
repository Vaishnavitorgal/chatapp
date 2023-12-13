package com.example.chatapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignupActivity : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var btnSignup: Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.name)
        edtEmail = findViewById(R.id.email)
        edtPass = findViewById(R.id.pass)
        btnSignup = findViewById(R.id.signup)

        btnSignup.setOnClickListener {
            var name = edtName.text.toString()
            var email = edtEmail.text.toString()
            var password = edtPass.text.toString()
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                // Handle case where email or password is empty
                Toast.makeText(
                    this@SignupActivity,
                    "Please enter both email and password",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            signing(name,email,password);
        }
    }

    private fun signing(name:String,email:String, password:String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDataBase(name,email,mAuth.currentUser?.uid!!)
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
//                    Toast.makeText(this@SignupActivity, "Some error occurred while connecting to firebase",Toast.LENGTH_LONG ).show()
                    val exception = task.exception
                    if (exception is FirebaseAuthInvalidCredentialsException) {
                        // Invalid email or password format
                        Toast.makeText(
                            this@SignupActivity,
                            "Invalid email or password format",
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (exception is FirebaseAuthUserCollisionException) {
                        // User with the same email already exists
                        Toast.makeText(
                            this@SignupActivity,
                            "User with the same email already exists",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else{
                        // Other errors
                        Toast.makeText(
                            this@SignupActivity,
                            "Some error occurred while connecting to firebase",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
    }

    private fun addUserToDataBase(name: String,email: String,uid:String){
         mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
        //to add in this way in db, child adds
    }

}