package core.common

import scala.concurrent.Future

trait Layer

trait LayerCompanion[L <: Layer] {
	def filter[C <: Command](f: C => Future[C]) = new CommandFilter[L, C] {
		def filter(command: C) = f(command)
	}
}

object Layers {

	trait Validation extends Layer
	object Validation extends LayerCompanion[Validation]

	trait Security extends Layer
	object Security extends LayerCompanion[Security]

	trait Consistency extends Layer
	object Consistency extends LayerCompanion[Consistency]

}