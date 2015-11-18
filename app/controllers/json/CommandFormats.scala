package controllers.json

import play.api.libs.json._

import core.commands._

object CommandFormats {
	implicit val createItemWrites: Writes[CreateItem] = Json.writes[CreateItem]
	implicit val createItemReads: Reads[CreateItem] = Json.reads[CreateItem]
}