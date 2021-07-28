package br.com.andressa.core.ports

import br.com.andressa.core.model.Todo
import java.util.*

interface TodoServicePort {

    fun getAll(): List<Todo>
    fun getById(id: UUID): Todo
}