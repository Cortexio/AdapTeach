package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json.Json.toJson

import graph.CategoryRepo
import models.Category
import models.Formats._

class CategoryCtrl extends Controller {

	def create() = Action.async(parse.json) { request =>
		CategoryRepo.create((request.body \ "name").as[String]) map { createdCategory =>
			Ok(toJson(createdCategory))
		}
	}

	def read(uuid: String) = Action.async { request =>
		CategoryRepo.find(uuid) map { maybeCategory =>
			maybeCategory match {
				case Some(category) => Ok(toJson(category))
				case None => NotFound("No category found for uuid : " + uuid)
			}
		}
	}

}