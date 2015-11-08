import React from 'react'
import ReactDOM from 'react-dom'
import createCategory from './createCategory'

export default {
  enter() {
    ReactDOM.render(<createCategory />, document.getElementById('content'))
  }
};