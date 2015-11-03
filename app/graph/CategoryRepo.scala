package graph

import scala.concurrent.Future

object CategoryRepo {

	def create(name: String): Future[String] = {
		Cypher.execute(s"""CREATE (n:Category {name: "$name"}) RETURN n""")
	}
	
}