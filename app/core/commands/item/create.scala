package core.commands

import play.api.libs.json._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core.common._
import models.Item
import graph.ItemRepo
import core.exceptions._

object CreateItem {

	case class CreateItem (
		name: String,
		description: String,
		categoryId: String
	) extends Command

	case class CreateItemOutcome (
		createdItem: Item
	) extends Outcome[CreateItem]

	implicit val handler = Command.handler[CreateItem, CreateItemOutcome]( (command) => {
		ItemRepo.create(command) map {
			createdItem => CreateItemOutcome(createdItem)
		}
	})

	implicit val validation = Command.filter[Layer.Validation, CreateItem]( (command) => {
		if (command.name.length < 2) throw new ValidationException("Validation Failed")
		Future(command)
	})

}