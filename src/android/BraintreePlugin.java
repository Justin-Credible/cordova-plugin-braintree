package net.justincredible;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public final class BraintreePlugin extends CordovaPlugin {

    @Override
    public synchronized boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {

        if (action == null) {
            return false;
        }

        if (action.equals("initialize")) {

            try {
                this.initialize(args, callbackContext);
            }
            catch (Exception exception) {
                callbackContext.error("BraintreePlugin uncaught exception: " + exception.getMessage());
            }

            return true;
        }
        else if (action.equals("presentDropInPaymentUI")) {

            try {
                this.presentDropInPaymentUI(args, callbackContext);
            }
            catch (Exception exception) {
                callbackContext.error("BraintreePlugin uncaught exception: " + exception.getMessage());
            }

            return true;
        }
        else {
            // The given action was not handled above.
            return false;
        }
    }

    private synchronized void initialize(final JSONArray args, final CallbackContext callbackContext) throws JSONException {

        //TODO
        callbackContext.success();
    }

    private synchronized void presentDropInPaymentUI(final JSONArray args, final CallbackContext callbackContext) throws JSONException {

        //TODO
        callbackContext.success();
    }
}
