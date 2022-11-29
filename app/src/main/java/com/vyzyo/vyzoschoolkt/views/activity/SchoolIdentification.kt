package com.vyzyo.vyzoschoolkt.views.activity

import `in`.aabhasjindal.otptextview.OTPListener
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vyzyo.vyzoschoolkt.R
import com.vyzyo.vyzoschoolkt.config.BaseUrlHolder
import com.vyzyo.vyzoschoolkt.config.Config
import com.vyzyo.vyzoschoolkt.config.Config.Companion.CHANGEURL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.LOGO_URL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.SCHOOL_VERIFICATION_URL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.apiHost
import com.vyzyo.vyzoschoolkt.databinding.ActivitySchoolIdentificationBinding
import com.vyzyo.vyzoschoolkt.utils.SharedPreference
import com.vyzyo.vyzoschoolkt.viewModel.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SchoolIdentification : AppCompatActivity() {

    private lateinit var binding: ActivitySchoolIdentificationBinding
    private var currentOtp: String? = null
    private val mainViewModel: SchoolViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolIdentificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClickListener()
    }

    private fun initClickListener() {
        binding.identificationNext.setOnClickListener {
            if (currentOtp != null) {

                getSkoolUrl(currentOtp!!)
                // startActivity(Intent(this@SchoolIdentification, SchoolPicture::class.java))

            } else
                Toast.makeText(this, getString(R.string.error_otp), Toast.LENGTH_SHORT).show()
        }
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {}
            override fun onOTPComplete(otp: String) {
                currentOtp = otp
            }
        }
    }


    private fun getSkoolUrl(email: String) {

        mainViewModel.getSchoolUrl(email)
        mainViewModel.observeSchoolUrl().observe(this)
        {
            if (it.code == 200) {

                val sharedPreference = SharedPreference(this)

                sharedPreference.save(Config.BASEURL, it.data[0].domain)
                sharedPreference.enableChangeURL()
                Log.d("Current URL", it.data[0].sk_name)
                LOGO_URL = it.data[0].domain
                apiHost =  it.data[0].domain

                startActivity(Intent(this@SchoolIdentification, SchoolPicture::class.java))
                finish()
            }
        }
    }

}