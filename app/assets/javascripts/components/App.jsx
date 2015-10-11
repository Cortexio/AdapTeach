var React = require('react');
var DemoStore = require('../store/DemoStore');
var DemoAction = require('../actions/DemoAction');
var Constants = require('../constants/Constants');

module.exports = React.createClass ({

  propTypes: {
    message: React.PropTypes.string.isRequired
  },

  render: function () {
    return <div>{this.props.message}</div>;
  }
});