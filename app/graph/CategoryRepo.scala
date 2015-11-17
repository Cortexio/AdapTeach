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

	def find(uuid: String): Future[Option[Category]] = {
		val statement = "MATCH (n {uuid: {uuid}}) RETURN n"
		val parameters = Json.obj("uuid" -> uuid)
		Cypher.execute(statement, parameters) map { result =>
			result.elements match {
				case elem :: Nil =>
					val node = elem("n")
					Some(node.as[Category])
				case nil => None
			}
		}
	}

	def create(name: String): Future[Category] = {
		val statement = "CREATE (n:Category {uuid: {uuid}, name: {name}}) RETURN n"
		val parameters = Json.obj(
			"uuid" -> UUID.randomUUID,
			"name" -> name
		)
		Cypher.execute(statement, parameters) map { result =>
			result.elements(0)("n").as[Category]
		}
	}
	
}