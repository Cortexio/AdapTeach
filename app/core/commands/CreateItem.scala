package core.commands

import play.api.libs.json._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core.common._
import core.layers._
import models.Item
import graph.ItemRepo
import core.exceptions.EntityNotFound

object CreateItem {

	case class CreateItem (
		name: String,
		description: String,
		categoryId: String
	) extends Command

	case class CreateItemOutcome (
		createdItem: Item
		) extends Outcome[CreateItem]

	trait Filter[N <: Layer] extends CommandFilter[N, CreateItem]

	implicit object coreHandler extends CoreHandler[CreateItem, CreateItemOutcome] {
		def handle(c: CreateItem): Future[CreateItemOutcome] = {
			ItemRepo.create(c) map {
				createdItem => CreateItemOutcome(createdItem)
			}
		}
	}

	implicit object ghost extends Filter[Layers.GHOST] {
		def filter(c: CreateItem): Future[CommandFilterOutcome] = {
			if (c.name.length < 2) throw new Exception("Validation Failed")
			success
		}
	}

	val success: Future[CommandFilterOutcome] = Future(CommandFilterOutcome())

}