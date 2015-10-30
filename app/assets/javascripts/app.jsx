import React from 'react'
import ReactDOM from 'react-dom'
import { State } from 'abyssa'

import router from './router'
import json from './helpers/json'

import home from './views/home'
import signin from './views/signin'
import signup from './views/signup'

import { syncSession } from './actions/authActions'
import { setLocale } from './actions/i18n'



router
  .addState('home', State('', home))
  .addState('signin', State('signin', signin))
  .addState('signup', State('signup', signup))


window.init = function () {
  setLocale(navigator.language.substr(0, 2), function() { router.init(); syncSession() })
}