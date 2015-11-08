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
			result.elements match {
				case elem :: Nil =>
					val node = elem("n")
					val name = (node \ "name").as[String] // TODO Use a Reads
					Some(Category(uuid, name))
				case nil => None
			}
		}
	}

	def create(name: String): Future[CypherStatementResult] = {
		val statement = "CREATE (n:Category {name: {name}, uuid: {uuid}}) RETURN n"
		val parameters = Json.obj(
			"uuid" -> UUID.randomUUID,
			"name" -> name
		)
		Cypher.execute(statement, parameters)
	}
	
}