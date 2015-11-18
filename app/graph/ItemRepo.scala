package graph 

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._

import models._
import core.commands.CreateItem
import core.exceptions.EntityNotFound
import models.Formats._

object ItemRepo {

	def create(command: CreateItem): Future[Item]= {
		val statement = """
			MATCH (c:Category {uuid: {categoryId}})
			CREATE (i:Item {uuid: {uuid}, name: {name}, description: {description}}) -[:IN_CATEGORY]-> (c)
			RETURN i, c"""
		val parameters = Json.obj(
			"uuid" -> UUID.randomUUID,
			"name" -> command.name,
			"description" -> command.description,
			"categoryId" -> command.categoryId
		)
		Cypher.execute(statement, parameters) map { result =>
			if (result.elements.isEmpty) throw new EntityNotFound("No Category found for id " + command.categoryId)
			val objective = result.elements(0)("i").as[Objective]
			val category = result.elements(0)("c").as[Category]
			Item(objective.uuid, objective.name, objective.description, category)
		}
	}

}