package core.commands

import core.Command

case class CreateItem (
	name: String,
	description: String,
	categoryId: String
) extends Command