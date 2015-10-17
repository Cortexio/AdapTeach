package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

import dataAccess._

class Application extends Controller {

  def index(url: String) = Action {
    Ok(views.html.index())
  }
}
