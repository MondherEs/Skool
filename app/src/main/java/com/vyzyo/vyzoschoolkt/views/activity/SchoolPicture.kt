package com.vyzyo.vyzoschoolkt.views.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vyzyo.vyzoschoolkt.R
import com.vyzyo.vyzoschoolkt.config.Config
import com.vyzyo.vyzoschoolkt.databinding.ActivitySchoolPictureBinding

class SchoolPicture : AppCompatActivity() {

    private lateinit var binding : ActivitySchoolPictureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = Handler()
        val schoolImage = findViewById<ImageView>(R.id.school_image)
        Glide.with(this).load(Config.LOGO_URL + "../uploads/logo/logo.png")
            .apply(RequestOptions().override(schoolImage.width, schoolImage.height))
            .into(schoolImage)

        handler.postDelayed({ startActivity(Intent(this@SchoolPicture, LoginActivity::class.java))
            finish() }, 4000)
    }
}