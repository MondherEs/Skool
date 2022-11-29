package com.vyzyo.vyzoschoolkt.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vyzyo.vyzoschoolkt.apiservice.HostSelectionInterceptor
import com.vyzyo.vyzoschoolkt.apiservice.HostSelectionInterceptor2
import com.vyzyo.vyzoschoolkt.apiservice.MainAPICall
import com.vyzyo.vyzoschoolkt.config.BaseUrlHolder
import com.vyzyo.vyzoschoolkt.config.Config
import com.vyzyo.vyzoschoolkt.config.Config.Companion.SCHOOL_VERIFICATION_URL
import com.vyzyo.vyzoschoolkt.repositories.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptorClient = OkHttpClient().newBuilder().addInterceptor(
        HostSelectionInterceptor()
    ).addInterceptor(HttpLoggingInterceptor().also { it.setLevel(HttpLoggingInterceptor.Level.BODY) }).build()
    private var gson: Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideBaseURl() = SCHOOL_VERIFICATION_URL

    @Provides
    @Singleton
    fun providesMainRepository(mainAPICall: MainAPICall):MainRepository = MainRepository(mainAPICall = mainAPICall)

    @Provides
    fun provideRetroFitInstance(baseURl: String): MainAPICall {


    return interceptorClient.let {

        Retrofit.Builder().baseUrl(SCHOOL_VERIFICATION_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(it).build()
            .create(MainAPICall::class.java)
    }}


    @Provides
    fun provide(): BaseUrlHolder {
        return BaseUrlHolder(SCHOOL_VERIFICATION_URL)
    }

}