package core.commands

import scala.concurrent.ExecutionContext.Implicits.global

import core.common._
import models.Category

import graph.CategoryRepo

object CreateCategory {

	case class CreateCategory (
		name: String
	) extends Command

	case class CreateCategoryOutcome (
		createdCategory: Category
	) extends Outcome[CreateCategory]

	implicit object Handler extends CommandHandler[CreateCategory, CreateCategoryOutcome] {
		def handle(command: CreateCategory) = {
			CategoryRepo.create(command.name) map { createdCategory =>
				CreateCategoryOutcome(createdCategory)
			}
		}
	}
}