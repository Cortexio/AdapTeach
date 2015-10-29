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