package controllers

import play.api.mvc._
import play.api.libs.json.Json

import controllers.common.Endpoint
import core.commands.CreateCompositeObjective._
import models.Formats._

class ObjectiveCtrl extends Controller {

	implicit val createItem = Json.reads[CreateCompositeObjective]
	implicit val createItemOutcome = Json.writes[CreateCompositeObjectiveOutcome]

	def createComposite() = Endpoint.executeAs[CreateCompositeObjective, CreateCompositeObjectiveOutcome]

}