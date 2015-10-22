package dataAccess

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.bson.BSONDocument

import play.api.libs.iteratee.Iteratee

import reactivemongo.bson.BSONDocument
import reactivemongo.api.collections.bson.BSONCollection

object UserRepo {
  private val collection: BSONCollection = DB.getCollection("users")

  def find(username: String) : Future[Option[User]] = {
    val query = BSONDocument("username" -> username)
    collection.find(query).one[User]
  }
}