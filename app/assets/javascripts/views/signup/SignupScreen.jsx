import React from 'react'
import json from '../../helpers/json'
import { signup, checkAvailabilityEmail, checkAvailabilityUsername } from '../../actions/authActions'

import Form from '../generic/form'
import FCST from '../../constants/formConstants'


export default React.createClass({

  render() {
    return (
      <div className="signup">
        <Form 
          method="POST"
          fields={this._signupFields()}
          submitAction={this._handleSignup}
          submitValue={i18n.t('signup')} />
      </div>
    )
  },

  _signupFields() {
    let firstname = {}
    json.tupled(firstname, FCST.FIELD.ELEMENT, 'input')
    json.tupled(firstname, FCST.FIELD.TYPE, 'text')
    json.tupled(firstname, FCST.FIELD.PLACEHOLDER, i18n.t('authentication_placeholder_firstname'))
    json.tupled(firstname, FCST.FIELD.NAME, 'firstname')
    json.tupled(firstname, FCST.FIELD.MANDATORY, true)
    json.tupled(firstname, FCST.FIELD.HALF_SIZE, FCST.FIELD.POSITION.LEFT)

    let lastname = {}
    json.tupled(lastname, FCST.FIELD.ELEMENT, 'input')
    json.tupled(lastname, FCST.FIELD.TYPE, 'text')
    json.tupled(lastname, FCST.FIELD.PLACEHOLDER, i18n.t('authentication_placeholder_lastname'))
    json.tupled(lastname, FCST.FIELD.NAME, 'lastname')
    json.tupled(lastname, FCST.FIELD.MANDATORY, true)
    json.tupled(lastname, FCST.FIELD.HALF_SIZE, FCST.FIELD.POSITION.RIGHT)

    let email = {}
    json.tupled(email, FCST.FIELD.ELEMENT, 'input')
    json.tupled(email, FCST.FIELD.TYPE, 'email')
    json.tupled(email, FCST.FIELD.PLACEHOLDER, i18n.t('authentication_placeholder_email'))
    json.tupled(email, FCST.FIELD.NAME, 'email')
    json.tupled(email, FCST.FIELD.ONBLUR, {action : checkAvailabilityEmail, error: this._fieldInError, reset: this._fieldReset})
    json.tupled(email, FCST.FIELD.MANDATORY, true)
    json.tupled(email, FCST.FIELD.AUTOCOMPLETE, false)

    let username = {}
    json.tupled(username, FCST.FIELD.ELEMENT, 'input')
    json.tupled(username, FCST.FIELD.TYPE, 'text')
    json.tupled(username, FCST.FIELD.PLACEHOLDER, i18n.t('authentication_placeholder_username'))
    json.tupled(username, FCST.FIELD.NAME, 'username')
    json.tupled(username, FCST.FIELD.ONCHANGE, {action : checkAvailabilityUsername, success: this._fieldInSuccess, error: this._fieldInError, reset: this._fieldReset})
    json.tupled(username, FCST.FIELD.MANDATORY, true)
    json.tupled(username, FCST.FIELD.AUTOCOMPLETE, false)

    let password = {}
    json.tupled(password, FCST.FIELD.ELEMENT, 'input')
    json.tupled(password, FCST.FIELD.TYPE, 'password')
    json.tupled(password, FCST.FIELD.PLACEHOLDER, i18n.t('authentication_placeholder_password'))
    json.tupled(password, FCST.FIELD.NAME, 'password')
    json.tupled(password, FCST.FIELD.MANDATORY, true)

    return(
      [firstname, lastname, email, username, password]
    )
  },

  _handleSignup(formData) {
    return signup(formData)
  },

  _fieldInSuccess(elem) {
    this._fieldReset(elem)
    elem.classList.add("success");
  },

  _fieldInError(elem) {
    this._fieldReset(elem)
    elem.classList.add("error")
  },

  _fieldReset(elem) {
    elem.classList.remove("success")
    elem.classList.remove("error")
  }
})