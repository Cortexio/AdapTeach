package graph

import play.api.libs.json._
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.ws.ning.NingAsyncHttpClientConfigBuilder
import scala.concurrent.Future

object Cypher {

	implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext

	def execute(query:String) : Future[String] = {

		val url = "http://localhost:7474/db/data/transaction/commit"

		val body = s"""
				{
					"statements" : [ {
						"statement" : "$query"
					} ]
				}
			"""

		val futureResponse = WS.url(url)
													.withAuth("neo4j", "password", WSAuthScheme.BASIC)
													.withHeaders("Content-Type" -> "application/json")
													.post(body)
		futureResponse map { response =>
			response.body
		}
	}

}