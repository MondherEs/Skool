package com.vyzyo.vyzoschoolkt.model

data class UserResponse(
    val code: String,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val address: String,
        val address_email: String,
        val authentication_key: Any,
        val birthday: String,
        val blood_group: Any,
        val dormitory_id: Any,
        val dormitory_room_number: Any,
        val email: String,
        val level: String,
        val name: String,
        val nationality: String,
        val other_phone: String,
        val parent_id: String,
        val parents_situation: String,
        val password: String,
        val phone: String,
        val religion: Any,
        val sex: String,
        val state_id: String,
        val student_code: String,
        val student_id: String,
        val transport_id: Any
    )
}