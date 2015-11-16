package models

case class CompositeObjective (
  uuid: String,
  name: String,
  description: Option[String],
  subObjectives: Seq[Objective]
) extends Objective(uuid, name, description)