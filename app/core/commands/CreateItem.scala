package core.commands

import play.api.libs.json._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core.common._
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

	implicit object Handler extends CommandHandler[CreateItem, CreateItemOutcome] {
		def handle(c: CreateItem) = {
			ItemRepo.create(c) map {
				createdItem => CreateItemOutcome(createdItem)
			}
		}
	}

	trait Filter[N <: Layer] extends CommandFilter[N, CreateItem]

	implicit object Validation extends Filter[Layers.Validation] {
		def filter(c: CreateItem) = {
			if (c.name.length < 2) throw new Exception("Validation Failed")
			pass(c)
		}
	}

}