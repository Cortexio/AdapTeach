package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse

import graph.CategoryRepo

class Category extends Controller {

	def create() = Action.async(parse.json) { request =>
		println(request.body \ "name")
		CategoryRepo.create((request.body \ "name").as[String]) map { node =>
			println(node)
			Ok(node)
		}
	}

}