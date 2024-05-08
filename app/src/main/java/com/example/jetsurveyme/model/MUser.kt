package com.example.jetsurveyme.model

data class MUser(
    val id:String?,
    val userId:String,
    val email:String
){
    fun toMap():MutableMap<String,Any>{
        return mutableMapOf(
            "user_id" to this.userId,
            "email" to this.email
        )
    }
}
