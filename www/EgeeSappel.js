cordova.define("cordova.plugin.egeesappel.EgeeSappel", function(require, exports, module) {
var exec = require('cordova/exec');

var EgeeSappel = {


     /**
     *  Gets the currently version of IZARLIBRARY 
     * @param 	win	callback function
     * @param 	fail	callback function if error
     */
    getVersion: function(win, fail) {
        if (typeof win != "function") {
            console.log("getVersion first parameter must be a function to handle IzarLibrary Version.");
            return;
        }
        cordova.exec(win, fail, 'EgeeSappel', 'getVersion', []);
    },
    
    /**
     *  Gets 'true' or 'false' if license is valid or invalid
     * @param 	win	callback function
     * @param 	fail
     */
    isLicenseValid: function(win, fail) {
        if (typeof win != "function") {
            console.log("isLicenseValid first parameter must be a function to handle license status.");
            return;
        }
        cordova.exec(
            // Cordova can only return strings to JS, and the underlying plugin
            // sends a "1" for true and "0" for false.
            function(result) {
                win(result == "1");
            },
            fail, 'EgeeSappel', 'isLicenseValid', []
        );
    },

     /**
     *  Gets 'true' or 'false' if device configuration is avalable or if not
     * @param param1 opto mac address
     * @param param2 opto head password
     * @param 	win	callback function
     * @param 	fail
     */
    readDeviceConfiguration: function(param1, param2, win, fail) {
        if (typeof win != "function") {
            return;
        }
        cordova.exec(
            function(result) {
                win(result == "1");
            },
            fail, 'EgeeSappel', 'readDeviceConfiguration', [param1, param2]
        );
    },

    /**
     *  Gets 'true' or 'false' if frame interpretation success or if not
     * @param param frame to interpret
     * @param 	win	callback function
     * @param 	fail
     */
    interpret: function(param, win, fail) {
        if (typeof win != "function") {
            return;
        }
        cordova.exec(
            function(result) {
                win(result == "1");
            },
            fail, 'EgeeSappel', 'interpret', [param]
        );
    },

    /**
     *  Gets 'true' or 'false' if frame interpretation success or if not
     * @param param frame to interpret head
     * @param 	cb	callback function
     */
    interpretHead: function(param, success, error) {
        cordova.exec(success,error, 'EgeeSappel', 'interpretHead', [param]);
    },

};
module.exports = EgeeSappel;

});
