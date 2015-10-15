import React from 'react'
import ReactDOM from 'react-dom'
import SignupScreen from './SignupScreen'

export default {
  enter() {
    ReactDOM.render(<SignupScreen />, document.getElementById('content'))
  }
};