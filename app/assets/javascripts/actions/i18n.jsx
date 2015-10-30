import Polyglot from 'node-polyglot'

import api from './api'
import json from '../helpers/json'
import { locales } from '../constants/i18n'

export function setLocale(requestLocale, callback) {
  let locale = json.values(locales).reduce((acc, value) => {
    return requestLocale === value ? value : acc
  }, locales.EN)

  api.loadMessages(locale)
    .done((data, textStatus, jqXHR) => {
      window.i18n = new Polyglot({locale: locale, phrases: data})
      if(callback) callback()
    })
}