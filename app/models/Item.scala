package models

case class Item (
  uuid: String,
  name: String,
  description: Option[String],
  category: Category
) extends Objective(uuid, name, description)