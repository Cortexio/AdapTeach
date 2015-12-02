package controllers

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json.Json.toJson

import controllers.common.Endpoint
import core.common.Core.execute
import core.commands.CreateCategory._
import core.commands.FindCategory._
import core.exceptions.EntityNotFound
import graph.CategoryRepo
import models.Category
import models.Formats._
import controllers.json.CommandFormats._

class CategoryCtrl extends Controller {

	def create() = Endpoint.executeAs[CreateCategory, CreateCategoryOutcome]

	def find(uuid: String) = Endpoint.execute[FindCategory, FindCategoryOutcome](FindCategory(uuid))

}