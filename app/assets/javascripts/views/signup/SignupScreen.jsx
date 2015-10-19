import React from 'react'
import json from '../../helpers/json'

import Form from '../generic/form'
import FCST from '../../constants/formConstants'

var signup = React.createClass({
  render() {
    return (
      <div className="signup">
        <Form 
          method="POST"
          fields={this._signupFields()}
          submitAction={this._handleSignup}
          submitValue="Sign up" />
      </div>
    )
  },

  _signupFields() {
    let firstname = {}
    json.tupled(firstname, FCST.FIELD.ELEMENT, 'input')
    json.tupled(firstname, FCST.FIELD.TYPE, 'text')
    json.tupled(firstname, FCST.FIELD.PLACEHOLDER, 'First name')
    json.tupled(firstname, FCST.FIELD.NAME, 'firstname')
    json.tupled(firstname, FCST.FIELD.MANDATORY, true)
    json.tupled(firstname, FCST.FIELD.HALF_SIZE, FCST.FIELD.POSITION.LEFT)

    let lastname = {}
    json.tupled(lastname, FCST.FIELD.ELEMENT, 'input')
    json.tupled(lastname, FCST.FIELD.TYPE, 'text')
    json.tupled(lastname, FCST.FIELD.PLACEHOLDER, 'Last name')
    json.tupled(lastname, FCST.FIELD.NAME, 'lastname')
    json.tupled(lastname, FCST.FIELD.MANDATORY, true)
    json.tupled(lastname, FCST.FIELD.HALF_SIZE, FCST.FIELD.POSITION.RIGHT)

    let username = {}
    json.tupled(username, FCST.FIELD.ELEMENT, 'input')
    json.tupled(username, FCST.FIELD.TYPE, 'text')
    json.tupled(username, FCST.FIELD.PLACEHOLDER, 'Username')
    json.tupled(username, FCST.FIELD.NAME, 'username')
    json.tupled(username, FCST.FIELD.MANDATORY, true)
    
    let password = {}
    json.tupled(password, FCST.FIELD.ELEMENT, 'input')
    json.tupled(password, FCST.FIELD.TYPE, 'password')
    json.tupled(password, FCST.FIELD.PLACEHOLDER, 'Password')
    json.tupled(password, FCST.FIELD.NAME, 'password')
    json.tupled(password, FCST.FIELD.MANDATORY, true)

    return(
      [firstname, lastname, username, password]
    )
  },

  _handleSignup(formData) {
    console.log(formData)
  }
})
export default signup