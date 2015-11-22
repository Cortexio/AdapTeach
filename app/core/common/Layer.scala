package core.common

import scala.concurrent.Future

trait Layer

object Layer {

	trait Validation extends Layer
	trait Security extends Layer
	trait Consistency extends Layer

}