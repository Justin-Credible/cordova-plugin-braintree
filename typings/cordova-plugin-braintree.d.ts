// Type definitions for cordova-plugin-braintree 0.5.1
// Project: https://github.com/Justin-Credible/cordova-plugin-braintree
// Definitions by: Justin Unterreiner <https://github.com/Justin-Credible>
// Definitions: https://github.com/borisyankov/DefinitelyTyped

declare module BraintreePlugin {

    interface BraintreePluginStatic {

        /**
         * Used to initialize the Braintree client.
         * 
         * The client must be initialized before other methods can be used.
         * 
         * @param token The client token or tokenization key to use with the Braintree client.
         * @param successCallback The success callback for this asynchronous function.
         * @param failureCallback The failure callback for this asynchronous function; receives an error string.
         */
        initialize(token: string, successCallback?: () => void, failureCallback?: (error: string) => void): void;

        /**
         * Shows Braintree's drop-in payment UI.
         * 
         * @param successCallback The success callback for this asynchronous function; receives a result object.
         * @param failureCallback The failure callback for this asynchronous function; receives an error string.
         */
        presentDropInPaymentUI(options?: PaymentUIOptions, successCallback?: (result: PaymentUIResult) => void, failureCallback?: (error: string) => void): void;
    }

    /**
     * Options for the presentDropInPaymentUI method.
     */
    interface PaymentUIOptions {

        /**
         * Text to show on the cancel button.
         * Defaults to "Cancel".
         */
        cancelText?: string;

        /**
         * Text to show on the title of the drop-in UI.
         * Defaults to empty string.
         */
        title?: string;

        /**
         * Text to show on the call to action button in the drop-in UI.
         * Defaults to "Select Payment Method".
         */
        ctaText?: string;

        /**
         * The amount of the transaction to show in the drop-in UI on the
         * summary row as well as the call to action button.
         * Defaults to empty string.
         */
        amount?: string;

        /**
         * The description of the transaction to show in the drop-in UI on the
         * summary row.
         * Defaults to empty string.
         */
        primaryDescription?: string;

        /**
         * The description of the transaction to show in the drop-in UI on the
         * summary row.
         * Defaults to empty string.
         */
        secondaryDescription?: string;
    }

    /**
     * Successful callback result for the presentDropInPaymentUI method.
     */
    interface PaymentUIResult {

        /**
         * Indicates if the user used the cancel button to close the dialog without
         * completing the payment.
         */
        userCancelled: boolean;

        /**
         * The nonce for the payment transaction (if a payment was completed).
         */
        nonce: string;

        /**
         * The payment type (if a payment was completed).
         */
        type: string;

        /**
         * A description of the payment method (if a payment was completed).
         */
        localizedDescription: string;

        /**
         * Information about the credit card used to complete a payment (if a credit card was used).
         */
        card: {

            /**
             * The last two digits of the credit card used.
             */
            lastTwo: string;

            /**
             * An enumerated value used to indicate the type of credit card used.
             * 
             * Can be one of the following values:
             * 
             * BTCardNetworkUnknown
             * BTCardNetworkAMEX
             * BTCardNetworkDinersClub
             * BTCardNetworkDiscover
             * BTCardNetworkMasterCard
             * BTCardNetworkVisa
             * BTCardNetworkJCB
             * BTCardNetworkLaser
             * BTCardNetworkMaestro
             * BTCardNetworkUnionPay
             * BTCardNetworkSolo
             * BTCardNetworkSwitch
             * BTCardNetworkUKMaestro
             */
            network: string;
        };

        /**
         * Information about the PayPal account used to complete a payment (if a PayPal account was used).
         */
        payPalAccount: {
            email: string;
            firstName: string;
            lastName: string;
            phone: string;
            billingAddress: string;
            shippingAddress: string;
            clientMetadataId: string;
            payerId: string;
        };

        /**
         * Information about the Apple Pay card used to complete a payment (if Apple Pay was used).
         */
        applePaycard: {
        };

        /**
         * Information about 3D Secure card used to complete a payment (if 3D Secure was used).
         */
        threeDSecureCard: {
            liabilityShifted: boolean;
            liabilityShiftPossible: boolean;
        };

        /**
         * Information about Venmo account used to complete a payment (if a Venmo account was used).
         */
        venmoAccount: {
            username: string;
        };
    }
}

declare var BraintreePlugin: BraintreePlugin.BraintreePluginStatic;
