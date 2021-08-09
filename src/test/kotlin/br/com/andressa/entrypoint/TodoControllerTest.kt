package br.com.andressa.entrypoint

import br.com.andressa.core.mappers.TodoConverter
import br.com.andressa.core.model.Todo
import br.com.andressa.core.ports.TodoServicePort
import br.com.andressa.entrypoint.controller.TodoController
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
class TodoControllerTest: AnnotationSpec() {

    val port = mockk<TodoServicePort>()
    val controller = TodoController(port)

    lateinit var todo: Todo
    lateinit var listTodo: List<Todo>

    @BeforeEach
    fun setUp(){
        todo = Todo(UUID.randomUUID(),"segunda","correr",false)
        listTodo = listOf(todo)
    }

    @Test
    fun `recebendo requisição de busca getAll`(){
        every { port.getAll() } answers {listTodo}
        val listRequest = listTodo.map { TodoConverter.todoToTodoRequest(it) }
        val result = controller.getAll()

        result.body().get(0).id shouldBe listRequest.get(0).id
        result.body().get(0).date shouldBe listRequest.get(0).date
        result.body().get(0).description shouldBe listRequest.get(0).description
        result.body().get(0).done shouldBe listRequest.get(0).done

    }

    @Test
    fun `recebendo requisição de busca getById`(){
        every { port.getById(any()) } answers {todo}
        val todoEntity = TodoConverter.todoToTodoRequest(todo)
        val result = controller.getById(todo.id!!)

        result.body().id shouldBe todoEntity.id
        result.body().date shouldBe todoEntity.date
        result.body().description shouldBe todoEntity.description
        result.body().done shouldBe todoEntity.done
    }
}