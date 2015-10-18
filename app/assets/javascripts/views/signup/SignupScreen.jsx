import React from 'react'
import json from '../../helpers/json'

import Form from '../generic/form'
import FCST from '../../constants/formConstants'

var signup = React.createClass({
  componentWillMount() {
    json.help()
  },
  
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
      .tupled(FCST.FIELD.ELEMENT, 'input')
      .tupled(FCST.FIELD.TYPE, 'text')
      .tupled(FCST.FIELD.PLACEHOLDER, 'First name')
      .tupled(FCST.FIELD.NAME, 'firstname')
      .tupled(FCST.FIELD.MANDATORY, true)
      .tupled(FCST.FIELD.STYLE, 'half')

    let lastname = {}
      .tupled(FCST.FIELD.ELEMENT, 'input')
      .tupled(FCST.FIELD.TYPE, 'text')
      .tupled(FCST.FIELD.PLACEHOLDER, 'Last Name')
      .tupled(FCST.FIELD.NAME, 'lastname')
      .tupled(FCST.FIELD.MANDATORY, true)
      .tupled(FCST.FIELD.STYLE, 'half')

    let username = {}
      .tupled(FCST.FIELD.ELEMENT, 'input')
      .tupled(FCST.FIELD.TYPE, 'text')
      .tupled(FCST.FIELD.PLACEHOLDER, 'Username')
      .tupled(FCST.FIELD.NAME, 'username')
      .tupled(FCST.FIELD.MANDATORY, true)
    
    let password = {}
      .tupled(FCST.FIELD.ELEMENT, 'input')
      .tupled(FCST.FIELD.TYPE, 'password')
      .tupled(FCST.FIELD.PLACEHOLDER, 'Password')
      .tupled(FCST.FIELD.NAME, 'password')
      .tupled(FCST.FIELD.MANDATORY, true)

    return(
      [firstname, lastname, username, password]
    )
  },

  _handleSignup(formData) {
    console.log(formData)
  }
})
export default signup