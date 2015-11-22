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
The server-side application is centered around a pure Scala, framework-agnostic core App object. The core App does only one thing : it executes Commands.
```scala
App.execute(command)
```
A Command represents a single action that can be performed on the system. Typically, on the REST API, every route is associated to a single Command :
```scala
val command = request.body.as[CreateItem]
App.execute(command)
```
Executing a Command results in a Future[Outcome] :
```scala
val command = request.body.as[CreateItem]
App.execute(command) map {
	outcome: CreateItemOutcome => Ok(toJson(outcome.createdItem))
}
```
Creating a new type of Command and associated Outcome is very simple. Just inherit the traits :
```scala
case class DoSomething () extends Command
case class DoSomethingOutcome () extends Outcome[DoSomething]
```
Now, if your try to execute this command, you'll get a compilation error. That's because the core App object expects you to provide an implicit CommandHandler. To create one, just use the helper function :
```scala
implicit val handler = Command.handler( (command: DoSomething) => {
	// Do Something... or not
	Future(DoSomethingOutcome())
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