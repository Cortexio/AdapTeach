package core.commands

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import core.common._

object DoSomething {
	def doIt() = App.execute(DoSomething())
	case class DoSomething () extends Command
	case class DoSomethingOutcome () extends Outcome[DoSomething]
	implicit val handler = Command.handler( (command: DoSomething) => {
		Future(DoSomethingOutcome())
	})
	implicit val filter = Command.filter[Layer.Validation, CreateItem]( (command) => {
		// Throw validation error if necessary
		Future(command)
	})
}
