package controllers

import play.api._
import play.api.{ data => PlayForm }
import play.api.mvc._
import play.api.libs.json._
import play.api.mvc.Results._

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import models.{User}

abstract class CommonController extends Controller {

  val rxemail = """.+\@.+\..+""".r
  def strictEmail = {
    PlayForm.Forms.text
         .verifying(PlayForm.validation.Constraints.pattern(rxemail, error = "Enter a valid email address"))
         .transform[String](_.toLowerCase, identity[String])
  }

  def longPassword: PlayForm.Mapping[String] = longPassword(5)
  def longPassword(size: Int): PlayForm.Mapping[String] = {
    PlayForm.Forms.text.verifying("Enter a longer password", (s: String) => s.trim.size > size)
  }
} 

class AuthenticatedRequest[A](val user: User, request: Request[A]) extends WrappedRequest[A](request)

object WithSession extends ActionBuilder[AuthenticatedRequest] {
  def invokeBlock[A](request: Request[A], block: (AuthenticatedRequest[A]) => Future[Result]) = {
    request.session.get("user") match {
      case Some(user) => block(new AuthenticatedRequest(User.fromJson(Json.parse(user)).get, request))
      case None => Future.successful(Forbidden)
    }
  }
}

class MaybeAuthenticatedRequest[A](val user: Option[User], request: Request[A]) extends WrappedRequest[A](request)

object WithMaybeSession extends ActionBuilder[MaybeAuthenticatedRequest] {
  def invokeBlock[A](request: Request[A], block: (MaybeAuthenticatedRequest[A]) => Future[Result]) = {
    request.session.get("user") match { 
      case Some(user) => block(new MaybeAuthenticatedRequest(Some(User.fromJson(Json.parse(user)).get), request))
      case None => block(new MaybeAuthenticatedRequest(None, request))
    }
  }
}