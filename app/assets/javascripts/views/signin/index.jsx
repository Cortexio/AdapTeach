import React from 'react'
import ReactDOM from 'react-dom'
import SigninScreen from './SigninScreen'

export default {
  enter() {
    ReactDOM.render(<SigninScreen />, document.getElementById('content'))
  }
};