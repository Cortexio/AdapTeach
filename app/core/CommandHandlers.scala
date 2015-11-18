package core

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core.commands._
import core.commands.CreateItemHandler._
import core.exceptions._

trait SafetyLayer {
	def handle[C <: Command, O <: Outcome[C]](command: C)(implicit handler: CommandHandler[C, O]): Future[O]
}

object CommandHandlers {

	def handle[C <: Command, O <: Outcome[C]](command: C)(implicit handler: CommandHandler[C, O]): Future[O] = {
		Security.handle(command)
	}
	
	private object Security extends SafetyLayer {
		def handle[C <: Command, O <: Outcome[C]](command: C)(implicit handler: CommandHandler[C, O]): Future[O] = command match {
			case c: CreateItem => handler.handle(command) // TODO Check user is logged in
			case _ => handler.handle(command)
		}
	}
	

}