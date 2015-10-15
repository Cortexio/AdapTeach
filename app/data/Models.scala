package dataAccess 

import reactivemongo.bson._

case class User(username: String, password: String)

object User {

	implicit val userReader: BSONDocumentReader[User] = Macros.reader[User]
	implicit val userWriter: BSONDocumentWriter[User] = Macros.writer[User]
}