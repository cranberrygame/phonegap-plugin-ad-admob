//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://www.github.com/cranberrygame
//License: MIT (http://opensource.org/licenses/MIT)
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
@property NSString *bannerViewCallbackId;
@property NSString *interstitialViewCallbackId;
//
@property BOOL bannerAdPreload;	
@property BOOL fullScreenAdPreload;	
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
