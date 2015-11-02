import React from 'react'
import ReactDOM from 'react-dom'
import { State } from 'abyssa'

import router from './router'

import json from './helpers/json'

import home from './views/home'
import signin from './views/signin'
import signup from './views/signup'
import objective from './views/objective'


router
  .addState('home', State('', home))
  .addState('signin', State('signin', signin))
  .addState('signup', State('signup', signup))
  .addState('objective', State('objective', objective))


window.init = function () {
  router.init()
}