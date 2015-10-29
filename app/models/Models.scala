package models

import play.api.Logger

import reactivemongo.bson._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson.BSONDocument
import reactivemongo.api.collections.bson.BSONCollection
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class User (
  _id : BSONObjectID,
  firstname: String,
  lastname: String,
  email: String,
  username: String,
  password: String
) {
	def id : String = _id.stringify
}

object User {
	implicit val userBsonReader: BSONDocumentReader[User] = Macros.reader[User]
	implicit val userBsonWriter: BSONDocumentWriter[User] = Macros.writer[User]

	implicit val userJsonReader : Reads[User] =
			(
				(__ \ "id").read[String] and
				(__ \ "firstname").read[String] and
				(__ \ "lastname").read[String] and
				(__ \ "email").read[String] and
				(__ \ "username").read[String] and
				(__ \ "password").read[String]
			)(User.toObject _)

	implicit val userJsonWrite : OWrites[User] = OWrites { u =>
		Json.obj (
			"id" -> u.id,
			"firstname" -> u.firstname,
			"lastname" -> u.lastname,
			"email" -> u.email,
			"username" -> u.username,
			"password" -> u.password
		)
	}

	def toObject(id: String, firstname: String, lastname: String, email: String, username: String, password: String) = {
		println("USER ID ",id)
		User(BSONObjectID(id), firstname, lastname, email, username, password)
	}

	def fromJson(userAsJson: JsValue) : Option[User] = {
		userJsonReader.reads(userAsJson) match {
			case JsSuccess(user, _) =>
				Some(user)
			case e: JsError =>
				Logger.warn("this is not a valid user " + e)
				None
		}
	}

	def toJson(user: User) : JsValue = {
		userJsonWrite.writes(user)
	}
}

case class Category (
  id: String,
  dname: String,
  parent: Category
)

case class Objective (
  id: String,
  title: String,
  summary: String,
  children: Seq[Objective]
)

case class Item (
  id: String,
  title: String,
  summary: Option[String],
  parentCategory: Category,
  parentObjectives: Seq[Objective]
)

case class Preq (
  id: String,
  mandatory: Boolean,
  item: Item,
  active: Boolean
)

case class Assessment(
  testedItems: Seq[Item],
  preqs: Seq[Preq]

)