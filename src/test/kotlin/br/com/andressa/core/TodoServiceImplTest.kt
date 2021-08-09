package br.com.andressa.core

import br.com.andressa.core.mappers.TodoConverter
import br.com.andressa.core.ports.TodoRepositoryPort
import br.com.andressa.core.service.TodoServiceImpl
import br.com.andressa.database.entity.TodoEntity
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
class TodoServiceImplTest: AnnotationSpec() {

    val port = mockk<TodoRepositoryPort>()
    val service = TodoServiceImpl(port)

    lateinit var todoEntity: TodoEntity
    lateinit var list: List<TodoEntity>

    @BeforeEach
    fun setUp(){
        todoEntity = TodoEntity(UUID.fromString("09d9e708-e8fc-11eb-9a03-0242ac130003"),"segunda","correr",false)
        list = listOf(todoEntity)
    }

    @Test
    fun `getAll`(){
        every { port.getCql() } answers {list}
        val listTodo = list.map { TodoConverter.todoEntityToTodo(it) }
        val result = service.getAll()
        result shouldBe listTodo
    }

    @Test
    fun `getById`(){
        every { port.getByIdCql(any()) } answers {todoEntity}
        val todo = TodoConverter.todoEntityToTodo(todoEntity)
        val result = service.getById(todoEntity.id!!)

        result.id shouldBe todo.id
        result.date shouldBe todo.date
        result.description shouldBe todo.description
        result.done shouldBe todo.done
    }

}