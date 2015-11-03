package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse

import graph.CategoryRepo

class Category extends Controller {

	def read(uuid: String) = Action.async { request =>
		CategoryRepo.find(uuid) map { category =>
			Ok(category.toString)
		}
	}

	def create() = Action.async(parse.json) { request =>
		CategoryRepo.create((request.body \ "name").as[String]) map { createdCategory =>
			Ok(createdCategory)
		}
	}

}