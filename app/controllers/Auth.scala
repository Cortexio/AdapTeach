package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.data._
import play.api.data.Forms._

import play.api.Play.current
import play.api.i18n.Messages.Implicits._

import org.joda.time.DateTime

import play.api.libs.functional._
import play.api.libs.functional.syntax._

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs._
import play.api.libs.json._
import reactivemongo.bson._
import scala.util.{ Failure, Success }

import dataAccess.{UserRepo}
import models.{User}

import org.mindrot.jbcrypt.{BCrypt}

class Auth extends CommonController {
 
  val signinForm = Form (
    tuple  (
      "username" ->  text.verifying("Enter your last name", !_.trim.isEmpty),
      "password" -> longPassword
    )
  )

   def signin() = Action.async { implicit req =>
    signinForm.bindFromRequest.fold(
      {
        case errors =>
          Future.successful(BadRequest(Json.obj("errors" -> errors.errorsAsJson)))
      }, 
      {
        case (username, password) =>
          for(
            maybeUser <- UserRepo.signin(username, password)
          ) yield (
            maybeUser match {
              case Some(user) => Ok(Json.obj("session" -> user)).withSession("user" -> Json.stringify(User.toJson(user)))
              case None => BadRequest(Json.obj("errors" -> Json.obj("username" -> "Authentication failed !")))
            }
          )
      }
    )
  }

  val signupForm = Form (
    tuple(
      "firstname" -> text.verifying("Enter your first name", !_.trim.isEmpty),
      "lastname" -> text.verifying("Enter your last name", !_.trim.isEmpty),
      "email" -> strictEmail,
      "username" ->  text.verifying("Enter your last name", !_.trim.isEmpty),
      "password" -> longPassword
    )
  )

  def signup() = Action.async { implicit req =>
    signupForm.bindFromRequest.fold(
      {
        case errors =>
          Future.successful(BadRequest(Json.obj("errors" -> errors.errorsAsJson)))
      }, 
      {
        case (firstname, lastname, email, username, password) =>
        val bsonId = BSONObjectID.generate
        val hash = BCrypt.hashpw(password, BCrypt.gensalt())
        val user = User(bsonId, firstname, lastname, email, username, hash)
        UserRepo.insert(user)
        Future.successful(Ok(Json.obj("session" -> user)).withSession("user" -> Json.stringify(User.toJson(user))))
      }
    )
  }

  def logout() = Action { request =>
    Ok(views.html.index()).withNewSession
  }

  def availableEmail(email: String) =  Action.async { implicit req =>
    for(
      maybeUser <- UserRepo.checkEmailAvailable(email)
    ) yield (
      maybeUser match {
        case Some(user) => Conflict
        case None => Ok
      }
    )
  }

  def availableUsername(username: String) =  Action.async { implicit req =>
    for(
      maybeUser <- UserRepo.checkUsernameAvailable(username)
    ) yield (
      maybeUser match {
        case Some(user) => Conflict
        case None => Ok
      }
    )
  }
}