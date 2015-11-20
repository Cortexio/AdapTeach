package core.common

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core.layers._

object App {
	
	def execute[C <: Command, O <: Outcome[C]](command: C)(implicit
		ghost: CommandFilter[Layers.GHOST, C],
		core: CommandHandler[Layers.CORE, C, O]
		) = for {
			ghostOutcome <- ghost.filter(command)
			coreOutcome <- core.handle(command)
		} yield coreOutcome

}