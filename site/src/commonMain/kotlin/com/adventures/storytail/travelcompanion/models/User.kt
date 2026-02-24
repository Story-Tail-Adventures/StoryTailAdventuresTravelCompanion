package com.adventures.storytail.travelcompanion.models




expect class User {
    val id: String
    val username: String
    val password: String
}


expect class UserWithOutPassword {
    val id: String
    val username: String
}
