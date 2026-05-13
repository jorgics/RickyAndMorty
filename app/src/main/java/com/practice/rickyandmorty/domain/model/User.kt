package com.practice.rickyandmorty.domain.model

data class User(
    val id: String = "1",
    val name: String = "John Doe",
    val email: String = "johnDoe@gmail.com",
    val avatarUrl: String? = ""
)