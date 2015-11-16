package core.actions

case class CreateItem (
	name: String,
	description: Option[String],
	categoryId: String
)