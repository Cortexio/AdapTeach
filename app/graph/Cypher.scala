package graph

import dispatch._, Defaults._

object Cypher {

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