package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json.Json.toJson

import graph.ItemRepo
import models.Item
import models.Formats._
import core.actions.CreateItem
import core.actions.Formats._

class ItemCtrl extends Controller {

	def create() = Action.async(parse.json) { request =>
		val action = request.body.as[CreateItem]
		ItemRepo.create(action) map { createdItem =>
			Ok(toJson(createdItem))
		}
	}
	
}