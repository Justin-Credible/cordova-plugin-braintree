//
//  BraintreePlugin.h
//
//  Copyright (c) 2016 Justin Unterreiner. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
#import "MBProgressHUD.h"

@interface BraintreePlugin : CDVPlugin
- (void)initialize:(CDVInvokedUrlCommand *)command;
- (void)presentDropInPaymentUI:(CDVInvokedUrlCommand *)command;
@end
