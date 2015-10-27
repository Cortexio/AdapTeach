import { Store } from 'fluxx'

import {authDispatcher} from '../actions/authActions'

export default Store({
  // The store's private state; The store could have many of these.
  session : {},

  handlers: {
    [authDispatcher.SET_SESSION] : (session, data) => session = data,
    [authDispatcher.REMOVE_SESSION]: (session, _) => session = {}
  }
})