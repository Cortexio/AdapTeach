package models

import play.api.libs.json._

object Formats {
	
	implicit val categoryWrites: Writes[Category] = Json.writes[Category]
	implicit val categoryReads: Reads[Category] = Json.reads[Category]

	implicit val objectiveWrites: Writes[Objective] = Json.writes[Objective]
	implicit val objectiveReads: Reads[Objective] = Json.reads[Objective]

	implicit val itemWrites: Writes[Item] = Json.writes[Item]
	implicit val itemReads: Reads[Item] = Json.reads[Item]

}