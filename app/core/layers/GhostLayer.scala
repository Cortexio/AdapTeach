package core.layers

import scala.concurrent.Future

import core.common._

trait GhostHandler[C <: Command, O <: Outcome[C]] extends CommandHandler[Layers.GHOST, C, O] {

}