package com.vyzyo.vyzoschoolkt.views.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vyzyo.vyzoschoolkt.config.Config
import com.vyzyo.vyzoschoolkt.databinding.ActivityWelcomeActivityfirstpageBinding
import com.vyzyo.vyzoschoolkt.utils.SharedPreference

class Welcome_Activityfirstpage : AppCompatActivity() {
    private lateinit var binding : ActivityWelcomeActivityfirstpageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeActivityfirstpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreference = SharedPreference(this)
        sharedPreference.disableChangeURL()
        sharedPreference.save(Config.BASEURL, Config.DEFAULT_BASE_URL)
        binding.startApp.setOnClickListener {
            startActivity(Intent(this, SchoolIdentification::class.java))
            finish()
        }
    }
}