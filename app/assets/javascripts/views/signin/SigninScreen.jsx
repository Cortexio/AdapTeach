import React from 'react'
import Form from '../generic/form'
import FORM_CONSTANTS from '../../constants/formConstants'

var signin = React.createClass({

  render() {
    return (
      <div className="signin">
        <Form
          method="POST"
          fields={this.signinFields()}
          submitAction={this.handleSignin}
          submitValue="Sign in" />
      </div> 
    )
  },

  handleSignin(formData) {
    console.log(formData)
  },

  signinFields() {
    let username = {}
    username[FORM_CONSTANTS.FIELD.ELEMENT] = 'input'
    username[FORM_CONSTANTS.FIELD.TYPE] = 'text'
    username[FORM_CONSTANTS.FIELD.PLACEHOLDER] = 'username'
    username[FORM_CONSTANTS.FIELD.NAME] = 'username'
    username[FORM_CONSTANTS.FIELD.MANDATORY] = true

    let password = {}
    password[FORM_CONSTANTS.FIELD.ELEMENT] = 'input'
    password[FORM_CONSTANTS.FIELD.TYPE] = 'password'
    password[FORM_CONSTANTS.FIELD.PLACEHOLDER] = 'password'
    password[FORM_CONSTANTS.FIELD.NAME] = 'password'
    password[FORM_CONSTANTS.FIELD.MANDATORY] = true

    return(
      [username, password]
    )
  }

})

export default signin