package controllers.json

import play.api.libs.json._

import core.commands.CreateCategory._
import core.commands.FindCategory._
import core.commands.CreateItem._
import core.commands.FindItem._

import models.Formats._

object CommandFormats {

	implicit val createCategory = Json.format[CreateCategory]
	implicit val createCategoryOutcome = Json.format[CreateCategoryOutcome]

	implicit val findCategoryOutcome = Json.format[FindCategoryOutcome]

	implicit val createItem = Json.format[CreateItem]
	implicit val createItemOutcome = Json.format[CreateItemOutcome]

	implicit val findItemOutcome = Json.format[FindItemOutcome]

}