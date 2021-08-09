package br.com.andressa.database.repository

import br.com.andressa.core.model.Todo
import br.com.andressa.core.ports.TodoRepositoryPort
import br.com.andressa.database.entity.TodoEntity
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import java.util.*
import javax.inject.Singleton


@Singleton
class TodoRepositoryImpl(private val cqlSession: CqlSession) : TodoRepositoryPort {

    override fun getCql(): List<TodoEntity> {
        val all = cqlSession.execute(SimpleStatement.newInstance("SELECT*FROM todo.Todo"))
        return all.map {
            TodoEntity(
                it.getUuid("id"),
                it.getString("date")!!,
                it.getString("description")!!,
                it.getBoolean("done")
            )
        }.toList()
    }

    override fun getByIdCql(id: UUID): TodoEntity = converteRowParaTodoEntity(
        cqlSession.execute(
            QueryBuilder.selectFrom("todo")
                .all()
                .whereColumn("id")
                .isEqualTo(QueryBuilder.literal(id))
                .build()
        ).one()!!
    )
}

private fun converteRowParaTodoEntity(row: Row) =
    TodoEntity(UUID.fromString("09d9e708-e8fc-11eb-9a03-0242ac130003"), "segunda", "correr", false!!)



