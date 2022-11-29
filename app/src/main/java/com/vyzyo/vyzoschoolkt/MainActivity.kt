package com.vyzyo.vyzoschoolkt

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.vyzyo.vyzoschoolkt.config.Config.Companion.BASEURL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.SCHOOL_VERIFICATION_URL
import com.vyzyo.vyzoschoolkt.utils.SharedPreference
import com.vyzyo.vyzoschoolkt.views.activity.Welcome_Activityfirstpage

class MainActivity : AppCompatActivity() {

    var mHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mHandler = Handler()
        val sharedPreference =SharedPreference(this)
       // sharedPreference.save(BASEURL,SCHOOL_VERIFICATION_URL)

        intent = Intent(this, Welcome_Activityfirstpage::class.java)

        val updateHandler = Handler()
        val runnable = Runnable {
            startActivity(intent)
        }

        updateHandler.postDelayed(runnable, 3000)


    }
}