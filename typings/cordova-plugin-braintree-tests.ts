/// <reference path="cordova-plugin-braintree.d.ts" />

BraintreePlugin.initialize("a");
BraintreePlugin.initialize("a", () => {});
BraintreePlugin.initialize("a", () => {}, () => {});

var paymentUIOptions: BraintreePlugin.PaymentUIOptions = {
    cancelText: "Cancel",
    title: "Purchase",
    ctaText: "Select Payment Method",
    amount: "$49.99",
    primaryDescription: "Your Item",
    secondaryDescription: "Free shipping!"
};

BraintreePlugin.presentDropInPaymentUI();
BraintreePlugin.presentDropInPaymentUI(paymentUIOptions);
BraintreePlugin.presentDropInPaymentUI(paymentUIOptions, (result: BraintreePlugin.PaymentUIResult) => {});
BraintreePlugin.presentDropInPaymentUI(paymentUIOptions, (result: BraintreePlugin.PaymentUIResult) => {}, () => {});
