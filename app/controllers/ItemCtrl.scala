package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json.Json.toJson

import graph.ItemRepo
import models.Item
import models.Formats._
import core.CommandHandlers.handle
import core.commands.CreateItem
import core.commands.CreateItemOutcome
import core.exceptions.EntityNotFound
import controllers.json.CommandFormats._

import core.commands.CreateItemHandler._

class ItemCtrl extends Controller {

	def create() = Action.async(parse.json) { request =>
		val command = request.body.as[CreateItem]
		handle(command) map {
			outcome: CreateItemOutcome => Ok(toJson(outcome.createdItem))
		} recover {
			case e: EntityNotFound => NotFound(e.getMessage)
		}
	}
	
}