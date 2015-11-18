package core

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait CommandHandler[C <: Command, O <: Outcome[C]] {

	def handle(command: C): Future[O]

}