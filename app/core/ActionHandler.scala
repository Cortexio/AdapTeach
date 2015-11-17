package core

import core.commands._

object CommandHandler {

	def handle(command: Command): Result = command match {
		case c: CreateItem => Result("123")
		case _ => Result("0")
	}

}