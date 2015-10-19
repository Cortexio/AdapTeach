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


import dataAccess.{User, UserRepo}

class Auth extends CommonController {
 
  val signinForm = Form(
    tuple(
      "username" ->  text.verifying("Enter your last name", !_.trim.isEmpty),
      "password" -> longPassword
    )
  )

  val signupForm = Form (
    tuple(
      "firstname" -> text.verifying("Enter your first name", !_.trim.isEmpty),
      "lastname" -> text.verifying("Enter your last name", !_.trim.isEmpty),
      "email" -> strictEmail,
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
          val futureMaybeUser: Future[Option[User]] = UserRepo.find(username)
          for(
            maybeUser <- futureMaybeUser
          ) yield (
            maybeUser match {
              case Some(user) => Ok(Json.obj("auth" -> "reussi")) //return user as json
              case None => BadRequest(Json.obj("errors" -> Json.obj("username" -> "Invalid username")))
            }
          )
      }
    )
  }

  def signup() = Action.async { implicit req =>
    signupForm.bindFromRequest.fold(
      {
        case errors =>
          Future.successful(BadRequest(Json.obj("errors" -> errors.errorsAsJson)))
      }, 
      {
        case (firstname, lastname, email, username, password) =>
          Future.successful(Ok(Json.obj("auth" -> "done")))
      }
    )
  }
}