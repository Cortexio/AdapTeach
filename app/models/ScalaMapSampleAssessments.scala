package models

object ScalaMapSampleAssessments {

	val assessment1a = YesOrNoQuestion(
			Seq( // Assessed items
				// Map is immutable
				),
			Seq( // Prerequisites
				// Creating a map with companion object (PassiveRecall)
				// Declaring a val (PassiveRecall)
				// Using the += operator on a Map (PassiveRecall) 
				), 
			QuestionWithCode(
				"Is the second instruction valid ?",
				Seq(Snippet("Java", """val salaries = Map("Bob" -> 1000, "John" -> 2000)"""),
					Snippet("Java", """salaries += ("Steve" -> 3000)"""))
				),
			false // Correct answer
			)

	val assessment1b = YesOrNoQuestion(
			Seq( // Assessed items
				// A val can't be reassigned
				),
			Seq( // Prerequisites
				// Creating a map with companion object (PassiveRecall)
				// Declaring a val (PassiveRecall)
				// Using the + operator on a Map (PassiveRecall)
				), 
			QuestionWithCode(
				"Is the second instruction valid ?",
				Seq(Snippet("Java", """val salaries = Map("Bob" -> 1000, "John" -> 2000)"""),
					Snippet("Java", """salaries = salaries + ("Steve" -> 3000)"""))
				),
			false
			)

	val assessment2a = MCQ(
			Seq( // Assessed items
				// Map is immutable
				// A var can be reassigned
				),
			Seq( // Prerequisites
				// Creating a map with companion object (PassiveRecall)
				// Declaring a var (PassiveRecall)
				// Using the += operator on a Map (PassiveRecall)
				// Using the + operator on a Map (PassiveRecall)
				), 
			QuestionWithCode(
				"Considering this code, is the following instruction valid ?",
				Seq(Snippet("Java", """var salaries = Map("Bob" -> 1000, "John" -> 2000)"""))
				),
			Seq( // Answers
				SimpleAnswer(false, """salaries += ("Steve" -> 3000)"""),
				SimpleAnswer(true, """salaries = salaries + ("Steve" -> 3000)""")
				)
			)

	val assessment2b = MCQ(
			Seq( // Assessed items
				// Map is immutable
				// A var can be reassigned
				),
			Seq( // Prerequisites
				// Creating a map with companion object (PassiveRecall)
				// Declaring a var (PassiveRecall)
				// Using the += operator on a Map (PassiveRecall)
				// Using the + operator on a Map (PassiveRecall)
				), 
			QuestionWithCode(
				"Considering this code, is the following instruction valid ?",
				Seq(Snippet("Java", """var salaries = Map("Bob" -> 1000, "John" -> 2000)"""))
				),
			Seq( // Answers
				SimpleAnswer(false, """salaries += ("Steve" -> 3000)"""),
				SimpleAnswer(true, """salaries = salaries + ("Steve" -> 3000)""")
				)
			)

/**

	val assessment3 = Assessment(
			Seq( // Tested items
				// a mutable Map's state can be modified
				// A val can't be reassigned
				),
			Seq( // Prerequisites
				// Creating a mutable map with companion object
				// Declaring a val
				// Using the += operator on a Map 
				// Using the + operator on a Map 
				), 
			YesOrNoQuestionWithCode(), // Kind
			QuestionWithCode(
				"Considering this code, which of the following instructions are valid ?",
				Code("Java", """val salaries = scala.collection.mutable.Map("Bob" -> 1000, "John" -> 2000)""")
				),
			Seq( // Answers
				Answer(true, """salaries += ("Steve" -> 3000)""", Seq()),
				Answer(false, """salaries = salaries + ("Steve" -> 3000)""", Seq())
				)
			)

	val assessment4 = Assessment(
			Seq( // Tested items
				// a mutable Map's state can be modified
				// A var can be reassigned
				),
			Seq( // Prerequisites
				// Creating a mutable map with companion object
				// Declaring a var
				// Using the += operator on a Map 
				// Using the + operator on a Map 
				), 
			YesOrNoQuestionWithCode(), // Kind
			QuestionWithCode(
				"Considering this code, which of the following instructions are valid ?",
				Code("Java", """var salaries = scala.collection.mutable.Map("Bob" -> 1000, "John" -> 2000)""")
				),
			Seq( // Answers
				Answer(true, """salaries += ("Steve" -> 3000)""", Seq()),
				Answer(true, """salaries = salaries + ("Steve" -> 3000)""", Seq())
				)
			)

			*/

		}