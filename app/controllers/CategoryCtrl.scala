package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json._

import graph.CategoryRepo
import models.Category

class CategoryCtrl extends Controller {

	implicit val categoryWrites: Writes[Category] = Json.writes[Category]

	def read(uuid: String) = Action.async { request =>
		CategoryRepo.find(uuid) map { maybeCategory =>
			maybeCategory match {
				case Some(category) => Ok(Json.toJson(category)) // TODO Respond with JSON by Writes
				case None => NotFound("No category found for uuid : " + uuid)
			}
		}
	}

	def create() = Action.async(parse.json) { request =>
		CategoryRepo.create((request.body \ "name").as[String]) map { createdCategory =>
			Ok(Json.toJson(createdCategory)) // TODO Respond with JSON by Writes
		}
	}

}