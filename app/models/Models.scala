package models

import reactivemongo.bson.{BSONObjectID}

case class User (
	id : BSONObjectID,
	username: String,
	email: String,
	firstName: String,
	lastName: String
	)

case class Category (
	id: BSONObjectID,
	name: String,
	parent: BSONObjectID
	)

case class Objective (
	id: BSONObjectID,
	name: String,
	description: Option[String],
	children: Seq[BSONObjectID],
	items: Seq[BSONObjectID]
	)

case class Item (
	id: BSONObjectID,
	name: String,
	description: Option[String],
	category: BSONObjectID
	)

case class Prerequisite (
	item: BSONObjectID,
	recall: Recall
	)

// TODO Replace this with Enum equivalent
class Recall()
case class ActiveRecall() extends Recall
case class PassiveRecall() extends Recall
case class NoRecall() extends Recall

class Assessment(
	testedItems: Seq[BSONObjectID],
	prerequisites: Seq[Prerequisite]
	)

class QuestionAssessment(
	testedItems: Seq[BSONObjectID],
	prerequisites: Seq[Prerequisite],
	question: Question
	) extends Assessment(testedItems, prerequisites)

case class YesOrNoQuestion(
	testedItems: Seq[BSONObjectID],
	prerequisites: Seq[Prerequisite],
	question: Question,
	correctAnswer: Boolean
	) extends QuestionAssessment(testedItems, prerequisites, question)

case class MCQ(
	testedItems: Seq[BSONObjectID],
	prerequisites: Seq[Prerequisite],
	question: Question,
	answers: Seq[Answer]
	) extends QuestionAssessment(testedItems, prerequisites, question)

class Question(
	question: String
	)

case class QuestionWithCode(
	question: String,
	code: Seq[Snippet]
	) extends Question(question)

case class Snippet(
	language: String,
	text: String
	) 

case class Answer(
	isCorrect: Boolean,
	text: String,
	testedItems: Seq[BSONObjectID]
	)