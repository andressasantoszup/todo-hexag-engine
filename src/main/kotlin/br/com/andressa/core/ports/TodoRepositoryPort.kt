package br.com.andressa.core.ports

import br.com.andressa.database.entity.TodoEntity
import java.util.*

interface TodoRepositoryPort {

    fun getCql(): List<TodoEntity>
    fun getByIdCql(id: UUID): TodoEntity
}