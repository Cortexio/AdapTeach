import React from 'react'

var signin = React.createClass({
  render() {
    return (
      <div className="signin">
        <form method="post" action="/signin">
          <input type="text" placeholder="username" />
          <input type="password" placeholder="password" />
          <input type="submit" value="Sign in" />
        </form>
      </div>
    )
  }
})

export default signin