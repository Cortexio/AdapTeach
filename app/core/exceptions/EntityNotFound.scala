package core.exceptions

case class EntityNotFound(message: String) extends Exception(message)