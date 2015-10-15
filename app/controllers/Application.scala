package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api._
import play.api.mvc._
import play.api.libs.json._

import dataAccess._

class Application extends Controller {

  def index(url: String) = Action {
    Ok(views.html.index("Hello World!"))
  }

  def signin(username: String, password: Option[String]) = Action.async {
    val futureMaybeUser: Future[Option[User]] = UserRepo.find(username)
    for(
      maybeUser <- futureMaybeUser
    ) yield(
      maybeUser match {
        case Some(user) => Ok(views.html.test(user.username))
        case None => Redirect(routes.Application.index(""))
      }
    )
  }
}
