package core.exceptions

case class ValidationException(message: String) extends Exception(message)