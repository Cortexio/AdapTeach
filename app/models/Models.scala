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
	dname: String,
	parent: BSONObjectID
	)

case class Objective (
	id: BSONObjectID,
	title: String,
	summary: String,
	children: Seq[BSONObjectID],
	items: Seq[BSONObjectID]
	)

case class Item (
	id: BSONObjectID,
	title: String,
	description: Option[String],
	category: BSONObjectID
	)

case class Preq (
	id: String,
	mandatory: Boolean,
	item: Item,
	active: Boolean
	)

class Assessment(
	testedItems: Seq[Item],
	preqs: Seq[Preq]
	)

class QuestionAssessment(
	testedItems: Seq[Item],
	preqs: Seq[Preq],
	question: Question
	) extends Assessment(testedItems, preqs)

case class YesOrNoQuestion(
	testedItems: Seq[Item],
	preqs: Seq[Preq],
	question: Question,
	correctAnswer: Boolean
	) extends QuestionAssessment(testedItems, preqs, question)

case class MCQ(
	testedItems: Seq[Item],
	preqs: Seq[Preq],
	question: Question,
	answers: Seq[Answer]
	) extends QuestionAssessment(testedItems, preqs, question)

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