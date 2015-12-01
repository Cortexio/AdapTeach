package controllers.common

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.Results._

import core.exceptions.EntityNotFound

object Endpoint {
	
	def handle[A](bodyParser: BodyParser[A])(block: Request[A] => Future[Result]): Action[A] = Action.async(bodyParser) { request: Request[A] =>
    block(request)
    	.recover {
				case e: EntityNotFound => NotFound(e.getMessage)
			}
  }

  def handle(block: Request[AnyContent] => Future[Result]): Action[AnyContent] = handle(BodyParsers.parse.default)(block)

}