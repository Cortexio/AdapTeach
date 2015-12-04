package models

import java.util.UUID

case class CompositeObjective (
  uuid: UUID,
  name: String,
  description: Option[String],
  subObjectives: Seq[Objective]
)