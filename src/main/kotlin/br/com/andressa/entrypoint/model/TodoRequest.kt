package br.com.andressa.entrypoint.model

import java.util.*

data class TodoRequest (
    val id: UUID? = null,
    val date: String? = "",
    val description: String? ="",
    val done: Boolean = false

)