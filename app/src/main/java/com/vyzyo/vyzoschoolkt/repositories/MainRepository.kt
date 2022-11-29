package com.vyzyo.vyzoschoolkt.repositories

import com.vyzyo.vyzoschoolkt.apiservice.MainAPICall
import com.vyzyo.vyzoschoolkt.config.Config
import javax.inject.Inject


class MainRepository @Inject constructor(private val mainAPICall: MainAPICall) {
    suspend fun getSchoolUrl(otp: String) = mainAPICall.getSchoolUrl(Config.IDENTIFICATION_TOKEN, otp )

    suspend fun getLogin(email: String, password: String) = mainAPICall.loginUser( email,password)
    suspend fun getChildren(token: String, parentID: String) = mainAPICall.getChildren( token,parentID)
    suspend fun getStudentImage(token: String, student_id: String, type:String, gender:String) = mainAPICall.getStudentImage( token,student_id,type,gender)
    suspend fun getUser(token: String, user_id: String, user_type:String ) = mainAPICall.getUser( token,user_id,user_type)

}