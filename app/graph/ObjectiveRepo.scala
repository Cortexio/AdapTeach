package graph 

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._

import models._
import core.commands.CreateItem._
import core.exceptions.EntityNotFound
import models.Formats._

object ObjectiveRepo {

	def createComposite(name: String, description: Option[String]): Future[Objective]= {
		val statement = """
			CREATE (co:CompositeObjective {uuid: {uuid}, name: {name}, description: {description}})
			RETURN co
		"""
		val parameters = Json.obj(
			"uuid" -> UUID.randomUUID,
			"name" -> name,
			"description" -> description
		)
		Cypher.send(statement, parameters) map { result =>
			println(result)
			result.rows(0)("co").as[Objective]
		}
	}

}