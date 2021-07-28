package br.com.andressa.entrypoint.controller

import br.com.andressa.core.mappers.TodoConverter
import br.com.andressa.core.model.Todo
import br.com.andressa.core.ports.TodoServicePort
import br.com.andressa.database.entity.TodoEntity
import br.com.andressa.entrypoint.model.TodoRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*


@Controller ("/todos")
class TodoController(private val todoServicePort: TodoServicePort) {

    @Get
    fun getAll(): HttpResponse<List<TodoRequest>> {
        val todoRequest = todoServicePort.getAll()
        return HttpResponse.ok(todoRequest.map { TodoConverter.todoToTodoRequest(it) })

    }


    @Get ("/{id}")
    fun getById(id: UUID): HttpResponse<TodoRequest>{
        val todoRequest = todoServicePort.getById(id)
        return HttpResponse.ok(TodoConverter.todoToTodoRequest(todoRequest))
    }
}