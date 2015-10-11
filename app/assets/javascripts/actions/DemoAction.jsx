var AppDispatcher = require('../dispatcher/AppDispatcher');
var Constants = require('../constants/Constants');

module.exports = {
    demoFunction : function () {
        let data = {};
        AppDispatcher.dispatch({
            actionType: Constants.DEMO_CONSTANT,
            data: data
        });
    }
};