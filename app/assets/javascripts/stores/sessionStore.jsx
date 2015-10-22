import { ActorStore} from 'fluxx'

import {authDispatcher} from '../actions/authActions'

export default ActorStore(on => {
  // The store's private state; The store could have many of these.
  let session = {}

  // When the store receives the init Action,
  // initialize its initial state with the action's payload.
  on(authDispatcher.SET_SESSION, data => {
    session = data
  })

  on(authDispatcher.REMOVE_SESSION, _ => session = {})

  return {
    session: () => session
  };
});