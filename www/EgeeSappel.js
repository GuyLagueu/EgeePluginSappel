var exec = require('cordova/exec');


module.exports.add = function (arg0, success, error)
{
    exec(success, error, 'EgeeSappel', 'add', [arg0]);
};

module.exports.substract = function (arg0, success, error) 
{
    exec(success, error, 'EgeeSappel', 'substract', [arg0]);
};

module.exports.getlicense = function (success, error) 
{
    exec(success, error, 'EgeeSappel', 'getlicense', []);
};

module.exports.getlicence = function (success, error) 
{
    exec(success, error, 'EgeeSappel', 'getlicence', []);
};
