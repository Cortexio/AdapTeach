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

> SPECIFIC SERVER CASE
If you wanna create a message on the server side and use the i18n engine from the client, you have to follow these instructions:
Create your translation in the `messages_<locale>.json`
>> On the server side, your message must have the following structure
```sh
{
  key: "translation_key",
  params: {
    param_key: "param value"
  }
}
```
>> you will have the following source code to do this
```sh
Json.obj("key" -> "translation_key", "params" -> Json.obj("param_key" -> "param value")).stringify
```
In this case, `param_key` is a variable corresponding to the var you set in the translation message like `smart_count` (ref to the pluralization exemple)  
If you wanna render a global error in a form, use the param_key `global` and the error will automatically render just above the submit button.