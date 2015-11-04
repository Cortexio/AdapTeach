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

	def find(uuid: String): Future[Category] = {
		val statement = "MATCH (n {uuid: {uuid}}) RETURN n"
		val parameters = Json.obj("uuid" -> uuid)
		Cypher.execute(statement, parameters) map { result =>
			if (result.data.size == 0) throw new Exception("TODO Return 404 - UUID not found") // TODO
			if (result.data.size > 1) throw new Exception("Corrupt database : Duplicate UUIDs")
			val resultItem: JsValue = result.data(0).row(0)
			val name: String = (resultItem \ "name").as[String]
			Category(uuid, name)
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