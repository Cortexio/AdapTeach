package core

import core.actions._

object ActionHandler {

	def handle(action: Action): Result = action match {
		case a: CreateItem => Result("123")
		case _ => Result("0")
	}

}