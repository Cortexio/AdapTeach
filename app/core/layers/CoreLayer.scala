package core.layers

import scala.concurrent.Future

import core.common._

trait CoreHandler[C <: Command, O <: Outcome[C]] extends CommandHandler[Layers.CORE, C, O] {

}