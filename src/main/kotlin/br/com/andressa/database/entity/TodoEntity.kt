package br.com.andressa.database.entity

import java.util.*

data class TodoEntity (

    val id: UUID? = null,
    val date: String? = "",
    val description: String? ="",
    val done: Boolean = false
)