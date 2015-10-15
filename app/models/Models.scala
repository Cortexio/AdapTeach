package models

case class User (
  id : String,
  username: String,
  email: String,
  firstName: String,
  lastName: String
)

case class Category (
  id: String,
  dname: String,
  parent: Category
)

case class Objective (
  id: String,
  title: String,
  summary: String,
  children: Seq[Objective]
)

case class Item (
  id: String,
  title: String,
  summary: Option[String],
  parentCategory: Category,
  parentObjectives: Seq[Objective]
)

case class Preq (
  id: String,
  mandatory: Boolean,
  item: Item,
  active: Boolean
)

case class Assessment(
  testedItems: Seq[Item],
  preqs: Seq[Preq]

)