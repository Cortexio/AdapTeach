package controllers.json

import play.api.libs.json._

import core.commands.CreateItem._

import controllers.json.ModelFormats._

object CommandFormats {

	implicit val createItemWrites: Writes[CreateItem] = Json.writes[CreateItem]
	implicit val createItemReads: Reads[CreateItem] = Json.reads[CreateItem]

	implicit val createItemOutcomeWrites: Writes[CreateItemOutcome] = Json.writes[CreateItemOutcome]
	implicit val createItemOutcomeReads: Reads[CreateItemOutcome] = Json.reads[CreateItemOutcome]

}