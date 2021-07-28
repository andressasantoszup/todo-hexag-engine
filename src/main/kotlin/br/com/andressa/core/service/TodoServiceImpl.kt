package br.com.andressa.core.service

import br.com.andressa.core.mappers.TodoConverter
import br.com.andressa.core.model.Todo
import br.com.andressa.core.ports.TodoRepositoryPort
import br.com.andressa.core.ports.TodoServicePort
import java.util.*
import javax.inject.Singleton


@Singleton
class TodoServiceImpl(private val todoRepositoryPort: TodoRepositoryPort): TodoServicePort {

    override fun getAll(): List<Todo> {
        val todoEntity = todoRepositoryPort.getCql()
       return todoEntity.map { TodoConverter.todoEntityToTodo(it) }
    }

    override fun getById(id: UUID): Todo {
        return TodoConverter.todoEntityToTodo(todoRepositoryPort.getByIdCql(id))
    }
}