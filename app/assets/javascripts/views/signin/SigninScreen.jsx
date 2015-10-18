import React from 'react'
import json from '../../helpers/json'

import Form from '../generic/form'
import FCST from '../../constants/formConstants'

var signin = React.createClass({
  componentWillMount() {
    json.help()
  },

  render() {
    return (
      <div className="signin">
        <Form
          method="POST"
          fields={this._signinFields()}
          submitAction={this._handleSignin}
          submitValue="Sign in" />
      </div> 
    )
  },

  _handleSignin(formData) {
    console.log(formData)
  },

  _signinFields() {
    let username = {}
      .tupled(FCST.FIELD.ELEMENT, 'input')
      .tupled(FCST.FIELD.TYPE, 'text')
      .tupled(FCST.FIELD.PLACEHOLDER, 'Username')
      .tupled(FCST.FIELD.NAME, 'username')
      .tupled(FCST.FIELD.MANDATORY, false)
    
    let password = {}
      .tupled(FCST.FIELD.ELEMENT, 'input')
      .tupled(FCST.FIELD.TYPE, 'password')
      .tupled(FCST.FIELD.PLACEHOLDER, 'Password')
      .tupled(FCST.FIELD.NAME, 'password')
      .tupled(FCST.FIELD.MANDATORY, true)

    return(
      [username, password]
    )
  }

})

export default signin