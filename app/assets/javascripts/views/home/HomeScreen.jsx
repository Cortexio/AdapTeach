import React from 'react'
import { onChange } from 'fluxx'

import store from '../../stores/sessionStore' 

import HeadMenu from '../layout/HeadMenu'


export default React.createClass ({

  propTypes: {
    session: React.PropTypes.object
  },

  getDefaultProps() {
    return { session: {} }
  },

  render() {
    return (
      <div>
        {i18n.t('hello')} {this.props.session.username ? this.props.session.firstname + ' ' + this.props.session.lastname : 'Guest'}
        <HeadMenu session={this.props.session} ></HeadMenu>
      </div>
    )
  }
})