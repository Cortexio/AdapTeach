package graph

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.ws.ning.NingAsyncHttpClientConfigBuilder
import scala.concurrent.Future

case class CypherResponse (
	results: Seq[CypherResult],
	errors: Seq[CypherError]
)

case class CypherResult (
	columns: Seq[String],
	data: Seq[CypherResultItem]
)

case class CypherResultItem (
	row: Seq[JsValue],
	graph: JsObject
)

case class CypherError (
	code: String,
	message: String
)

object Cypher {

	implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext

	implicit val cypherResultDataReads: Reads[CypherResultItem] = (
		(__ \ "row").read[Seq[JsValue]] and
		(__ \ "graph").read[JsObject]
	)(CypherResultItem.apply _)

	implicit val cypherResultReads: Reads[CypherResult] = (
		(__ \ "columns").read[Seq[String]] and
		(__ \ "data").read[Seq[CypherResultItem]]
	)(CypherResult.apply _)

	implicit val cypherErrorReads: Reads[CypherError] = (
		(__ \ "code").read[String] and
		(__ \ "message").read[String]
	)(CypherError.apply _)

	implicit val cypherResponseReads: Reads[CypherResponse] = (
		(__ \ "results").read[Seq[CypherResult]] and
		(__ \ "errors").read[Seq[CypherError]]
	)(CypherResponse.apply _)

	val url = "http://localhost:7474/db/data/transaction/commit"
	val backend = WS.url(url)
									.withAuth("neo4j", "password", WSAuthScheme.BASIC)

	def execute(statement:String, parameters: JsObject): Future[CypherResult] = {

		val data = Json.obj(
			"statements" -> Json.arr(
				Json.obj(
					"statement" -> statement,
					"parameters" -> parameters,
					"resultDataContents" -> Json.arr("row", "graph")
				)
			)
		)

		val futureResponse = backend.post(data)
		futureResponse map { response =>
			if (response.status != 200) {
				throw new Exception("Neo4j server answered : " + response.status + " - " + response.statusText)
			} else {
				val json: JsValue = response.json
				json.validate[CypherResponse] match {
					case s: JsSuccess[CypherResponse] => checkNoErrors(s.get)
					case e: JsError => throw new Exception("Unable to parse CypherResult JSON: " + JsError.toJson(e).toString)
				}
			}
		}
	}

	def checkNoErrors(response: CypherResponse): CypherResult = {
		if (response.errors.nonEmpty) throw new Exception("Cypher errors : " + response.errors)
		response.results(0) // Only one statement was sent
	}

}