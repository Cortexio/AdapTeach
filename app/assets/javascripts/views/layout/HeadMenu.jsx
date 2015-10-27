import React from 'react'

import { logout } from '../../actions/AuthActions'

var component = React.createClass({
  
  render() {
    return (
      <div className="head-menu">
        <a className="btn btn-default signin" href="/signin">Signin</a>
        <br />
        <a className="btn btn-hightlight signup" href="/signup">Signup</a>
        <br />
        <a className="btn btn-default logout" href="#" onClick={this._handleLogout}>Logout</a>
      </div>
    )
  },

  _handleLogout(e) {
    e.preventDefault()
    logout()
  }
})

export default component