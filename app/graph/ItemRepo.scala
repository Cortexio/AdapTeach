package graph 

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._

import models._
import core.commands.CreateItem._
import core.exceptions.EntityNotFound
import models.Formats._

object ItemRepo {

	def find(uuid: UUID): Future[Option[Item]] = {
		val statement = "MATCH (i:Item {uuid: {uuid}}) -[:IN_CATEGORY]-> (c) RETURN i, c"
		val parameters = Json.obj("uuid" -> uuid)
		Cypher.send(statement, parameters) map { result =>
			result.rows match {
				case row :: Nil =>
					val objective = row("i").as[Objective]
					val category = row("c").as[Category]
					Some(Item(objective.uuid, objective.name, objective.description, category))
				case nil => None
			}
		}
	}

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
		Cypher.send(statement, parameters) map { result =>
			if (result.rows.isEmpty) throw new EntityNotFound("No Category found for id " + command.categoryId)
			val objective = result.rows(0)("i").as[Objective]
			val category = result.rows(0)("c").as[Category]
			Item(objective.uuid, objective.name, objective.description, category)
		}
	}

}