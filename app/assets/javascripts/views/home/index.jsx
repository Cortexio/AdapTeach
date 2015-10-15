import React from 'react'
import ReactDOM from 'react-dom'
import HomeScreen from './HomeScreen'

export default {
  enter() {
    ReactDOM.render(<HomeScreen />, document.getElementById('content'));
  }
};