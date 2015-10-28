import { Action } from 'fluxx';
import api from './api';
import router from '../router';

export var authDispatcher = Action.create(
  'SET_SESSION', 'REMOVE_SESSION'
  );

export function signin(data) {
  return api.signin(data.method, data.params)
  .done((data, textStatus, jqXHR) => {
    authDispatcher.SET_SESSION(data.session)
    router.transitionTo('home')
  })
}

export function signup(data) {
  return api.signup(data.method, data.params)
  .done((data, textStatus, jqXHR) => {
    authDispatcher.SET_SESSION(data.session)
    router.transitionTo('home')
  })
}

export function logout() {
  return api.logout()
  .done((data, textStatus, jqXHR) => {
    authDispatcher.REMOVE_SESSION()
    router.transitionTo('home')
  })
}

export function syncSession() {
  return api.session()
  .done((data, textStatus, jqXHR) => {
    authDispatcher.SET_SESSION()
  })
}

export function checkAvailabilityEmail(email) {
  return api.availableEmail(email)
}

export function checkAvailabilityUsername(username) {
  return api.availableUsername(username)
}