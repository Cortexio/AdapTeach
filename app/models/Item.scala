package models

case class Item (
  uuid: String,
  name: String,
  description: String,
  category: Category
)