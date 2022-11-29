package com.vyzyo.vyzoschoolkt.model

data class getSchoolResponse(
    val code: Int,
    val data: List<Data>,
    val message: String
) {
    data class Data(
        val domain: String,
        val id: Int,
        val ip: String,
        val sk_code: Int,
        val sk_name: String
    )
}