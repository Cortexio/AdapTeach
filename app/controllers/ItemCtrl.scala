package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json.Json.toJson

import graph.ItemRepo
import models.Item
import models.Formats._
import core.commands.CreateItem
import core.exceptions.EntityNotFound
import controllers.json.CommandFormats._

class ItemCtrl extends Controller {

	def create() = Action.async(parse.json) { request =>
		val command = request.body.as[CreateItem]
		command.handle map {
			outcome => Ok(toJson(outcome.createdItem))
		} recover {
			case e: EntityNotFound => NotFound(e.getMessage)
		}
	}
	
}