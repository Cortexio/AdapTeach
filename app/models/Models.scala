package models

import reactivemongo.bson._
import org.joda.time.{DateTime}

case class User (
	_id : BSONObjectID,
	username: String,
	email: String,
	firstName: String,
	lastName: String,
	learnerProfile: LearnerProfile
	)

case class LearnerProfile (
	objectives: Seq[BSONObjectID],
	items: Seq[ItemProgress],
	history: Seq[AssessmentEvent]
	)

case class ItemProgress (
	item: BSONObjectID,
	nextRepetition: DateTime,
	consolidationRate: Double
	)

case class AssessmentEvent (
	date: DateTime,
	assessment: BSONObjectID,
	outcome: Boolean
	)

case class Category (
	_id: BSONObjectID,
	name: String
	// parent: Option[BSONObjectID]
	)
object Category {
	implicit val categoryReader: BSONDocumentReader[Category] = Macros.reader[Category]
	implicit val categoryWriter: BSONDocumentWriter[Category] = Macros.writer[Category]
}

case class Objective (
	_id: BSONObjectID,
	name: String,
	description: Option[String],
	children: Seq[BSONObjectID],
	items: Seq[BSONObjectID]
	)

case class Item (
	_id: BSONObjectID,
	name: String,
	description: Option[String],
	category: BSONObjectID
	)

object Recall extends Enumeration {
	val Active, Passive, None = Value
}

case class Prerequisite (
	item: BSONObjectID,
	recall: Recall.Value
	)

class Assessment (
	assessedItems: Seq[BSONObjectID],
	prerequisites: Seq[Prerequisite]
	)

class QuestionAssessment (
	assessedItems: Seq[BSONObjectID],
	prerequisites: Seq[Prerequisite],
	question: Question
	) extends Assessment(assessedItems, prerequisites)

case class YesOrNoQuestion (
	assessedItems: Seq[BSONObjectID],
	prerequisites: Seq[Prerequisite],
	question: Question,
	correctAnswer: Boolean
	) extends QuestionAssessment(assessedItems, prerequisites, question)

case class MCQ (
	assessedItems: Seq[BSONObjectID],
	prerequisites: Seq[Prerequisite],
	question: Question,
	answers: Seq[Answer]
	) extends QuestionAssessment(assessedItems, prerequisites, question)

class Question (
	question: String
	)

case class SimpleQuestion (
	question: String
	) extends Question(question)

case class QuestionWithCode (
	question: String,
	code: Seq[Snippet]
	) extends Question(question)

case class Snippet (
	language: String,
	text: String
	) 

class Answer (
	isCorrect: Boolean
	)

case class SimpleAnswer (
	isCorrect: Boolean,
	text: String
	) extends Answer(isCorrect)

case class SnippetAnswer (
	isCorrect: Boolean,
	snippet: Snippet
	) extends Answer(isCorrect)