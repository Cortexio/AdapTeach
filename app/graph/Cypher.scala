package graph

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.ws.ning.NingAsyncHttpClientConfigBuilder
import scala.concurrent.Future

case class Neo4jResponse (
	results: Seq[Neo4jResult],
	errors: Seq[Neo4jError]
)

case class Neo4jResult (
	columns: Seq[String],
	data: Seq[Neo4jResultElement]
) {
	def asCypherResult: CypherStatementResult = {
		val elements = for {
			element <- data
			i <- 0 until columns.size
		} yield (columns(i), element.row(i))
		CypherStatementResult(elements)
	}
}

case class Neo4jResultElement (
	row: Seq[JsValue]
)

case class Neo4jError (
	code: String,
	message: String
)

case class CypherStatementResult(
	elements: Seq[Tuple2[String, JsValue]]
)

object Cypher {

	implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext

	implicit val cypherResultDataReads: Reads[Neo4jResultElement] =
		Json.reads[Neo4jResultElement] <~ (__ \ "row").read(Reads.seq[JsValue])

	implicit val cypherResultReads: Reads[Neo4jResult] = (
		(__ \ "columns").read[Seq[String]] and
		(__ \ "data").read[Seq[Neo4jResultElement]]
	)(Neo4jResult.apply _)

	implicit val cypherErrorReads: Reads[Neo4jError] = (
		(__ \ "code").read[String] and
		(__ \ "message").read[String]
	)(Neo4jError.apply _)

	implicit val cypherResponseReads: Reads[Neo4jResponse] = (
		(__ \ "results").read[Seq[Neo4jResult]] and
		(__ \ "errors").read[Seq[Neo4jError]]
	)(Neo4jResponse.apply _)

	val url = "http://localhost:7474/db/data/transaction/commit"
	val backend = WS.url(url)
									.withAuth("neo4j", "password", WSAuthScheme.BASIC)

	def execute(statement:String, parameters: JsObject): Future[CypherStatementResult] = {

		val data = Json.obj(
			"statements" -> Json.arr(
				Json.obj(
					"statement" -> statement,
					"parameters" -> parameters
				)
			)
		)

		val futureResponse = backend.post(data)
		futureResponse map { response =>
			if (response.status != 200) {
				throw new Exception("Neo4j server answered : " + response.status + " - " + response.statusText)
			} else {
				val json: JsValue = response.json
				json.validate[Neo4jResponse] match {
					case s: JsSuccess[Neo4jResponse] => checkNoErrors(s.get).asCypherResult
					case e: JsError => throw new Exception("Unable to parse Neo4jResult JSON: " + JsError.toJson(e).toString)
				}
			}
		}
	}

	def checkNoErrors(response: Neo4jResponse): Neo4jResult = {
		if (response.errors.nonEmpty) throw new Exception("Neo4j errors : " + response.errors)
		response.results(0) // Only one statement was sent
	}

}