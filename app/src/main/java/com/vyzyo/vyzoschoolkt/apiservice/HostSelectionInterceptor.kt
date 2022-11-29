package com.vyzyo.vyzoschoolkt.apiservice

import android.annotation.SuppressLint
import android.content.Context
import com.vyzyo.vyzoschoolkt.config.Config.Companion.BASEURL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.CHANGEURL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.SCHOOL_VERIFICATION_URL
import com.vyzyo.vyzoschoolkt.di.app.HiltApplication
import com.vyzyo.vyzoschoolkt.utils.Utils
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response


class HostSelectionInterceptor : Interceptor {
    val PREFS_NAME = "VyzoSchool"

    @SuppressLint("CommitPrefEdits")
    override fun intercept(chain: Interceptor.Chain): Response {

        val mSharedPreferencesUrl =
            HiltApplication.appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val newUrll = mSharedPreferencesUrl.getString(BASEURL, null)
        var change = mSharedPreferencesUrl.getBoolean(CHANGEURL, false)


        var original = chain.request()
        val response = chain.proceed(chain.request())

        if (response.code == 401 || response.code == 404)
            Utils.logOut(HiltApplication.appContext, Utils.Companion.LoginState.expired)


//        if (newUrll?.length!! > 3 && change) {
//          //  val newUrl = newUrll
//          //  original = original.newBuilder().url(newUrl).build()
//
//
//            val url = HttpUrl.Builder()
//                .scheme("https")
//                .host(newUrll.substring(8,newUrll.length-1)) //Just the host (like "google.com", not "google.com/api")
//               // .addPathSegment("api")
//               // .addPathSegment("7")
//                .build()
//
//            var request = chain.request()
//
//
//
//            original = request.newBuilder()
//                .url(url)
//                .build()
//        }
        //  return chain.proceed(original)
        val request = chain.request()
        if (change) {


        }
        if (change) {

            return newUrll?.let {
                chain.proceed(
                    request.newBuilder().url(
                        request.url.toString()
                            .replace(SCHOOL_VERIFICATION_URL, "${it}api/")
                            .toHttpUrlOrNull() ?: request.url
                    )
                        .build()
                ) } ?: response

        }
        else
            return response
    }
}