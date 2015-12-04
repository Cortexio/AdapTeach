package core.commands

import play.api.libs.json._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core.common._
import models.Objective
import graph.ObjectiveRepo
import core.exceptions._

object CreateCompositeObjective {

	case class CreateCompositeObjective (
		name: String,
		description: Option[String]
	) extends Command

	case class CreateCompositeObjectiveOutcome (
		createdObjective: Objective
	) extends Outcome[CreateCompositeObjective]

	implicit val handler = Command.handler[CreateCompositeObjective, CreateCompositeObjectiveOutcome]( command => {
		ObjectiveRepo.createComposite(command.name, command.description) map {
			created => CreateCompositeObjectiveOutcome(created) 
		}
	})

	implicit val validation = Command.filter[Layer.Validation, CreateCompositeObjective]( command => {
		if (command.name.length < 2) Future.failed(ValidationException("Validation Failed"))
		else Future(command)
	})

}