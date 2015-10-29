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

        for(
          maybeAvailableEmailUser <- UserRepo.checkEmailAvailable(email);
          maybeAvailableUsernameUser <- UserRepo.checkUsernameAvailable(username)
        ) yield (
          maybeAvailableUsernameUser match {
            case Some(userUsername) =>
              val usernameNotAvailableMessage = username + " is not available"
              Conflict(Json.obj("errors" -> Json.obj("username" -> usernameNotAvailableMessage)))

            case None =>
              
              maybeAvailableEmailUser match {
                case Some(userEmail) =>
                  val emailNotAvailableMessage = email + " is not available"
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
    "email" -> text
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
                val notAvailableMessage = email + " is not available"
                Conflict(Json.obj("errors" -> Json.obj("email" -> notAvailableMessage)))
              
              case None => Ok
            }
          )
      }
    )
  }

  def usernameForm = Form (
    "username" -> text.verifying("Enter your username", !_.trim.isEmpty)
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
                val notAvailableMessage = username + " is not available"
                Conflict(Json.obj("errors" -> Json.obj("username" -> notAvailableMessage)))
              
              case None => Ok
            }
          )
      }
    )
  }
}