package models

import java.util.UUID

case class Objective (
	uuid: UUID,
	name: String,
	description: Option[String]
)