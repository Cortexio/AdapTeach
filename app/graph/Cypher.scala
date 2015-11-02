package graph

import play.api.libs.json._
import dispatch._, Defaults._

case class Car (
	model: String,
	color: String
)



object Cypher {

	implicit val carReader: Reads[Car] = (
	  (__ \ "color").read[String] and
	  (__ \ "model").read[String]
	) (Car.apply _)

	private def parse(jsonString: String) = {
	  val jsonJsValue = Json.parse(jsonString)
	  jsonJsValue.as[Car]
	}

	object CarJsonDeserializer extends (com.ning.http.client.Response => Car) {
		override def apply(response: com.ning.http.client.Response): Car = {
			(dispatch.as.String andThen (jsonString => parse(jsonString)))(response)
		}
	}

	val endpoint = url("http://localhost:7474/db/data/transaction/commit")
									.POST
									.as_!("neo4j", "password")
									.setContentType("application/json", "UTF-8")

	def execute(query:String) : Future[String] = {
		val request = endpoint.setBody(s"""
				{
					"statements" : [ {
						"statement" : "$query"
					} ]
				}
			""")
		val futureResponse: Future[Either[Throwable, String]] = Http(request OK as.String).either
		val result: Future[String] = futureResponse map { either =>
			if (either.isLeft) either.left.get.getMessage
			else either.right.get
		}
		result
	}

}