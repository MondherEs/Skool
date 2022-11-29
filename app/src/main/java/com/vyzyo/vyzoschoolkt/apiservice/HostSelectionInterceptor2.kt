package com.vyzyo.vyzoschoolkt.apiservice

import android.annotation.SuppressLint
import android.content.Context
import com.vyzyo.vyzoschoolkt.config.Config.Companion.BASEURL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.CHANGEURL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.SCHOOL_VERIFICATION_URL
import com.vyzyo.vyzoschoolkt.config.Config.Companion.apiHost
import com.vyzyo.vyzoschoolkt.di.app.HiltApplication
import com.vyzyo.vyzoschoolkt.utils.Utils
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class HostSelectionInterceptor2 : Interceptor {
    val PREFS_NAME = "VyzoSchool"

    override fun intercept(chain: Interceptor.Chain): Response {
        apiHost?.let { host ->
            if (host == "http://www.vs.accounts.voncom.tn/") {
             val url = HttpUrl.Builder()
                .scheme("http")
                .host(host.substring(7,host.length-1)) //Just the host (like "google.com", not "google.com/api")
              //   .addPathSegment("api")
                // .addPathSegment("7")
                .build()
                val newRequest = chain.request().newBuilder().url(url).build()
                return chain.proceed(newRequest)
            }
            else {
                val url = HttpUrl.Builder()
                    .scheme("https")
                    .host(host.substring(8,host.length-1)) //Just the host (like "google.com", not "google.com/api")
                    .addPathSegment("api/")
                    // .addPathSegment("7")
                    .build()
                val newRequest = chain.request().newBuilder().url(url).build()
                return chain.proceed(newRequest)
            }


            }

          //  val request = chain.request()
        //    val newUrl = request.url.newBuilder().host(host+"api/").build()


        throw IOException("Unknown Server")
    }

}