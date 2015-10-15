package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

class Application extends Controller {

  def index(url: String) = Action {
    Ok(views.html.index("Hello World!"))
  }

  def test = Action { implicit req =>
    Ok
  }

}
