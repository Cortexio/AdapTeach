# ADAPTEACH

### Version
0.1.0

### Getting Started

- Go to the root of the project adapteach

You need to install all the node dependencies :
```sh
$ npm install
```
Run the default gulp task to watch jsx and sass files :
```sh
$ gulp
```

##### For Windows
Run Activator :
```sh
$ activator
[adapteach] $ run
```
##### For Mac
Run Activator :
```sh
$ ./activator
[adapteach] $ run
```

### Guidelines

##### Create a new Feature

To create a new feature, you need to create a dedicated branch first.
- Go to exp branch
```sh
$ git checkout exp
```
- create a new local branch:
```sh
$ git checkout -b <branch-name>
```
- add modified files to your next commit (in this case you'll add all the edited files)
```sh
$ git add -A
```
- Commit
```sh
$ git commit -m "<your commit message>"
```
- Push to the remote repository on a new branch remote (usually you pick the same name as your local branch)
```sh
$ git push -u "<your remote branch name>"
```

Now, you can merge the "exp" branch before submitting a pull request

- fetch data from origin to update your remote tracking
```sh
$ git fetch origin exp
```
- merge branch "origin/exp" into your local branch
```sh
$ git merge origin/exp
```

Finally, just go on github and make your pull request from your remote branch to the exp branch.

##### Manage Translations (i18n)

In the folder `public`, you can find a folder `i18n` with multiple files as `messages_<locale>.json`.  
A variable `i18n` is injected in the window to access the i18n engine instance.  
there are different cases:

> SIMPLE TRANSLATION
>> key exemple in the json translation file
```sh
{
  translation_key: "value"
}
```
>> source code to use it
```sh
i18n.t("translation_key")
=> "value"
```
- - - -

> INTERPOLATION
>> key exemple in the json translation file
```sh
{
  translation_key: "translation %{variable}"
}
```
>> source code to use it
```sh
i18n.t("translation_key", {variable: "value"})
=> "translation value"
```
- - - -

> PLURALIZATION
>> key exemple in the json translation file
```sh
{
  translation_key: "%{smart_count} value |||| %{smart_count} values",
}
```
>> source code to use it
```sh
i18n.t("translation_key", {smart_count: 0});
=> "values"
i18n.t("translation_key", {smart_count: 1});
=> "value"
i18n.t("translation_key", {smart_count: 2});
=> "values"
```
- - - -

> SPECIFIC SERVER CASE  
If you want to create a message on the server side and use the i18n engine from the client, you have to follow these instructions:
Create your translation in the `messages_<locale>.json`
>> On the server side, your message must have the following structure
```
{
  key: "translation_key",
  params: {
    any_key_you_want: "param value"
  }
}
```
`KEY` and `PARAMS` are keywords here. You must use it. the other keys are completely your call.
>> you will have this kind of source code to do this (exemple of a json response in scala Action)
```scala
Ok(Json.obj("any_key" -> Json.obj("key" -> "translation_key", "params" -> Json.obj("param_key" -> "param value")).stringify))
```
In this case, `param_key` is a variable corresponding to the var you set in the translation message like `smart_count` (ref to the pluralization exemple)  
If you want to render a global error in a form, use the param_key `global` instead of `any_key`; which is a random choice just for the exemple in this case; and the error will automatically render just under the submit button.

### Core Application Architecture
The server-side application is centered around a pure Scala, framework-agnostic Core object. It does only one thing : Command execution
```scala
Core.execute(command)
```
A Command represents a single action that can be performed on the system. Typically, on the REST API, every route is associated to a single Command :
```scala
val command = request.body.as[CreateItem]
Core.execute(command)
```
Or more concisely :
```scala
def create() = Endpoint.executeAs[CreateItem, CreateItemOutcome]
```
Executing a Command results in a Future[Outcome] :
```scala
val command = request.body.as[CreateItem]
Core.execute(command) map {
	outcome: CreateItemOutcome => Ok(toJson(outcome.createdItem))
}
```
Creating a new type of Command and associated Outcome is very simple. Just inherit the traits :
```scala
case class CreateItem () extends Command
case class CreateItemOutcome () extends Outcome[CreateItem]
```
Now, if your try to execute this command, you'll get a compilation error. That's because the Core object expects you to provide an implicit CommandHandler. To create one, just use the helper function :
```scala
implicit val handler = Command.handler( (command: CreateItem) => {
	// Create the Item...
	Future(CreateItemOutcome())
})
```
##### Filter Layers
Before being handed to the handler, a Command is passed through several layers of safety checks, namely Validation, Security and Consistency. If you wish to, you can provide an implicit CommandFilter for a specific Command and Layer :
```scala
implicit val filter = Command.filter[Layer.Validation, CreateItem]( (command) => {
	// Throw validation error if necessary
	Future(command)
})
```

### Neo4j
The project defines its own internal API to communicate with the Neo4j database. Every interaction with Neo4j is done by sending an HTTP request containing a Cypher query. To send a request, use the Cypher.send() function :
```scala
val statement = "CREATE (n:Category {uuid: {uuid}, name: {name}})"
val parameters = Json.obj(
	"uuid" -> UUID.randomUUID,
	"name" -> name
)
Cypher.send(statement, parameters)
```
Statements should always be separated from parameter values :
 * Queries run faster
 * It protects from Cypher code injection

Cypher.send() returns a Future[CypherStatementResult]. In turn, a CypherStatementResult is basically a Seq[Map[String, JsValue]]. Let's look at an example :
```scala
val statement = "CREATE (n:Category {uuid: {uuid}, name: {name}}) RETURN n"
val parameters = Json.obj(
	"uuid" -> UUID.randomUUID,
	"name" -> name
)
Cypher.send(statement, parameters) map { result =>
	val firstRow: Map[String, JsValue] = result.rows(0)         // 1
	val createdNode: JsValue = firstRow("n")                    // 2
	val createdCategory: Category = createdNode.as[Category]    // 3
}
```
(The 3 lines of code in the map block should really be just one line, they are here for explanatory purposes only)

Note that the statement ends with "RETURN n", n being the identifier of the created node in the Cypher query. So in this case, we expect the query to return only one node, the created node, and we can safely assume that result.rows is a Seq of size 1. Had there been a problem with the query, the Future would have failed and the call to map() would have been ignored.

 1. By calling result.rows(0), we get a Map of all the returned values for the first (and only) match of this query. More frequently, when using the MATCH keyword in Cypher queries (comparable to SQL's SELECT), there can be many result rows for a single statement.
 2. This row is a Map, its keys are the identifiers declared after the RETURN clause in the Cypher query. Here, we RETURN only one identifier, "n", so we can call firstRow("n") to get the data for the node referenced by this identifier.
 3. Cypher is a flexible language, and it allows multiple types of values after the RETURN clause. Here, we RETURN a node, but we could just as well return a relationship, a number, a boolean... which is why we store row values as JsValue, which can be conveniently converted to the corresponding Scala type (just don't forget to provide an implicit Reads for the target type)