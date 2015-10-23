import { Action } from 'fluxx';
import api from './api';
import router from '../router';

export var authDispatcher = Action.create(
  'SET_SESSION', 'REMOVE_SESSION'
  );

export function signin(data) {
  return api.signin(data.method, data.params)
  .then(function (response) {
    // authDispatcher.SET_SESSION(response.data)
    router.transitionTo('home')
  })
}

export function signup(data) {
  return api.signup(data.method, data.params)
  .then(function (response) {
    // authDispatcher.SET_SESSION(response.data.session)
    router.transitionTo('home')
  })
}

export function logout() {
  return api.logout()
  .then(function (response) {
    // authDispatcher.REMOVE_SESSION()
    router.transitionTo('home')
  })
}