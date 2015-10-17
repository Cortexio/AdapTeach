package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.RequestHeader
import org.mindrot.jbcrypt.{BCrypt}
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import dataAccess.{User, UserRepo}

class Auth extends Controller {
 
   def signin(username: String, hashedPasswd: String) = Action.async {
    val futureMaybeUser: Future[Option[User]] = UserRepo.find(username)
    for(
      maybeUser <- futureMaybeUser
    ) yield(
      maybeUser match {
        case Some(user) => Ok(views.html.test(user.username))
        case None => Ok
      }
    )
  } 
}