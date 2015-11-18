package core.commands

import play.api.libs.json._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core.Command
import core.Outcome
import models.Item
import graph.ItemRepo
import core.exceptions.EntityNotFound

case class CreateItem (
	name: String,
	description: String,
	categoryId: String
) extends Command {
	def handle(): Future[CreateItemOutcome] = {
		ItemRepo.create(this) map {
			createdItem => CreateItemOutcome(createdItem)
		}
	}
}

case class CreateItemOutcome (
	createdItem: Item
) extends Outcome