import { Action } from 'fluxx';
import api from './api';
import router from '../router';

export var authDispatcher = Action.create(
  'SET_SESSION', 'REMOVE_SESSION'
  );

export function signin(method, params) {
  return api.signin(params)
  .then(function (response) {
    authDispatcher.SET_SESSION(response.data)
    router.transitionTo(router.current().name)
  })
}

export function signup(method, params) {
  return api.signup(method, params)
  .then(function (response) {
    authDispatcher.SET_SESSION(response.data)
    router.transitionTo(router.current().name)
  })
}

export function logout() {
  return api.logout()
  .then(function (response) {
    authDispatcher.REMOVE_SESSION()
    router.transitionTo(router.current().name)
  })
}