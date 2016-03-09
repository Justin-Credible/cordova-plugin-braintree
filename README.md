# Braintree Cordova Plugin

This is a [Cordova](http://cordova.apache.org/) plugin for the [Braintree](https://www.braintreepayments.com/) mobile payment processing SDK.

This version of the plugin uses versions `4.1.3` (iOS) and `2.1.2` (Android) of the Braintree mobile SDK. Documentation for the Braintree SDK can be found [here](https://developers.braintreepayments.com/start/overview).

**This plugin is still in development.**

# Install

To add the plugin to your Cordova project, simply add the plugin from the npm registry:

    cordova plugin add cordova-plugin-braintree

Alternatively, you can install the latest version of the plugin directly from git:

    cordova plugin add https://github.com/Justin-Credible/cordova-plugin-braintree

# Usage

The plugin is available via a global variable named `BraintreePlugin`. It exposes the following properties and functions.

All functions accept optional success and failure callbacks as their last two arguments, where the failure callback will receive an error string as an argument unless otherwise noted.

A TypeScript definition file for the JavaScript interface is available in the `typings` directory as well as on [DefinitelyTyped](https://github.com/borisyankov/DefinitelyTyped) via the `tsd` tool.

## Initialize Braintree Client ##

Used to initialize the Braintree client. The client must be initialized before other methods can be used.

Method Signature:

`initialize(token, successCallback, failureCallback)`

Parameters:

* `token` (string): The unique client token or static tokenization key to use.

Example Usage:

```
var token = "YOUR_TOKEN";

BraintreePlugin.initialize(token,
    function () { console.log("init OK!"); },
    function (error) { console.error(error); });
```

## Show Drop-In Payment UI ##

Used to show Braintree's drop-in UI for accepting payments.

Method Signature:

`presentDropInPaymentUI(options, successCallback, failureCallback)`

Parameters:

* `options` (object): An optional argument used to configure the payment UI; see type definition for parameters.

Example Usage:

```
var options = {
    cancelText: "Cancel",
    title: "Purchase",
    ctaText: "Select Payment Method",
    amount: "$49.99",
    primaryDescription: "Your Item",
    secondaryDescription :"Free shipping!"
};

BraintreePlugin.presentDropInPaymentUI(options, function (result) {

    if (result.userCancelled) {
        console.debug("User cancelled payment dialog.");
    }
    else {
        console.info("User completed payment dialog.");
        console.info("Payment Nonce: " + result.nonce);
        console.debug("Payment Result.", result);
    }
});
```
