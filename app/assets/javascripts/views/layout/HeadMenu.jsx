import React from 'react'

var component = React.createClass({
  render() {
    return (
      <div className="head-menu">
        <a className="btn btn-default signin" href="/signin">Signin</a>
        <a className="btn btn-hightlight signup" href="/signup">Signup</a>
        <a className="btn btn-hightlight signup" href="/objective">Add an objective</a>
      </div>
    )
  }
})

export default component