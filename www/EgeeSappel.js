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

};
module.exports = EgeeSappel;
