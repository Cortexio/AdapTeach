package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json.Json.toJson

import controllers.common.Endpoint
import graph.ItemRepo
import models.Item
import models.Formats._
import core.common.Core.execute
import core.commands.CreateItem._
import core.commands.FindItem._
import core.exceptions.EntityNotFound
import controllers.json.CommandFormats._

class ItemCtrl extends Controller {

	def create() = Endpoint.handle(parse.json) { request =>
		val command = request.body.as[CreateItem]
		execute(command) map {
			outcome => Ok(toJson(outcome.createdItem))
		}
	}

	def find(uuid: String) = Endpoint.handle { request =>
		execute(FindItem(uuid)) map {
			outcome => Ok(toJson(outcome.foundItem))
		}
	}
	
}