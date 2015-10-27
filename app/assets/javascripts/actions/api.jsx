import Axios from 'axios';

function ajaxWithFeedback(method, params, url) {
  return Axios({
    method: method,
    url: url,
    data: params
  })
  .catch(function (response) {
    if (response instanceof Error) {
      console.log(response.message)
    } else {
      console.log(response);
    }
  })
}

export default {
  signin(method, params) {
    return ajaxWithFeedback(method, params, '/api/signin')
  },

  signup(method, params) {
    return ajaxWithFeedback(method, params, '/api/signup')
  },

  logout() {
    return ajaxWithFeedback('GET', {}, '/api/logout')
  },

  session() {
    return ajaxWithFeedback('GET', {}, '/api/session')
  },

  availableEmail(email) {
    return ajaxWithFeedback('GET', {}, '/api/availability/email')
  }
}