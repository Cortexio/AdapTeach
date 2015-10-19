package models

object ScalaRangeSampleAssessments {

	val assessment1 = MCQ(
			Seq( // Assessed items
				// Java > Create a range of integers
				),
			Seq( // Prerequisites
				), 
			SimpleQuestion("Which expression is evaluated to a Stream of integers ranging from 6 to 12 ?"),
			Seq(
				SnippetAnswer(true, Snippet("Java", "IntStream.rangeClosed(6, 12)")),
				SnippetAnswer(false, Snippet("Java", "IntStream.range(6, 12)")),
				SnippetAnswer(false, Snippet("Java", "IntStream.rangeClosed(6, 11)")),
				SnippetAnswer(false, Snippet("Java", "IntStream.range(6, 11)"))
				)
			)

	val assessment2 = MCQ(
			Seq( // Assessed items
				// Scala > Create a range of integers
				),
			Seq( // Prerequisites
				// Java > Create a range of integers (PassiveRecall)
				), 
			QuestionWithCode(
				"What is the Scala equivalent of the following Java code ?",
				Seq(Snippet("Java", "IntStream.rangeClosed(6, 12)"))
				),
			Seq(
				SnippetAnswer(true, Snippet("Scala", "6 to 12")),
				SnippetAnswer(false, Snippet("Scala", "6 to 11")),
				SnippetAnswer(false, Snippet("Scala", "6 until 12")),
				SnippetAnswer(false, Snippet("Scala", "6 until 11"))
				)
			)

}