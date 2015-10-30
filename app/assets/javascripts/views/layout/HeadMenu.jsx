import React from 'react'

import { logout } from '../../actions/AuthActions'

var component = React.createClass({
  
  propTypes: {
    session: React.PropTypes.object.isRequired
  },

  getDefaultProps() {
    return { session: {} }
  },

  render() {
    return (
      <div className="head-menu">
      { this.props.session.username ? this._logout() : this._authentications() }
      </div>
    )
  },

  _authentications() {
    return (
      <div>
        <a className="btn btn-default signin" href="/signin">{i18n.t('signin')}</a>
        <br />
        <a className="btn btn-hightlight signup" href="/signup">{i18n.t('signup')}</a>
      </div>
    )
  },

  _logout() {
    return (
      <a className="btn btn-default logout" href="#" onClick={this._handleLogout}>{i18n.t('logout')}</a>
    )
  },

  _handleLogout(e) {
    e.preventDefault()
    logout()
  }
})

export default component