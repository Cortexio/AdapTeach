package core.common 

import scala.concurrent.Future

trait CommandHandler[C <: Command, O <: Outcome[C]] {

	def handle(command: C): Future[O]

}