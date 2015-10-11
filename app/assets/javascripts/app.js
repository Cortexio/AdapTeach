var React = require('react');
var ReactDOM = require('react-dom');
var App = require('./components/App');

window.initReact = function (params) {
  ReactDOM.render(
    React.createElement(App, params),
    document.getElementById('content')
  );
}