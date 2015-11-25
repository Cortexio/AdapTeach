package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json.Json.toJson

import core.common._
import core.commands.CreateCategory._
import core.commands.FindCategory._
import core.exceptions.EntityNotFound
import graph.CategoryRepo
import models.Category
import models.Formats._
import controllers.json.CommandFormats._

class CategoryCtrl extends Controller {

	def create() = Action.async(parse.json) { request =>
		App.execute(request.body.as[CreateCategory]) map {
			outcome => Ok(toJson(outcome.createdCategory))
		}
	}

	def read(uuid: String) = Action.async { request =>
		val command = FindCategory(uuid)
		App.execute(command) map {
			outcome => Ok(toJson(outcome.foundCategory))
		} recover {
			case e: EntityNotFound => NotFound(e.getMessage)
		}
	}

}