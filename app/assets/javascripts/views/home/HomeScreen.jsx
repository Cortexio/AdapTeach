import React from 'react'

import HeadMenu from '../layout/HeadMenu'

var home = React.createClass ({

  propTypes: {
    session: React.PropTypes.object
  },

  render() {
    return (
      <div>
        Hello {this.props.session ? this.props.session.firstname + ' ' + this.props.session.lastname : 'Guest'}
        <HeadMenu></HeadMenu>
      </div>
    )
  }
});

export default home