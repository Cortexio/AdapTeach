package core.common

import scala.concurrent.Future

trait CommandFilter[L <: Layer, C <: Command] {

	def filter(command: C): Future[C]

}