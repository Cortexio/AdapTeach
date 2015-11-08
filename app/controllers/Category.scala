package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse

import graph.CategoryRepo

class Category extends Controller {

	def read(uuid: String) = Action.async { request =>
		CategoryRepo.find(uuid) map { maybeCategory =>
			maybeCategory match {
				case Some(category) => Ok(category.toString) // TODO Respond with JSON by Writes
				case None => NotFound("No category found for uuid : " + uuid)
			}
		}
	}

	def create() = Action.async(parse.json) { request =>
		CategoryRepo.create((request.body \ "name").as[String]) map { createdCategory =>
			Ok(createdCategory.toString) // TODO Respond with JSON by Writes
		}
	}

}