package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import graph._

import models.{User}

class Application extends Controller{

	def index(url: String) = Action {
		Ok(views.html.index())
	}

	def session = WithSession { request =>
		Ok(User.toJson(request.user))
	}

	def test = Action.async {
		Cypher.execute("MATCH (n) RETURN id(n) as id") map { result =>
			Ok(views.html.test(result))
		}
	}

}