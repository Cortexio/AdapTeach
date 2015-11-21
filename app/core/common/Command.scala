package core.common

import scala.concurrent.Future

trait Command

object Command {

	def handler[C <: Command, O <: Outcome[C]](f: C => Future[O]) = new CommandHandler[C, O] {
		def handle(command: C) = f(command)
	}

}