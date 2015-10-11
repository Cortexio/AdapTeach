var EventEmitter = require('events').EventEmitter;
var Constants = require('../constants/Constants');
var AppDispatcher = require('../dispatcher/AppDispatcher');
var assign = require('object-assign');

var data = {};

var CHANGE_EVENT = 'change';

module.exports = assign({}, EventEmitter.prototype, {
    emitChange: function () {
        "use strict";
        this.emit(CHANGE_EVENT);
    },

    addChangeListener: function (callback) {
        "use strict";
        this.on(CHANGE_EVENT, callback);
    },

    removeChangeListener: function (callback) {
        "use strict";
        this.removeListener(CHANGE_EVENT, callback);
    },

    demoFunction: function () {
        "use strict";
        return ;
    }
});

AppDispatcher.register(function (payload) {
    "use strict";
    var action = payload;
    switch (action.actionType) {
        case Constants.DEMO_CONSTANT :
            data = action.data;
            break;
    }
    DemoStore.emitChange();

    return true;
});