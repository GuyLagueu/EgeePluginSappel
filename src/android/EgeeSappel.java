package cordova.plugin.egeesappel;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.diehl.metering.izar.license.api.LicenseService;

/**
 * This class echoes a string called from JavaScript.
 */
public class EgeeSappel extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("add")) {
            this.add(args, callbackContext);
            return true;

        } else if (action.equals("substract")) {
            this.substract(args, callbackContext);
            return true;

        } else if (action.equals("getlicense")) {
            this.getlicense(callbackContext);
            return true;

        } else if (action.equals("getlicence")) {
            this.getlicence(callbackContext);
            return true;
        }

        return false;
    }

    private void add(JSONArray args, CallbackContext callback) {
        if (args != null) {
            try {
                int p1 = Integer.parseInt(args.getJSONObject(0).getString("param1"));
                int p2 = Integer.parseInt(args.getJSONObject(0).getString("param2"));

                callback.success("" + (p1 + p2));

            } catch (Exception ex) {
                callback.error("Something went wrong " + ex);
            }

        } else {
            callback.error("Please don't pass null value");
        }
    }

    private void substract(JSONArray args, CallbackContext callback) {
        if (args != null) {
            try {
                int p1 = Integer.parseInt(args.getJSONObject(0).getString("param1"));
                int p2 = Integer.parseInt(args.getJSONObject(0).getString("param2"));

                callback.success("" + (p1 - p2));

            } catch (Exception ex) {
                callback.error("Something went wrong " + ex);
            }

        } else {
            callback.error("Please don't pass null value");
        }
    }

    private void getlicense(CallbackContext callback) {

        try {
            boolean isValid = LicenseService.getInstance()
                    .readHexStream(IReadoutInterpretSPI.class.getResourceAsStream("resources/egee.lic"))
                    .validate("www.egee.fr");

            callback.success("" + (isValid));

        } catch (Exception ex) {
            callback.error("No license " + ex);
        }
    }

    private void getlicence(CallbackContext callback) {

        try {
            String lic = "020000167B010401C0000000000004031F202020202020202020202020202020204547454520202020207777772E656765652E667200000000000000009ADEC26B9DCA187F2B26B2679753063115F2A8FA0383BF34EEED47E57349ECD0D1D3F6A0223BD00B73937256F6BBEC20FFEFB56C8FF06778491112DC67C7552D56E10603BFCEC010115BBD04278FB8C5CE71DF49862CC8D64D758891F20A322E48734D270F294C7DD476515115C35C522F4306F74057C2835CFFECAECF3E43FD0713D2B9E0C6529AAFBC899C910D94A8499D80652EC973C030E02F461D70FB4FA81A9EA59D2AF902CD890507DB6639FC5B2F7AA36EEA9277C8EDC479B40294EC3E13C5A24BBAF7E28C8E880344FB37F36B6DE43B181CE4E099DF89BD02FDAF091939CEEDCA8FD0130BCAE18C02F1BB6EDC22D9495D1D0BA1510536B8A48F9570";

            boolean isValid = LicenseService.getInstance().read(lic).validate("www.egee.fr");

            callback.success("" + (isValid));

        } catch (Exception ex) {
            callback.error("No license " + ex);
        }
    }
}
