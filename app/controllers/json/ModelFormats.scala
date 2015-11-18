package controllers.json

import play.api.libs.json._

import models._

object ModelFormats {

	implicit val categoryWrites: Writes[Category] = Json.writes[Category]
	implicit val categoryReads: Reads[Category] = Json.reads[Category]
	
	implicit val itemWrites: Writes[Item] = Json.writes[Item]
	implicit val itemReads: Reads[Item] = Json.reads[Item]

}