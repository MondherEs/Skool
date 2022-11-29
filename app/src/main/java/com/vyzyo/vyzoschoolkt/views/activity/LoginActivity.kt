package com.vyzyo.vyzoschoolkt.views.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vyzyo.vyzoschoolkt.R
import com.vyzyo.vyzoschoolkt.config.BaseUrlHolder
import com.vyzyo.vyzoschoolkt.config.Config
import com.vyzyo.vyzoschoolkt.config.Config.Companion.BASEURL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.DEFAULT_BASE_URL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.LOGO_URL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.SCHOOL_VERIFICATION_URL
import com.vyzyo.vyzoschoolkt.databinding.ActivityLoginBinding
import com.vyzyo.vyzoschoolkt.model.LoginResponse
import com.vyzyo.vyzoschoolkt.model.Role
import com.vyzyo.vyzoschoolkt.model.UserResponse
import com.vyzyo.vyzoschoolkt.utils.SharedPreference
import com.vyzyo.vyzoschoolkt.viewModel.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var TAG: String = "LoginActivity"
    private val mainViewModel: SchoolViewModel by viewModels()

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            checkFieldsForEmptyValues()
        }

        override fun afterTextChanged(editable: Editable) {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {
        val sharedPreference = SharedPreference(this)

        binding.mainId.addTextChangedListener(textWatcher)
        binding.mainPassword.addTextChangedListener(textWatcher)
        binding.loginChangeschool.setOnClickListener { v ->

            sharedPreference.enableChangeURL()
            sharedPreference.save(BASEURL, DEFAULT_BASE_URL)
            BaseUrlHolder(SCHOOL_VERIFICATION_URL)
            startActivity(Intent(this@LoginActivity, SchoolIdentification::class.java))
            finish()
        }
        checkFieldsForEmptyValues()
        Glide.with(this).load("$LOGO_URL../uploads/logo/logo.png")
            .apply(RequestOptions().override(binding.imageSplash.width, binding.imageSplash.height))
            .into(binding.imageSplash)
        binding.mainSignin.setOnClickListener {

            login(binding.mainId.text.toString(), binding.mainPassword.text.toString())
        }


    }

    private fun login(email: String, password: String) {

        Log.d(TAG, email + password)

        mainViewModel.getLogin2(email, password)
        mainViewModel.getLoginDetail.observe(this) {
            if (it.code=="200") {

                //     it.body()!!.data[0].token
                Log.d(TAG + "LoginResponse", it.data[0].token)
                Log.d("Hello", it.data[0].token)

                if (it.data[0].user_role == "parent") {
                    Config.ROLE = Role.Parent
                } else {
                    Config.ROLE = Role.Student
                }
                Config.TOKEN = it.data[0].token
                getLoginData(it.data)
            }

        }
    }

//            if (it.code == "200") {
//                Log.d(TAG + "LoginResponse", it.data[0].token)
//                if (it.data[0].user_role == "parent") {
//                    Config.ROLE = Role.Parent
//                } else {
//                    Config.ROLE = Role.Student
//                }
//                Config.TOKEN = it.data[0].token
//                getLoginData(it.data)
//
//
//            } else if (it.code == "401") {
//                Toast.makeText(this@LoginActivity, getString(R.string.invalid_username), Toast.LENGTH_SHORT).show()
//            }
//            sharedPreference.save(Config.CHANGEURL, false)





    private fun getLoginData(data: List<LoginResponse.Data>) {

        val token = "Bearer " + data[0].token
        Config.TOKEN = token
        val role: String = data[0].user_role
        val userID: String = data[0].user_id
        Config.USER_ID = userID
        val sharedPreference = SharedPreference(this)


        when (role) {
            "parent" -> {
                sharedPreference.disableChangeURL()
                Config.ROLE = Role.Parent
                mainViewModel.getChildren2(Config.TOKEN, Config.USER_ID)

                mainViewModel.getChildren.observe(this)
                { childrenResponseResponse ->

                    Config.children.clear()
                    Config.children.addAll(childrenResponseResponse.data)
                    for (i in 0 until Config.children.size) {

                        mainViewModel.getStudentImage(Config.TOKEN, Config.children[i]!!.student_id, "student", Config.children[i]!!.sex)
                            .observe(this) {

                                Config.children[i]!!.imageUrl = it.data

                                if (i == Config.children.size - 1) {
                                    mainViewModel.getUser(Config.TOKEN, userID, "parent")
                                        .observe(this) { responseUser ->
                                            val users: List<UserResponse.Data> = responseUser.data
                                            if (users.isNotEmpty()) Config.loggedUser = users[0]
                                            //  GetFCMToken()
                                        }
                                }
                            }
                    }
                }
            }
            "student" -> {

                sharedPreference.disableChangeURL()
                Config.ROLE = Role.Student
                mainViewModel.getUser(Config.TOKEN, userID, "parent")
                    .observe(this) { responseUser ->

                        val users: List<UserResponse.Data> = responseUser.data
                        if (users.isNotEmpty()) Config.loggedUser = users[0]
                        //   GetFCMToken()
                    }
            }
            else -> Toast.makeText(this@LoginActivity, this@LoginActivity.getString(R.string.inaccessible_role), Toast.LENGTH_SHORT).show()
        }
    }


    private fun checkFieldsForEmptyValues() {
        val s1 = binding.mainPassword.text.toString()
        val s2 = binding.mainId.text.toString()
        if (s1.isNotEmpty() && s2.isNotEmpty()) {
            binding.mainSignin.isEnabled = true
            binding.mainSignin.background =
                AppCompatResources.getDrawable(this, R.drawable.roundedshape)
        } else {
            binding.mainSignin.isEnabled = false
            binding.mainSignin.background =
                AppCompatResources.getDrawable(this, R.drawable.roundedshapegray)
        }
    }
}