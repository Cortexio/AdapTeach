package graph

import play.api.libs.json._
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.ws.ning.NingAsyncHttpClientConfigBuilder
import scala.concurrent.Future

object Cypher {

	implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext
	val url = "http://localhost:7474/db/data/transaction/commit"
	val backend = WS.url(url)
									.withAuth("neo4j", "password", WSAuthScheme.BASIC)

	def execute(query:String): Future[String] = {

		val data = Json.obj(
			"statements" -> Json.arr(
				Json.obj("statement" -> query)
			)
		)

		val futureResponse = backend.post(data)
		futureResponse map { response =>
			if (response.status != 200) "NEO4J ERROR : " + response.status
			else Json.prettyPrint(response.json)
		} recover {
			case e: Exception => "SERVER ERROR : " + e.getMessage
		}
	}

}