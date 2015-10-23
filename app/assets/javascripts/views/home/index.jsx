import React from 'react'
import ReactDOM from 'react-dom'
import Fluxx from 'fluxx/lib/ReactConnector';
import HomeScreen from './HomeScreen'
import SessionStore from '../../stores/sessionStore'

export default {
  enter() {
    ReactDOM.render(
      // <Fluxx stores={[SessionStore]}> { (session) => 
        <HomeScreen session={ {firstname: "firstname", lastname: "lastname"} } />,
      // }
      // </Fluxx>, 
      document.getElementById('content'))
  }
}