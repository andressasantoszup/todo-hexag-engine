package br.com.andressa.database

import br.com.andressa.database.entity.TodoEntity
import br.com.andressa.database.repository.TodoRepositoryImpl
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.ResultSet
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
class TodoEntityTest: AnnotationSpec() {

    val cqlSession = mockk<CqlSession>(relaxed = true)
    val database = TodoRepositoryImpl(cqlSession)
    val row = mockk<Row>()

    lateinit var resultSet: ResultSet
    lateinit var todoEntity: TodoEntity
    lateinit var list: List<TodoEntity>

    @BeforeEach
    fun setUp(){
        todoEntity = TodoEntity(UUID.fromString("09d9e708-e8fc-11eb-9a03-0242ac130003"),"segunda","correr",false)
        list = listOf()
    }

    @Test
    fun `getAll`(){
        every {
            cqlSession.execute("todo") } answers { resultSet }
        val result = database.getCql()
        result shouldBe list
    }

    @Test
    fun `getById`(){
        every { cqlSession.execute("").one() } answers { row }
        every { row.getUuid("id") } answers { todoEntity.id }
        every { row.getString("date") } answers { todoEntity.date }
        every { row.getString("description") } answers { todoEntity.description }
        every { row.getBoolean("done") } answers { todoEntity.done }

        val result = database.getByIdCql(todoEntity.id!!)

        result shouldBe todoEntity.id

//        val queryResult = cqlSession.execute(SimpleStatement
//            .newInstance(
//                "SELECT * FROM todo WHERE id=?",
//                todoEntity.id
//            ))
//        queryResult.map { it ->
//            TodoEntity(
//                it.getUuid("id")!!,
//                it.getString("date")!!,
//                it.getString("description")!!,
//                it.getBoolean("done")!!
//            )
//        }.single()
        
//        val result = database.getByIdCql(UUID.fromString("09d9e708-e8fc-11eb-9a03-0242ac130003"))


    }
}