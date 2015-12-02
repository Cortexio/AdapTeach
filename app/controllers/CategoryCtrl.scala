package controllers

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json.Json

import controllers.common.Endpoint
import core.common.Core.execute
import core.commands.CreateCategory._
import core.commands.FindCategory._
import core.exceptions.EntityNotFound
import graph.CategoryRepo
import models.Category
import models.Formats._

class CategoryCtrl extends Controller {

	implicit val createCategory = Json.reads[CreateCategory]
	implicit val createCategoryOutcome = Json.writes[CreateCategoryOutcome]

	def create() = Endpoint.executeAs[CreateCategory, CreateCategoryOutcome]

	implicit val findCategoryOutcome = Json.writes[FindCategoryOutcome]

	def find(uuid: String) = Endpoint.execute[FindCategory, FindCategoryOutcome](FindCategory(uuid))

}