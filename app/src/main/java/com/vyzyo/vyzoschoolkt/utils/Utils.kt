package com.vyzyo.vyzoschoolkt.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.vyzyo.vyzoschoolkt.di.app.HiltApplication
import com.vyzyo.vyzoschoolkt.views.activity.LoginActivity

class Utils {
    companion object {

        enum class LoginState {
            fistLogin, expired, disconnect
        }


        fun logOut(context: Context, loginState: LoginState) {


            val sharedPreference = SharedPreference(HiltApplication.appContext.applicationContext)

            val editor: SharedPreferences.Editor
            if (sharedPreference != null) {
                editor = sharedPreference.sharedPref.edit()
                for (key in sharedPreference.sharedPref.all.keys) if (key != "baseUrl") editor.remove(
                    key
                )
                editor.apply()
            }
            //        if (((Activity) context != null)) {
            //           ((Activity) context).finish();
            //      }
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("state", loginState.ordinal)
            context.startActivity(intent)
        }
    }
}