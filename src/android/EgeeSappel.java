package cordova.plugin.egeesappel;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import com.diehl.metering.izar.license.api.LicenseService;
import com.diehl.metering.izar.module.readout.text.impl.IzarLibraryVersion;
import com.diehl.metering.izar.module.config.basic.text.impl.MeterConfig;
import com.diehl.metering.izar.module.readout.text.impl.RadioInterpret;
import com.diehl.metering.izar.module.readout.text.impl.Receiver;

public class EgeeSappel extends CordovaPlugin {

    private static final String lic = "020000167B010401C0000000000004031F202020202020202020202020202020204547454520202020207777772E656765652E667200000000000000009ADEC26B9DCA187F2B26B2679753063115F2A8FA0383BF34EEED47E57349ECD0D1D3F6A0223BD00B73937256F6BBEC20FFEFB56C8FF06778491112DC67C7552D56E10603BFCEC010115BBD04278FB8C5CE71DF49862CC8D64D758891F20A322E48734D270F294C7DD476515115C35C522F4306F74057C2835CFFECAECF3E43FD0713D2B9E0C6529AAFBC899C910D94A8499D80652EC973C030E02F461D70FB4FA81A9EA59D2AF902CD890507DB6639FC5B2F7AA36EEA9277C8EDC479B40294EC3E13C5A24BBAF7E28C8E880344FB37F36B6DE43B181CE4E099DF89BD02FDAF091939CEEDCA8FD0130BCAE18C02F1BB6EDC22D9495D1D0BA1510536B8A48F9570";
    private static final String GET_VERSION = "getVersion";
    private static final String IS_LICENSE_VALID = "isLicenseValid";
    private static final String READ_DEVICE_CONFIGURATION = "readDeviceConfiguration";
    private static final String FRAME_INTERPRET = "interpret";
    private static final String FRAME_INTERPRET_HEAD = "interpretHead";
    private static final String TAG = "EgeeSappel";

    private CallbackContext callbackContext;
    PluginResult result = null;

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
        } else if (action.equals(READ_DEVICE_CONFIGURATION)) {
            String param1 = args.getString(0);
            String param2 = args.getString(1);
            return this.readDeviceConfiguration(param1, param2, callbackContext);
        } else if (action.equals(FRAME_INTERPRET)) {
            String param = args.getString(0);
            return this.interpret(param, callbackContext);
        } else if (action.equals(FRAME_INTERPRET_HEAD)) {
            String param = args.getString(0);
            this.interpretHead(param, callbackContext);
            PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
            result.setKeepCallback(true);
            return true;
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
        String version = IzarLibraryVersion.INSTANCE.getVersion();
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

    /**
     * This method return the license status
     * 
     * @param param1          OPTO Head Mac address
     * @param param2          OPTO Head password 0000 by default
     * @param callbackContext A Cordova callback context
     * @return true if getting configuration information, fail will be called if
     *         not.
     */
    private boolean readDeviceConfiguration(String param1, String param2, CallbackContext callbackContext) {

        try {
            if (param1 == null) {
                callbackContext.success("Opto address is null.");
                return false;
            }

            if (param2 == null) {
                callbackContext.success("Opto password is required.");
                return false;
            }

            boolean isValid = LicenseService.getInstance().read(lic).validate("www.egee.fr");

            if (isValid != true) {
                callbackContext.success("Invalid license.");
                return false;
            }

            // MeterConfigStatic.create(param1);
            // MeterConfigStatic.initialize(param2);
            // final String readResult = MeterConfigStatic.readConfiguration();

            // MeterConfigStatic.disconnect();
            MeterConfig meterconfig = MeterConfig.create(param1);

            final String resultInit = meterconfig.initialize(param2);
            final String readResult = meterconfig.readConfiguration();

            meterconfig.disconnect();

            callbackContext.success(readResult);

            return true;

        } catch (Exception ex) {
            callbackContext.error(ex.getMessage());
            return false;
        }
    }

    /**
     * This method return the frame interpretation
     * 
     * @param param           frame to interpret
     * @param callbackContext A Cordova callback context
     * @return true if frame interpretation success, fail will be called if not.
     */
    private boolean interpret(String param, CallbackContext callbackContext) {

        try {
            if (param == null) {
                callbackContext.success("Frame is null.");
                return false;
            }

            boolean isValid = LicenseService.getInstance().read(lic).validate("www.egee.fr");

            if (isValid != true) {
                callbackContext.success("Invalid license.");
                return false;
            }

            String result = RadioInterpret.INSTANCE.interpret(param);
            callbackContext.success(result);

            return true;

        } catch (Exception ex) {
            callbackContext.error(ex.getMessage());
            return false;
        }
    }

    /**
     * This method return the frame head interpretation
     * 
     * @param param           frame to interpret
     * @param callbackContext A Cordova callback context
     * @return true if frame head interpretation success, fail will be called if
     *         not.
     */
    private void interpretHead(String param, CallbackContext callbackContext) {
        String result = RadioInterpret.INSTANCE.interpretHead(param);
        callbackContext.success(result);
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
        }
        return false;
    }
}
