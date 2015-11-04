package graph

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._

import models.Category

object CategoryRepo {

	def find(uuid: String): Future[Category] = {
		val statement = "MATCH (n {uuid: {uuid}}) RETURN n"
		val parameters = Json.obj("uuid" -> uuid)
		Cypher.execute(statement, parameters) map { result =>
			Category(uuid, "Fake Category")
		}
	}

	def create(name: String): Future[CypherResponse] = {
		val statement = "CREATE (n:Category {name: {name}, uuid: {uuid}}) RETURN n"
		val parameters = Json.obj(
			"uuid" -> UUID.randomUUID,
			"name" -> name
		)
		Cypher.execute(statement, parameters)
	}
	
}