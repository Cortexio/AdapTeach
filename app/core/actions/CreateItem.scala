package core.actions

import core.Action

case class CreateItem (
	name: String,
	description: String,
	categoryId: String
) extends Action