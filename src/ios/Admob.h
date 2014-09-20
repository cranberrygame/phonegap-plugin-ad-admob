// Copyright (c) 2014 cranberrygame
// Email: cranberrygame@yahoo.com
// Phonegap plugin: http://www.github.com/cranberrygame
// Construct2 phonegap plugin: https://www.scirra.com/forum/viewtopic.php?f=153&t=109586
// License: MIT (http://opensource.org/licenses/MIT)
#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
//
#import "GADBannerViewDelegate.h"
#import "GADInterstitialDelegate.h"
#import <UIKit/UIKit.h>

@interface Admob : CDVPlugin <GADBannerViewDelegate, GADInterstitialDelegate>

@property NSString *adUnitFullScreen;
@property NSString *adUnit;
@property BOOL isOverlap;
@property BOOL isTest;
//
@property GADBannerView *bannerView;
@property GADInterstitial *interstitialView;
@property NSString *interstitialViewCallbackId;
//
@property BOOL bannerAdPreloaded;	
@property BOOL fullScreenAdPreloaded;	
@property NSString *position;
@property NSString *size;
@property NSInteger lastOrientation;

- (void)setUp: (CDVInvokedUrlCommand*)command;
- (void)preloadBannerAd: (CDVInvokedUrlCommand*)command;
- (void)showBannerAd: (CDVInvokedUrlCommand*)command;
- (void)hideBannerAd: (CDVInvokedUrlCommand*)command;
- (void)reloadBannerAd: (CDVInvokedUrlCommand*)command;
- (void)preloadFullScreenAd: (CDVInvokedUrlCommand*)command;
- (void)showFullScreenAd: (CDVInvokedUrlCommand*)command;
- (void)reloadFullScreenAd: (CDVInvokedUrlCommand*)command;

@end
