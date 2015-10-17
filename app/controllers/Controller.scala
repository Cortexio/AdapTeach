package controllers

import play.api._
import play.api.{ data => PlayForm }

object Controller {

  val rxemail = """.+\@.+\..+""".r
  def strictEmail = {
    PlayForm.Forms.text
         .verifying(PlayForm.validation.Constraints.pattern(rxemail, error = "Enter your email address"))
         .transform[String](_.toLowerCase, identity[String])
  }

  def longPassword: PlayForm.Mapping[String] = longPassword(5)
  def longPassword(size: Int): PlayForm.Mapping[String] = {
    PlayForm.Forms.text.verifying("Enter a longer password", (s: String) => s.trim.size > size)
  }
}