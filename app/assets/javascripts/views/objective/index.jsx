import React from 'react'
import ReactDOM from 'react-dom'
import ObjectiveFormScreen from './ObjectiveFormScreen'

export default {
  enter() {
    ReactDOM.render(<ObjectiveFormScreen />, document.getElementById('content'))
  }
};