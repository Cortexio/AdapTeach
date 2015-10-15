import { set, remove } from '../actions/SessionActions';
import request from 'request';

var action = Action.create('set', 'remove');

var store = Store({
  session : {}, 

  handlers: {
    [action.set]: function(session) {
      request('/app/session', function (error, response, body) {
        if (!error && response.statusCode == 200) {
          return response
        }
      });
    },
    [action.remove]: (session) => {}
  }
});
 export { store, action };