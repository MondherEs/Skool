package com.vyzyo.vyzoschoolkt.apiservice

import com.vyzyo.vyzoschoolkt.model.*
import retrofit2.Response
import retrofit2.http.*

interface MainAPICall {

    @FormUrlEncoded
    @POST(".")
    suspend fun getSchoolUrl(@Field("token") token: String?, @Field("skool_code") schoolCode: String?): Response<getSchoolResponse?>


    @FormUrlEncoded
    @POST("login.php")
    suspend fun loginUser(@Field("login") login: String?, @Field("password") password: String?): Response<LoginResponse>

    @GET("get_children.php")
    suspend fun getChildren(@Header("Authorization") token: String?, @Query("parent_id") parentID: String?): Response<ChildrenResponse?>

    @GET("get_image_student_url.php")
    fun getStudentImage(@Header("Authorization") token: String?, @Query("student_id") studentId: String?, @Query("type") type: String?, @Query("gender") gender: String?
    ): Response<StudentImageResponse?>?

    @GET("get_user.php")
    fun getUser(@Header("Authorization") token: String?, @Query("user_id") id: String?, @Query("user_type") type: String?): Response<UserResponse?>?

}