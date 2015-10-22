package models

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import reactivemongo.bson._
import reactivemongo.api.collections.bson.BSONCollection

import dataAccess.DB

object CategoryRepo {
	private val collection: BSONCollection = DB.getCollection("category")

	def create(name: String) : Future[Category] = {
		val id = BSONObjectID.generate
		val category = Category(id, name)
		collection.insert(category) flatMap { res =>
			for {
				maybeCat: Option[Category] <- collection.find(BSONDocument("_id" -> id)).one[Category]
				cat: Category = maybeCat.getOrElse(throw new RuntimeException())
			} yield cat
		}
	}

  //	def find(username: String) : Future[Option[Category]] = {
  //	  val query = BSONDocument("username" -> username)
  //	  collection.find(query).one[Category]
  //	}

}