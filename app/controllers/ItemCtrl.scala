package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json.Json

import controllers.common.Endpoint
import graph.ItemRepo
import models.Item
import models.Formats._
import core.common.Core.execute
import core.commands.CreateItem._
import core.commands.FindItem._
import core.exceptions.EntityNotFound

class ItemCtrl extends Controller {

	implicit val createItem = Json.reads[CreateItem]
	implicit val createItemOutcome = Json.writes[CreateItemOutcome]

	def create() = Endpoint.executeAs[CreateItem, CreateItemOutcome]

	implicit val findItemOutcome = Json.writes[FindItemOutcome]

	def find(uuid: String) = Endpoint.execute[FindItem, FindItemOutcome](FindItem(uuid))
	
}