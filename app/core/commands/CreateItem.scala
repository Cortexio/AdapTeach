package core.commands

import play.api.libs.json._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core._
import models.Item
import graph.ItemRepo
import core.exceptions.EntityNotFound

case class CreateItem (
	name: String,
	description: String,
	categoryId: String
) extends Command

case class CreateItemOutcome (
	createdItem: Item
) extends Outcome[CreateItem]

object CreateItemHandler {

	implicit object handler extends CommandHandler[CreateItem, CreateItemOutcome] {
		def handle(c: CreateItem): Future[CreateItemOutcome] = {
			ItemRepo.create(c) map {
				createdItem => CreateItemOutcome(createdItem)
			}
		}
	}

}