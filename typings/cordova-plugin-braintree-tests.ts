/// <reference path="cordova-plugin-braintree.d.ts" />

BraintreePlugin.initialize("a");
BraintreePlugin.initialize("a", () => {});
BraintreePlugin.initialize("a", () => {}, () => {});

var paymentUIOptions: BraintreePlugin.PaymentUIOptions = {
    cancelText: "Cancel",
    title: "Title",
    ctaText: "Call to Action"
};

BraintreePlugin.presentDropInPaymentUI();
BraintreePlugin.presentDropInPaymentUI(paymentUIOptions);
BraintreePlugin.presentDropInPaymentUI(paymentUIOptions, (result: BraintreePlugin.PaymentUIResult) => {});
BraintreePlugin.presentDropInPaymentUI(paymentUIOptions, (result: BraintreePlugin.PaymentUIResult) => {}, () => {});
