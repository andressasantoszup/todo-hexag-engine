package br.com.andressa.database.repository

import br.com.andressa.core.model.Todo
import br.com.andressa.core.ports.TodoRepositoryPort
import br.com.andressa.database.entity.TodoEntity
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.SimpleStatement
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

    override fun getByIdCql(id: UUID): TodoEntity {
        val result = cqlSession.execute(
            SimpleStatement
                .newInstance(
                    "SELECT * FROM todo WHERE id=?",
                    id
                )
        ).one()

        return TodoEntity(
            result?.getUuid("id"),
            result?.getString("date"),
            result?.getString("description"),
            result?.getBoolean("done")!!
        )
    }

}
