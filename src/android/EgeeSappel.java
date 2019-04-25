package cordova.plugin.egeesappel;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.diehl.metering.izar.license.api.LicenseService;

public class EgeeSappel extends CordovaPlugin {

    private static final String lic = "020000167B010401C0000000000004031F202020202020202020202020202020204547454520202020207777772E656765652E667200000000000000009ADEC26B9DCA187F2B26B2679753063115F2A8FA0383BF34EEED47E57349ECD0D1D3F6A0223BD00B73937256F6BBEC20FFEFB56C8FF06778491112DC67C7552D56E10603BFCEC010115BBD04278FB8C5CE71DF49862CC8D64D758891F20A322E48734D270F294C7DD476515115C35C522F4306F74057C2835CFFECAECF3E43FD0713D2B9E0C6529AAFBC899C910D94A8499D80652EC973C030E02F461D70FB4FA81A9EA59D2AF902CD890507DB6639FC5B2F7AA36EEA9277C8EDC479B40294EC3E13C5A24BBAF7E28C8E880344FB37F36B6DE43B181CE4E099DF89BD02FDAF091939CEEDCA8FD0130BCAE18C02F1BB6EDC22D9495D1D0BA1510536B8A48F9570";
    private static final String GET_VERSION = "getVersion";
    private static final String IS_LICENSE_VALID = "isLicenseValid";
    private static final String TAG = "EgeeSappel";

    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        this.callbackContext = callbackContext;

        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        } else if (action.equals(GET_VERSION)) {
            return this.getVersion(callbackContext);
        } else if (action.equals(IS_LICENSE_VALID)) {
            return this.isLicenseValid(callbackContext);
        } else {
            callbackContext.error("Incorrect action parameter: " + action);
        }

        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    /**
     * This method retrieves the IZAR@LIBRARY current version
     *
     * @param callbackContext A Cordova callback context
     * @return true if Version found, false if not.
     */
    private boolean getVersion(CallbackContext callbackContext) {

        if (!this.isLicenseValid()) {
            callbackContext.error("License is invalid");
            return false;
        }

        String version = "";

        if (version.isEmpty()) {
            callbackContext.error("version is empty");
            return false;
        }

        callbackContext.success(version);
        return true;
    }

    /**
     * This method return the license status
     *
     * @param callbackContext A Cordova callback context
     * @return true if license is valid, fail will be called if not.
     */
    private boolean isLicenseValid(CallbackContext callbackContext) {
        boolean isValid = LicenseService.getInstance().read(lic).validate("www.egee.fr");
        callbackContext.success(isValid ? "1" : "0");
        return isValid;
    }

    private boolean validateData(JSONArray data) {
        try {
            if (data == null || data.get(0) == null) {
                callbackContext.error("Data is null.");
                return false;
            }
            return true;
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            Log.d(TAG, e.getMessage());
        }
        return false;
    }
}
