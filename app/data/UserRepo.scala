package dataAccess

import play.api.Logger

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs.iteratee.Iteratee

import reactivemongo.bson.BSONDocument
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.WriteResult
import scala.util.{ Failure, Success }
import reactivemongo.core.commands._

import org.mindrot.jbcrypt.{BCrypt}

import models.User

object UserRepo {
  private val collection: BSONCollection = DB.getCollection("users")

  def find(username: String) : Future[Option[User]] = {
    val query = BSONDocument("username" -> username)
    collection.find(query).one[User]
  }

  def checkUsernameAvailable(username: String) : Future[Option[User]] = {
    println("username " + username)

    val query = BSONDocument("username" -> username)
    collection.find(query).one[User]
  }

  def checkEmailAvailable(email: String) : Future[Option[User]] = {
    println("email " + email)
    val query = BSONDocument("email" -> email)
    collection.find(query).one[User]
  }

  def insert(user: User) : Unit = {
    collection.insert(user).onComplete {
      case Failure(e) => throw e
      case Success(writeResult) =>
        Logger.info(s"successfully inserted document with result: $writeResult")
    }
  }

  def signin(username: String, password: String) : Future[Option[User]] = {
    val query = BSONDocument("username" -> username)
    for(
      maybeUser <- collection.find(query).one[User]
    ) yield(
      maybeUser match {
        case Some(user) =>
          if(BCrypt.checkpw(password, user.password)) {
            Some(user)
          } else {
            Logger.warn("This user has been found but invalid password " + username)
            None
          }
        case None =>
          Logger.warn("this is not a valid user " + username)
          None
      }
    )
  }
}