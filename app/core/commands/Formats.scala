package core.commands

import play.api.libs.json._

object Formats {
	
	implicit val createItemWrites: Writes[CreateItem] = Json.writes[CreateItem]
	implicit val createItemReads: Reads[CreateItem] = Json.reads[CreateItem]

}