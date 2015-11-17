package models

case class CompositeObjective (
  uuid: String,
  name: String,
  description: String,
  subObjectives: Seq[Objective]
)