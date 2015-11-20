package controllers.json

import play.api.libs.json._

import models._

object ModelFormats {

	implicit val category = Json.format[Category]
	
	implicit val item = Json.format[Item]

	implicit val objective = Json.format[Objective]

}