package core.commands

import scala.concurrent.ExecutionContext.Implicits.global

import core.common._
import core.exceptions.EntityNotFound
import models.Item
import models.Category
import graph.ItemRepo

object FindItem {

	case class FindItem (
		uuid: String
	) extends Command

	case class FindItemOutcome (
		foundItem: Item
	) extends Outcome[FindItem]

	implicit val handler = Command.handler[FindItem, FindItemOutcome]( command => {
		ItemRepo.find(command.uuid) map { maybeItem =>
			maybeItem match {
				case Some(foundItem) => FindItemOutcome(foundItem)
				case None => throw new EntityNotFound("No Item found for uuid : " + command.uuid) 
			}
		}
	})

}