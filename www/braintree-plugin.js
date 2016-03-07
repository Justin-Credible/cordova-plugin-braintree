"use strict";

var exec = require("cordova/exec");

/**
 * The Cordova plugin ID for this plugin.
 */
var PLUGIN_ID = "BraintreePlugin";

/**
 * The plugin which will be exported and exposed in the global scope.
 */
var BraintreePlugin = {};

/**
 * Used to initialize the Braintree client.
 * 
 * The client must be initialized before other methods can be used.
 * 
 * @param {string} token - The client token or tokenization key to use with the Braintree client.
 * @param [function] successCallback - The success callback for this asynchronous function.
 * @param [function] failureCallback - The failure callback for this asynchronous function; receives an error string.
 */
BraintreePlugin.initialize = function initialize(token, successCallback, failureCallback) {

    if (!token || typeof(token) !== "string") {
        failureCallback("A non-null, non-empty string must be provided for the token parameter.");
        return;
    }

    exec(successCallback, failureCallback, PLUGIN_ID, "initialize", [token]);
};

/**
 * Shows Braintree's drop-in payment UI.
 * 
 * @param [function] successCallback - The success callback for this asynchronous function; receives a result object.
 * @param [function] failureCallback - The failure callback for this asynchronous function; receives an error string.
 */
BraintreePlugin.presentDropInPaymentUI = function showDropInUI(options, successCallback, failureCallback) {

    if (!options) {
        options = {};
    }

    if (typeof(options.cancelText) !== "string") {
        options.cancelText = "Cancel";
    }

    if (typeof(options.title) !== "string") {
        options.title = "";
    };

    if (typeof(options.ctaText) !== "string") {
        options.ctaText = "Select Payment Method";
    };

    var pluginOptions = [
        options.cancelText,
        options.title,
        options.ctaText
    ];

    exec(successCallback, failureCallback, PLUGIN_ID, "presentDropInPaymentUI", pluginOptions);
};

module.exports = BraintreePlugin;
