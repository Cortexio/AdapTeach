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
      "username" ->  text.verifying(Json.obj("key" -> "form_error_mandatory", "params" -> Json.obj("field" -> "username")).toString, !_.trim.isEmpty),
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
              case None => Forbidden(Json.obj("errors" -> Json.obj("global" -> Json.obj("key" -> "authentication_fail"))).toString)
            }
          )
      }
    )
  }

  val signupForm = Form (
    tuple(
      "firstname" -> text.verifying(Json.obj("key" -> "form_error_mandatory", "params" -> Json.obj("field" -> "first name")).toString, !_.trim.isEmpty),
      "lastname" -> text.verifying(Json.obj("key" -> "form_error_mandatory", "params" -> Json.obj("field" -> "last name")).toString, !_.trim.isEmpty),
      "email" -> strictEmail,
      "username" ->  text.verifying(Json.obj("key" -> "form_error_mandatory", "params" -> Json.obj("field" -> "username")).toString, !_.trim.isEmpty),
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

        for(
          maybeAvailableEmailUser <- UserRepo.checkEmailAvailable(email);
          maybeAvailableUsernameUser <- UserRepo.checkUsernameAvailable(username)
        ) yield (
          maybeAvailableUsernameUser match {
            case Some(userUsername) =>
              val usernameNotAvailableMessage = Json.obj("key" -> "form_error_unavailable", "params" -> Json.obj("field" -> username)).toString
              Conflict(Json.obj("errors" -> Json.obj("username" -> usernameNotAvailableMessage)))

            case None =>
              
              maybeAvailableEmailUser match {
                case Some(userEmail) =>
                  val emailNotAvailableMessage = Json.obj("key" -> "form_error_unavailable", "params" -> Json.obj("field" -> email)).toString
                  Conflict(Json.obj("errors" -> Json.obj("email" -> emailNotAvailableMessage)))

                case None =>
                  UserRepo.insert(user)
                  Ok(Json.obj("session" -> user)).withSession("user" -> Json.stringify(User.toJson(user)))
              }
          }
        )
      }
    )
  }

  def logout() = Action { request =>
    Ok(views.html.index()).withNewSession
  }

  def emailForm = Form (
    "email" -> strictEmail
  )

  def availableEmail() =  Action.async { implicit req =>
    emailForm.bindFromRequest.fold(
      {
        case errors =>
          Future.successful(BadRequest(Json.obj("errors" -> errors.errorsAsJson)))
      },
      {
        case(email) =>
          for(
            maybeUser <- UserRepo.checkEmailAvailable(email)
          ) yield (
            maybeUser match {
              case Some(user) => 
                val emailNotAvailableMessage = Json.obj("key" -> "form_error_unavailable", "params" -> Json.obj("field" -> email)).toString
                Conflict(Json.obj("errors" -> Json.obj("email" -> emailNotAvailableMessage)))
              
              case None => Ok
            }
          )
      }
    )
  }

  def usernameForm = Form (
    "username" ->  text.verifying(Json.obj("key" -> "form_error_mandatory", "params" -> Json.obj("field" -> "username")).toString, !_.trim.isEmpty)
  )

  def availableUsername() =  Action.async { implicit req =>
    usernameForm.bindFromRequest.fold(
      {
        case errors =>
          Future.successful(BadRequest(Json.obj("errors" -> errors.errorsAsJson)))
      },
      {
        case(username) =>
          for(
            maybeUser <- UserRepo.checkUsernameAvailable(username)
          ) yield (
            maybeUser match {
              case Some(user) => 
                val usernameNotAvailableMessage = Json.obj("key" -> "form_error_unavailable", "params" -> Json.obj("field" -> username)).toString
                Conflict(Json.obj("errors" -> Json.obj("username" -> usernameNotAvailableMessage)))
              
              case None => Ok
            }
          )
      }
    )
  }
}