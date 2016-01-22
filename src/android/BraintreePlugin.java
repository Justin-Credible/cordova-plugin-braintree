package net.justincredible;

import android.app.Activity;
import android.content.Intent;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.braintreepayments.api.BraintreePaymentActivity;
import com.braintreepayments.api.PaymentRequest;
import com.braintreepayments.api.models.CardNonce;
import com.braintreepayments.api.models.PayPalAccountNonce;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.ThreeDSecureInfo;
import com.braintreepayments.api.models.VenmoAccountNonce;

import java.util.HashMap;
import java.util.Map;

public final class BraintreePlugin extends CordovaPlugin {

    private static final int DROP_IN_REQUEST = 100;
    private static final int PAYMENT_BUTTON_REQUEST = 200;
    private static final int CUSTOM_REQUEST = 300;
    private static final int PAYPAL_REQUEST = 400;

    private PaymentRequest paymentRequest = null;
    private CallbackContext dropInUICallbackContext = null;

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

        // Ensure we have the correct number of arguments.
        if (args.length() != 1) {
            callbackContext.error("A token is required.");
            return;
        }

        // Obtain the arguments.
        String token = args.getString(0);

        if (token == null || token.equals("")) {
            callbackContext.error("A token is required.");
            return;
        }

        paymentRequest = new PaymentRequest().clientToken(token);

        if (paymentRequest == null) {
            callbackContext.error("The Braintree client failed to initialize.");
            return;
        }

        callbackContext.success();
    }

    private synchronized void presentDropInPaymentUI(final JSONArray args, final CallbackContext callbackContext) throws JSONException {

        // Ensure the client has been initialized.
        if (paymentRequest == null) {
            callbackContext.error("The Braintree client must first be initialized via BraintreePlugin.initialize(token)");
            return;
        }

        // Ensure we have the correct number of arguments.
        if (args.length() != 2) {
            callbackContext.error("cancelText and title are required.");
            return;
        }

        // Obtain the arguments.

        String cancelText = args.getString(0);

        if (cancelText == null) {
            callbackContext.error("cancelText is required.");
            return;
        }

        String title = args.getString(1);

        if (title == null) {
            callbackContext.error("title is required.");
        }

        paymentRequest.actionBarTitle(title);
        //paymentRequest.cancelButtonText(cancelText);//TODO

        this.cordova.setActivityResultCallback(this);
        this.cordova.startActivityForResult(this, paymentRequest.getIntent(this.cordova.getActivity()), DROP_IN_REQUEST);

        dropInUICallbackContext = callbackContext;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (dropInUICallbackContext == null) {
            return;
        }

        if (requestCode == DROP_IN_REQUEST) {

            PaymentMethodNonce paymentMethodNonce = null;

            if (resultCode == Activity.RESULT_OK) {
                paymentMethodNonce = intent.getParcelableExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE);
            }

            this.handleDropInPaymentUiResult(resultCode, paymentMethodNonce);
        }
        else if (requestCode == PAYMENT_BUTTON_REQUEST) {
            //TODO
            dropInUICallbackContext.error("Activity result handler for PAYMENT_BUTTON_REQUEST not implemented.");
        }
        else if (requestCode == CUSTOM_REQUEST) {
            dropInUICallbackContext.error("Activity result handler for CUSTOM_REQUEST not implemented.");
            //TODO
        }
        else if (requestCode == PAYPAL_REQUEST) {
            dropInUICallbackContext.error("Activity result handler for PAYPAL_REQUEST not implemented.");
            //TODO
        }
    }

    /**
     * Helper used to handle the result of the drop-in payment UI.
     *
     * @param resultCode Indicates the result of the UI.
     * @param paymentMethodNonce Contains information about a successful payment.
     */
    private void handleDropInPaymentUiResult(int resultCode, PaymentMethodNonce paymentMethodNonce) {

        if (dropInUICallbackContext == null) {
            return;
        }

        if (resultCode == Activity.RESULT_CANCELED) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("userCancelled", true);
            dropInUICallbackContext.success(new JSONObject(resultMap));
            dropInUICallbackContext = null;
            return;
        }

        Map<String, Object> resultMap = this.getPaymentUINonceResult(paymentMethodNonce);
        dropInUICallbackContext.success(new JSONObject(resultMap));
        dropInUICallbackContext = null;
    }

    /**
     * Helper used to return a dictionary of values from the given payment method nonce.
     * Handles several different types of nonces (eg for cards, PayPal, etc).
     *
     * @param paymentMethodNonce The nonce used to build a dictionary of data from.
     * @return The dictionary of data populated via the given payment method nonce.
     */
    private Map<String, Object> getPaymentUINonceResult(PaymentMethodNonce paymentMethodNonce) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("nonce", paymentMethodNonce.getNonce());
        resultMap.put("type", paymentMethodNonce.getTypeLabel());
        resultMap.put("localizedDescription", paymentMethodNonce.getDescription());

        // Card
        if (paymentMethodNonce instanceof CardNonce) {
            CardNonce cardNonce = (CardNonce)paymentMethodNonce;

            Map<String, Object> innerMap = new HashMap<String, Object>();
            innerMap.put("lastTwo", cardNonce.getLastTwo());
            innerMap.put("network", cardNonce.getCardType());

            resultMap.put("card", innerMap);
        }

        // PayPal
        if (paymentMethodNonce instanceof PayPalAccountNonce) {
            PayPalAccountNonce payPalAccountNonce = (PayPalAccountNonce)paymentMethodNonce;

            Map<String, Object> innerMap = new HashMap<String, Object>();
            resultMap.put("email", payPalAccountNonce.getEmail());
            resultMap.put("firstName", payPalAccountNonce.getFirstName());
            resultMap.put("lastName", payPalAccountNonce.getLastName());
            resultMap.put("phone", payPalAccountNonce.getPhone());
            //resultMap.put("billingAddress", payPalAccountNonce.getBillingAddress()); //TODO
            //resultMap.put("shippingAddress", payPalAccountNonce.getShippingAddress()); //TODO
            resultMap.put("clientMetadataId", payPalAccountNonce.getClientMetadataId());
            resultMap.put("payerId", payPalAccountNonce.getPayerId());

            resultMap.put("payPalAccount", innerMap);
        }

        // 3D Secure
        if (paymentMethodNonce instanceof CardNonce) {
            CardNonce cardNonce = (CardNonce) paymentMethodNonce;
            ThreeDSecureInfo threeDSecureInfo = cardNonce.getThreeDSecureInfo();

            if (threeDSecureInfo != null) {
                Map<String, Object> innerMap = new HashMap<String, Object>();
                innerMap.put("liabilityShifted", threeDSecureInfo.isLiabilityShifted());
                innerMap.put("liabilityShiftPossible", threeDSecureInfo.isLiabilityShiftPossible());

                resultMap.put("threeDSecureCard", innerMap);
            }
        }

        // Venmo
        if (paymentMethodNonce instanceof VenmoAccountNonce) {
            VenmoAccountNonce venmoAccountNonce = (VenmoAccountNonce) paymentMethodNonce;

            Map<String, Object> innerMap = new HashMap<String, Object>();
            innerMap.put("username", venmoAccountNonce.getUsername());

            resultMap.put("venmoAccount", innerMap);
        }

        return resultMap;
    }
}
