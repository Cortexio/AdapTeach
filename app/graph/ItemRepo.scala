package graph 

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import models._
import core.actions.CreateItem

object ItemRepo {

	def create(action: CreateItem): Future[Item]= {
		Future(Item(
			UUID.randomUUID.toString,
			"Item name",
			Option("Item description"),
			Category(
				UUID.randomUUID.toString,
				"Category name"
			)
		))
	}

}