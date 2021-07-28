package br.com.andressa.core.mappers

import br.com.andressa.core.model.Todo
import br.com.andressa.database.entity.TodoEntity
import br.com.andressa.entrypoint.model.TodoRequest

class TodoConverter {
    companion object{
        fun todoEntityToTodo(todoEntity: TodoEntity)
        = Todo(todoEntity.id,todoEntity.date,todoEntity.description,todoEntity.done)

        fun todoRequestToTodo(todoRequest: TodoRequest)=Todo(todoRequest.id,todoRequest.date,todoRequest.description,todoRequest.done)

        fun todoToTodoRequest(todo: Todo)
        = TodoRequest(todo.id,todo.date, todo.description, todo.done)
    }
}