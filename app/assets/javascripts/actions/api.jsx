function ajaxWithFeedback(method, params, url) {
  return $.ajax({
    method: method,
    url: url,
    data: params
  })
  .fail((jqXHR, textStatus, errorThrown) => console.log(textStatus))
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
    return ajaxWithFeedback('GET', {email: email}, '/api/availability/email')
  },

    availableUsername(username) {
    return ajaxWithFeedback('GET', {username: username}, '/api/availability/username')
  }
}