package com.example.videocall

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var mDatabase: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        mDatabase = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        save_bt.setOnClickListener {
            val name = name_et.text.toString()
            if(name.isNotEmpty()){
                mDatabase.collection("Users").document("${mAuth.currentUser!!.email.hashCode()}1234").update("name",name)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this,"Name Updated Successfully",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this,"Error Occurred: ${it.exception?.localizedMessage}",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        logout_bt.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            intent.flags = FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finishAffinity()
        }
    }
}