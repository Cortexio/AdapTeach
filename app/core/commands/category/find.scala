package core.commands

import scala.concurrent.ExecutionContext.Implicits.global

import core.common._
import core.exceptions.EntityNotFound
import models.Category
import graph.CategoryRepo

object FindCategory {

	case class FindCategory (
		uuid: String
	) extends Command

	case class FindCategoryOutcome (
		foundCategory: Category
	) extends Outcome[FindCategory]

	implicit val handler = Command.handler[FindCategory, FindCategoryOutcome]( (command) => {
		CategoryRepo.find(command.uuid) map { maybeCategory =>
			maybeCategory match {
				case Some(foundCategory) => FindCategoryOutcome(foundCategory)
				case None => throw new EntityNotFound("No Category found for uuid : " + command.uuid) 
			}
		}
	})

}