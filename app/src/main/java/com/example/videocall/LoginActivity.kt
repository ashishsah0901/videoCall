package com.example.videocall

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        dialog = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser?.uid
        if(currentUser!=null){
            startActivity(Intent(this,DashboardActivity::class.java))
            finish()
        }
        signup_bt.setOnClickListener {
            email_et.setText("")
            password_et.setText("")
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        login_bt.setOnClickListener {
            val email = email_et.text.toString()
            val password = password_et.text.toString()
            dialog.setTitle("Please wait...")
            dialog.setMessage("Wait Till We Process Your Data")
            dialog.show()
            mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        dialog.dismiss()
                        Toast.makeText(this,"Logged In Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,DashboardActivity::class.java))
                        finish()
                    }else{
                        dialog.dismiss()
                        Toast.makeText(this,"An Error Occurred: ${it.exception?.localizedMessage}",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}