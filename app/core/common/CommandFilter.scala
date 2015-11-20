package core.common

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait CommandFilter[L <: Layer, C <: Command] {

	def filter(command: C): Future[C]

	def pass(command: C) = Future(command)

}