package graph 

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._

import models._
import core.actions.CreateItem
import models.Formats._

object ItemRepo {

	def create(action: CreateItem): Future[Item]= {
		val statement = """
			MATCH (c:Category {uuid: {categoryId}})
			CREATE (i:Item {uuid: {uuid}, name: {name}, description: {description}}) -[:IN_CATEGORY]-> (c)
			RETURN i, c"""
		val parameters = Json.obj(
			"uuid" -> UUID.randomUUID,
			"name" -> action.name,
			"description" -> action.description,
			"categoryId" -> action.categoryId
		)
		Cypher.execute(statement, parameters) map { result =>
			if (result.elements.isEmpty) throw new Exception("No Category found for id " + action.categoryId)
			val objective = result.elements(0)("i").as[Objective]
			val category = result.elements(0)("c").as[Category]
			Item(objective.uuid, objective.name, objective.description, category)
		}
	}

}