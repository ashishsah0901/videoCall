package com.example.videocall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.MalformedURLException
import java.net.URL

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        var url:URL?=null
        try {
            url = URL("https://meet.jit.si")
            val defaultOptions = JitsiMeetConferenceOptions.Builder()
                .setServerURL(url)
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeet.setDefaultConferenceOptions(defaultOptions)
        }catch (e:MalformedURLException){
            Toast.makeText(this,"Error occurred: ${e.message.toString()}",Toast.LENGTH_LONG).show()
        }
        join_button.setOnClickListener {
            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(code_box.text.toString())
                .setWelcomePageEnabled(false)
                .build()
            code_box.setText("")
            JitsiMeetActivity.launch(this,options)
        }
        settings_bt.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }
}