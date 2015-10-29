package dataAccess

import reactivemongo.api._
import scala.concurrent.ExecutionContext.Implicits.global

object DB {

	private val driver = new MongoDriver
	private val db = driver.connection(List("localhost"))("adapteach")

	def getCollection(name: String) = {
		db(name)
	}
}