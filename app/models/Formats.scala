package models

import play.api.libs.json._

object Formats {
	
	implicit val categoryFormat = Json.format[Category]

	implicit val objectiveFormat = Json.format[Objective]

	implicit val itemFormat = Json.format[Item]

}