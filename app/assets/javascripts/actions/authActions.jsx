import { Action } from 'fluxx';
import Q from 'q';
import {signin, signup, logout} from './api';
import router from '../router';

export var authDispatcher = Action.create(
  'SET_SESSION', 'REMOVE_SESSION'
  );

export function signin(authData) {
  api.signin(authData.username, authData.password)
  authDispatcher.SET_SESSION({}); //todo in api callback
  router.transitionTo(router.current().name);
}

export function signup(authData) {
  signup(authData.username, authData.password, authData.firstname, authData.lastname)
  authDispatcher.SET_SESSION({}); //todo in api callback
  router.transitionTo(router.current().name);
}

export function logout() {
  logout()
  authDispatcher.REMOVE_SESSION();
  router.transitionTo(router.current().name);
}