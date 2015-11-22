package core.common

import scala.concurrent.Future

trait Command

object Command {

	def handler[C <: Command, O <: Outcome[C]](f: C => Future[O]) = new CommandHandler[C, O] {
		def handle(command: C) = f(command)
	}

	def filter[L <: Layer, C <: Command](f: C => Future[C]) = new CommandFilter[L, C] {
		def filter(command: C) = f(command)
	}

}