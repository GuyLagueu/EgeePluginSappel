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
                    .readHexStream(IReadoutInterpretSPI.class.getResourceAsStream("resources/egee.lic")).validate();

            callback.success("" + (isValid));

        } catch (Exception ex) {
            callback.error("No license " + ex);
        }
    }

    private void getlicence(CallbackContext callback) {

        try {
            String lic = "010000001000E71F200000001F454745450000000000000000000000000000000022C184F465E9D474F85F152C8899D9E4D3DE103DF2B7F6F58387978F426CF4219555EF54322B56093F38F87AB4FE95B442C8BADCD350C2D13BF7CC04374555CEF5CDA422E67E1E3CE4C9B3B2FA597962F0E0225A5B3634A72D1B2F60773A8D2CECB6117200601989C1AF84E08865A0EE693FB5D33E9441A3B7918C3621C17F8879201512C0D39F1C7059F99BE1AFB83A50CBD3B2CD6CEB4000DB2375EF943F3EB6536BA090B8164DC17FB5632E51E8FD22208C7D2F34F067EB5E8B654147E7086C5873DCECF9388EEB18B08FEB63D8D683C299410AD6714A144A90A852ED267779DE669F26E02D2B2807328EF9EB0F0653BF855A6814DB99A34F04A06B9459A5";
            boolean isValid = LicenseService.getInstance().read(lic).validate;

            callback.success("" + (isValid));

        } catch (Exception ex) {
            callback.error("No license " + ex);
        }
    }
}
