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

case class CypherStatementResult (
	rows: Seq[JsValue]
)

case class CypherResult (
	columns: Seq[String],
	statements: Seq[CypherStatementResult]
)

case class CypherError (
	code: String,
	message: String
)

object Cypher {

	implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext

	implicit val cypherErrorReads: Reads[CypherError] = (
		(__ \ "code").read[String] and
		(__ \ "message").read[String]
	)(CypherError.apply _)

	implicit val cypItemReader: Reads[CypherStatementResult] = Json.reads[CypherStatementResult] <~ (__ \ "row").read(Reads.seq[JsValue])

	implicit val cypResultReader: Reads[CypherResult] = (
		(__ \ "columns").read(Reads.seq[String]) and
		(__ \ "data").read(Reads.seq[CypherStatementResult])
	) apply (CypherResult.apply _)

	implicit val cypherResponseReader: Reads[CypherResponse] = (
		(__ \ "results").read(Reads.seq[CypherResult]) and
		(__ \ "errors").read(Reads.seq[CypherError])
	) apply (CypherResponse.apply _)

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
				cypherResponseReader.reads(response.json) match {
					case JsSuccess(response, _) => checkNoErrors(response)
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