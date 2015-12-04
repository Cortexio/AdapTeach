package models

import java.util.UUID

case class Item (
  uuid: UUID,
  name: String,
  description: Option[String],
  category: Category
)