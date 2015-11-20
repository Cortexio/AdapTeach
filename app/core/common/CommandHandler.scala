package core.common 

import scala.concurrent.Future

trait CommandHandler[L <: Layer, C <: Command, O <: Outcome[C]] {

	def handle(command: C): Future[O]

}