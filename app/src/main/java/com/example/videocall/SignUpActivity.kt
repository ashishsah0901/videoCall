package com.example.videocall

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseFirestore
    lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance()
        dialog = ProgressDialog(this)
        dialog.setTitle("Please wait...")
        mDatabase = FirebaseFirestore.getInstance()
        login_bt.setOnClickListener {
            finish()
        }
        signup_bt.setOnClickListener {
            dialog.show()
            dialog.setTitle("Please wait...")
            dialog.setMessage("Wait Till We Process Your Data")
            val name = name_et.text.toString()
            val email = email_et.text.toString()
            val password = password_et.text.toString()
            val user = User(name, email, password)
            mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        dialog.dismiss()
                        mDatabase.collection("Users")
                            .document("${email.hashCode()}1234").set(user).addOnSuccessListener {
                                Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this,DashboardActivity::class.java))
                                finishAffinity()
                            }
                    }else{
                        dialog.dismiss()
                        Toast.makeText(this,"An Error Occurred: ${it.exception?.localizedMessage}",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}