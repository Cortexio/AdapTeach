package controllers

import java.util.UUID

import play.api.mvc._
import play.api.libs.json.Json

import controllers.common.Endpoint
import core.commands.CreateItem._
import core.commands.FindItem._
import models.Formats._

class ItemCtrl extends Controller {

	implicit val createItem = Json.reads[CreateItem]
	implicit val createItemOutcome = Json.writes[CreateItemOutcome]

	def create() = Endpoint.executeAs[CreateItem, CreateItemOutcome]

	implicit val findItemOutcome = Json.writes[FindItemOutcome]

	def find(uuid: String) = Endpoint.execute[FindItem, FindItemOutcome](FindItem(UUID.fromString(uuid)))
	
}