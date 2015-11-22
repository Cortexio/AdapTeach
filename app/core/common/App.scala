package core.common

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core.common.Layer._

object App {

	def execute[C <: Command, O <: Outcome[C]](command: C)(implicit
		validation: CommandFilter[Validation, C] = noop[Validation, C],
		security: CommandFilter[Security, C] = noop[Security, C],
		consistency: CommandFilter[Consistency, C] = noop[Consistency, C],
		handler: CommandHandler[C, O]
		) = for {
			valid <- validation.filter(command)
			secure <- security.filter(valid)
			consistent <- consistency.filter(secure)
			outcome <- handler.handle(consistent)
		} yield outcome

	private def noop[L <: Layer, C <: Command] = new CommandFilter[L, C] {
		def filter(command: C) = Future(command)
	}

}