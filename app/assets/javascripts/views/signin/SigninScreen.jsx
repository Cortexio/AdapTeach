import React from 'react'
import json from '../../helpers/json'

import Form from '../generic/form'
import FCST from '../../constants/formConstants'
import { signin } from '../../actions/authActions'

export default React.createClass({
  render() {
    return (
      <div className="signin">
        <div className="form-wrapper">
          <Form
            method="POST"
            fields={this._signinFields()}
            submitAction={this._handleSignin}
            submitValue={i18n.t('signin')} />
          </div>
      </div> 
    )
  },

  _handleSignin(formData) {
    return signin(formData)
  },

  _signinFields() {
    let username = {}
    json.tupled(username, FCST.FIELD.ELEMENT, 'input')
    json.tupled(username, FCST.FIELD.TYPE, 'text')
    json.tupled(username, FCST.FIELD.PLACEHOLDER, i18n.t('authentication_placeholder_username'))
    json.tupled(username, FCST.FIELD.NAME, 'username')
    json.tupled(username, FCST.FIELD.MANDATORY, true)
    
    let password = {}
    json.tupled(password, FCST.FIELD.ELEMENT, 'input')
    json.tupled(password, FCST.FIELD.TYPE, 'password')
    json.tupled(password, FCST.FIELD.PLACEHOLDER, i18n.t('authentication_placeholder_password'))
    json.tupled(password, FCST.FIELD.NAME, 'password')
    json.tupled(password, FCST.FIELD.MANDATORY, true)

    return(
      [username, password]
    )
  }

})