var exec = require('cordova/exec');

exports.sendLogs = function (success, error) {
    exec(success, error, 'LogCat', 'sendLogs', []);
};

exports.uploadPlugin = function (vin, success, error) {
    exec(success, error, 'LogCat', 'uploadPlugin', [vin]);
};

exports.registerDevice = function (appid, success, error) {
    exec(success, error, 'LogCat', 'registerDevice', [appid]);
};
