package core.common

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core.common.Layers._

object App {

	class NoopFilter[L <: Layer, C <: Command] extends CommandFilter[L, C] {
		def filter(command: C) = pass(command)
	}
	
	def execute[C <: Command, O <: Outcome[C]](command: C)(implicit
		validation: CommandFilter[Validation, C] = new NoopFilter[Validation, C],
		security: CommandFilter[Security, C] = new NoopFilter[Security, C],
		consistency: CommandFilter[Consistency, C] = new NoopFilter[Consistency, C],
		handler: CommandHandler[C, O]
		) = for {
			v <- validation.filter(command)
			s <- security.filter(command)
			c <- consistency.filter(command)
			outcome <- handler.handle(command)
		} yield outcome

}