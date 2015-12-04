package graph

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import models.Category
import models.Formats._

object CategoryRepo {

	def find(uuid: UUID): Future[Option[Category]] = {
		val statement = "MATCH (c:Category {uuid: {uuid}}) RETURN c"
		val parameters = Json.obj("uuid" -> uuid)
		Cypher.send(statement, parameters) map { result =>
			result.rows match {
				case elem :: Nil =>
					val node = elem("c")
					Some(node.as[Category])
				case nil => None
			}
		}
	}

	def create(name: String): Future[Category] = {
		val statement = "CREATE (c:Category {uuid: {uuid}, name: {name}}) RETURN c"
		val parameters = Json.obj(
			"uuid" -> UUID.randomUUID,
			"name" -> name
		)
		Cypher.send(statement, parameters) map { result =>
			result.rows(0)("c").as[Category]
		}
	}
	
}