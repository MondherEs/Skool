package com.vyzyo.vyzoschoolkt.model

data class LoginResponse(
    val code: String,
    val data: List<Data>,
    val message: String
) {
    data class Data(
        val token: String,
        val user_id: String,
        val user_role: String
    )
}