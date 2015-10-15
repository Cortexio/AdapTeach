import React from 'react'

var signup = React.createClass({
  render() {
    return (
      <div className="signup">
        <form method="post" action="/signin">
          <input type="text" placeholder="firstname" />
          <input type="text" placeholder="lastname" />
          <input type="text" placeholder="username" />
          <input type="password" placeholder="password" />
          <input type="submit" value="Sign up" />
        </form>
      </div>
    )
  }
})

export default signup