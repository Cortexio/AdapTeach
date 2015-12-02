package controllers.common

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.Results._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json._
import play.api.libs.json.Json.toJson

import core.common._
import core.common.Layer._
import core.exceptions._

object Endpoint {

	def executeAs[C <: Command, O <: Outcome[C]](implicit
		reads: Reads[C],
		writes: Writes[O],
		validation: CommandFilter[Validation, C] = noop[Validation, C],
		security: CommandFilter[Security, C] = noop[Security, C],
		consistency: CommandFilter[Consistency, C] = noop[Consistency, C],
		handler: CommandHandler[C, O]
		) = json[C] { command =>
			Core.execute(command) map { outcome =>
				Ok(toJson(outcome))
			}
	}

	def execute[C <: Command, O <: Outcome[C]](command: C)(implicit
		writes: Writes[O],
		validation: CommandFilter[Validation, C] = noop[Validation, C],
		security: CommandFilter[Security, C] = noop[Security, C],
		consistency: CommandFilter[Consistency, C] = noop[Consistency, C],
		handler: CommandHandler[C, O]
		) = handle { request =>
			Core.execute(command) map { outcome =>
				Ok(toJson(outcome))
			}
	}
	
	private def noop[L <: Layer, C <: Command] = new CommandFilter[L, C] {
		def filter(command: C) = Future(command)
	}

	def json[E](block: E => Future[Result])(implicit reads: Reads[E]): Action[JsValue] = handle(parse.json) { request =>
		block(request.body.as[E](reads))
	}

  def handle(block: Request[AnyContent] => Future[Result]): Action[AnyContent] = handle(BodyParsers.parse.default)(block)

	def handle[A](bodyParser: BodyParser[A])(block: Request[A] => Future[Result]): Action[A] = Action.async(bodyParser) { request: Request[A] =>
    block(request)
    	.recover {
				case e: EntityNotFound => NotFound(e.getMessage)
				case e: ValidationException => BadRequest(e.getMessage)
			}
  }

}