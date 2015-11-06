package graph

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._

import models.Category

object CategoryRepo {

//	implicit val fromCypher: CypherResultItem => Category = (resultItem) => {
//			Category("uuid", "Fake Category")
//	}

	def find(uuid: String): Future[Option[Category]] = {
		val statement = "MATCH (n {uuid: {uuid}}) RETURN n"
		val parameters = Json.obj("uuid" -> uuid)
		Cypher.execute(statement, parameters) map { result =>
			result.statements match {
				case head :: _ =>
					val resultItem = head.rows(0)
					val name = (resultItem \ "name").as[String]
					Some(Category(uuid, name))
				case nil => None
			}
		}
	}

	def create(name: String): Future[CypherResult] = {
		val statement = "CREATE (n:Category {name: {name}, uuid: {uuid}}) RETURN n"
		val parameters = Json.obj(
			"uuid" -> UUID.randomUUID,
			"name" -> name
		)
		Cypher.execute(statement, parameters)
	}
	
}