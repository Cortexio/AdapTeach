package core

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core.commands._
import core.commands.CreateItemHandler._
import core.exceptions._

trait ApplicationLayer {
	def handle[C <: Command, O <: Outcome[C]](command: C, handler: CommandHandler[C, O]): Future[O]
}

trait SafetyLayer extends ApplicationLayer {
	val nextLayer: ApplicationLayer
}

object CoreLayer extends ApplicationLayer {
	def handle[C <: Command, O <: Outcome[C]](command: C, handler: CommandHandler[C, O]): Future[O] = {
		 handler.handle(command)
	}
}

object CommandHandlers {

	def handle[C <: Command, O <: Outcome[C]](command: C)(implicit handler: CommandHandler[C, O]): Future[O] = {
		ValidationLayer.handle(command, handler)
	}
	
	private object ValidationLayer extends SafetyLayer {
		val nextLayer = SecurityLayer
		def handle[C <: Command, O <: Outcome[C]](command: C, handler: CommandHandler[C, O]): Future[O] = command match {
			case c: CreateItem => nextLayer.handle(command, handler) // TODO Check user is logged in
			case _ => nextLayer.handle(command, handler)
		}
	}

	private object SecurityLayer extends SafetyLayer {
		val nextLayer = CoreLayer
		def handle[C <: Command, O <: Outcome[C]](command: C, handler: CommandHandler[C, O]): Future[O] = command match {
			case c: CreateItem => nextLayer.handle(command, handler) // TODO Check user is logged in
			case _ => nextLayer.handle(command, handler)
		}
	}

}