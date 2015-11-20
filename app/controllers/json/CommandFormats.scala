package controllers.json

import play.api.libs.json._

import core.commands.CreateCategory._
import core.commands.CreateItem._

import controllers.json.ModelFormats._

object CommandFormats {

	implicit val createCategory = Json.format[CreateCategory]
	implicit val createCategoryOutcome = Json.format[CreateCategoryOutcome]

	implicit val createItem = Json.format[CreateItem]
	implicit val createItemOutcome = Json.format[CreateItemOutcome]

}