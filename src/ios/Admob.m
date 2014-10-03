//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://www.github.com/cranberrygame
//License: MIT (http://opensource.org/licenses/MIT)
#import "Admob.h"
//
#import "GADAdMobExtras.h"
#import "GADAdSize.h"
#import "GADBannerView.h"
#import "GADInterstitial.h"
#import "MainViewController.h"

@implementation Admob

@synthesize adUnit;
@synthesize adUnitFullScreen;
@synthesize isOverlap;
@synthesize isTest;
//
@synthesize bannerView;
@synthesize interstitialView;
@synthesize bannerViewCallbackId;
@synthesize interstitialViewCallbackId;
//
@synthesize bannerAdPreload;	
@synthesize fullScreenAdPreload;	
@synthesize position;
@synthesize size;
@synthesize lastOrientation;

- (CDVPlugin *)initWithWebView:(UIWebView *)theWebView {
    self = (Admob *)[super initWithWebView:theWebView];
    if (self) {
        [[UIDevice currentDevice] beginGeneratingDeviceOrientationNotifications];
        [[NSNotificationCenter defaultCenter]
         addObserver:self
         selector:@selector(deviceOrientationChangeAdmob:)
         name:UIDeviceOrientationDidChangeNotification
         object:nil];
    }
    return self;
}

- (void)deviceOrientationChangeAdmob:(NSNotification *)notification{
    if (bannerView != nil)
    {
/*	
        CGRect bannerFrame = bannerView.frame;
        if (bannerFrame.origin.y != 0)
        {
            bannerFrame.origin.y = self.webView.frame.size.width - bannerView.frame.size.height;
        }
        bannerFrame.origin.x = self.webView.frame.size.height * 0.5f - bannerView.frame.size.width * 0.5f;
        bannerView.frame = bannerFrame;
*/
    }
}

- (bool)_isLandscape {
    bool landscape = NO;
        
    UIDeviceOrientation currentOrientation = [[UIDevice currentDevice] orientation];
    if (UIInterfaceOrientationIsLandscape(currentOrientation)) {
        landscape = YES;
    }
    return landscape;
}

- (void)setUp: (CDVInvokedUrlCommand*)command {
    //self.viewController
    //
	NSString *adUnit = [command.arguments objectAtIndex: 0];
	NSLog(@"%@", adUnit);
	NSString *adUnitFullScreen = [command.arguments objectAtIndex: 1];
	NSLog(@"%@", adUnitFullScreen);
	BOOL isOverlap = [[command.arguments objectAtIndex: 2] boolValue];
	NSLog(@"%b", isOverlap);
	BOOL isTest = [[command.arguments objectAtIndex: 3] boolValue];
	NSLog(@"%b", isTest);

	[self _setUp:adUnit anAdUnitFullScreen:adUnitFullScreen aIsOverlap:isOverlap aIsTest:isTest];

	CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
	//[pr setKeepCallbackAsBool:YES];
	[self.commandDelegate sendPluginResult:pr callbackId:command.callbackId];
	//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
	//[pr setKeepCallbackAsBool:YES];
	//[self.commandDelegate sendPluginResult:pr callbackId:command.callbackId];	
}

- (void)preloadBannerAd: (CDVInvokedUrlCommand*)command {
    //self.viewController

	self.bannerAdPreload = YES;
	
	[self _preloadBannerAd];

    self.bannerViewCallbackId = command.callbackId;
}

- (void)reloadBannerAd: (CDVInvokedUrlCommand*)command {
    //self.viewController

	[self _reloadBannerAd];

    self.bannerViewCallbackId = command.callbackId;
}

- (void)showBannerAd: (CDVInvokedUrlCommand*)command {
    //self.viewController
    //
	NSString *position = [command.arguments objectAtIndex: 0];
	NSLog(@"%@", position);
	NSString *size = [command.arguments objectAtIndex: 1];
	NSLog(@"%@", size);

	[self _showBannerAd:position aSize:size];

    self.bannerViewCallbackId = command.callbackId;
}

- (void)hideBannerAd: (CDVInvokedUrlCommand*)command {
    //self.viewController

	[self _hideBannerAd];

    self.bannerViewCallbackId = command.callbackId;
}
- (void)preloadFullScreenAd: (CDVInvokedUrlCommand*)command {
    //self.viewController

	self.fullScreenAdPreload = YES;	
	
	[self _preloadFullScreenAd];

    self.interstitialViewCallbackId = command.callbackId;	
}

- (void)reloadFullScreenAd: (CDVInvokedUrlCommand*)command {
    //self.viewController

	self.fullScreenAdPreload = YES;	
	
	[self _reloadFullScreenAd];
	
    self.interstitialViewCallbackId = command.callbackId;	
}

- (void)showFullScreenAd: (CDVInvokedUrlCommand*)command {
    //self.viewController
	
	[self _showFullScreenAd];
    
    self.interstitialViewCallbackId = command.callbackId;
}

//-------------------------------------------------------------------
- (void) _setUp:(NSString *)adUnit anAdUnitFullScreen:(NSString *)adUnitFullScreen aIsOverlap:(BOOL)isOverlap aIsTest:(BOOL)isTest
{
	self.adUnit = adUnit;
	self.adUnitFullScreen = adUnitFullScreen;
	self.isOverlap = isOverlap;
	self.isTest = isTest;
}
- (void) _preloadBannerAd
{
	if (isOverlap)
		[self _preloadBannerAd_overlap];
	else
		[self _preloadBannerAd_split];

    if (bannerView == nil)
    {
		if(size == nil) {
			size = @"SMART_BANNER";
		}	
		GADAdSize adSize = kGADAdSizeBanner;
		//https://developers.google.com/mobile-ads-sdk/docs/admob/android/banner			
		if ([size isEqualToString:@"BANNER"]) {
			adSize = kGADAdSizeBanner;//Banner (320x50, Phones and Tablets)
		} 
		else if ([size isEqualToString:@"LARGE_BANNER"]) {
			adSize = kGADAdSizeLargeBanner;//Large banner (320x100, Phones and Tablets)
		}
		else if ([size isEqualToString:@"MEDIUM_RECTANGLE"]) {
			adSize = kGADAdSizeMediumRectangle;//Medium rectangle (300x250, Phones and Tablets)
		}
		else if ([size isEqualToString:@"FULL_BANNER"]) {
			adSize = kGADAdSizeFullBanner;//Full banner (468x60, Tablets)
		}
		else if ([size isEqualToString:@"LEADERBOARD"]) {
			adSize = kGADAdSizeLeaderboard;//Leaderboard (728x90, Tablets)
		}
		else if ([size isEqualToString:@"SKYSCRAPER"]) {
			adSize = kGADAdSizeSkyscraper;//Skyscraper (120x600, Tablets)
		}		
		else if ([size isEqualToString:@"SMART_BANNER"]) {
			if([self _isLandscape]) {
				adSize = kGADAdSizeSmartBannerLandscape;//Smart banner (Auto size, Phones and Tablets) //https://developers.google.com/mobile-ads-sdk/docs/admob/android/banner#smart
			}
			else {
				adSize = kGADAdSizeSmartBannerPortrait;
			}			
		}
		else {
			if([self _isLandscape]) {
				adSize = kGADAdSizeSmartBannerLandscape;
			}
			else {
				adSize = kGADAdSizeSmartBannerPortrait;
			}			
		}		
		
		bannerView = [[GADBannerView alloc] initWithAdSize:adSize];
		bannerView.adUnitID = self.adUnit;
		bannerView.delegate = self;
		bannerView.rootViewController = self.viewController;//
	}
    
    [self _reloadBannerAd];
}
- (void) _preloadBannerAd_overlap
{
    if (bannerView != nil)
    {
        //https://developer.apple.com/library/ios/documentation/uikit/reference/UIView_Class/UIView/UIView.html#//apple_ref/occ/cl/UIView
        [self.bannerView removeFromSuperview];
		self.bannerView = nil;
	}
}
- (void) _preloadBannerAd_split
{

}
- (void) _reloadBannerAd
{
	if (bannerView != nil)
	{
		GADRequest *request = [GADRequest request];
		if (isTest) {
		/*
			request.testDevices =
			[NSArray arrayWithObjects:
			GAD_SIMULATOR_ID,
			// TODO: Add your device/simulator test identifiers here. They are printed to the console when the app is launched.			
			nil];
		*/
			//https://github.com/acyl/phonegap-plugins-1/blob/master/iOS/AdMobPlugin/AdMobPlugin.m
			request.testing = YES;
		}
		[self.bannerView loadRequest:request];	
	}
}
- (void) _showBannerAd:(NSString *)position aSize:(NSString *)size
{
	self.position = position;
	self.size = size;

	if(bannerAdPreload) {
		bannerAdPreload = NO;
	}
	else{
		[self _preloadBannerAd];
	}
			
	if (isOverlap)
		[self _showBannerAd_overlap:position aSize:size];
	else
		[self _showBannerAd_split:position aSize:size];
}
- (void) _showBannerAd_overlap:(NSString *)position aSize:(NSString *)size
{
/*
		//http://tigerwoods.tistory.com/11
		//http://developer.android.com/reference/android/widget/RelativeLayout.html
		//http://stackoverflow.com/questions/24900725/admob-banner-poitioning-in-android-on-bottom-of-the-screen-using-no-xml-relative
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(AdView.LayoutParams.WRAP_CONTENT, AdView.LayoutParams.WRAP_CONTENT);
		if (position.equals("top-left")) {
			Log.d("Admob", "top-left");		
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);		
		}
		else if (position.equals("top-center")) {		
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		}
		else if (position.equals("top-right")) {		
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		}
		else if (position.equals("left")) {
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);			
		}
		else if (position.equals("center")) {
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);	
			params.addRule(RelativeLayout.CENTER_VERTICAL);	
		}
		else if (position.equals("right")) {	
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);	
		}
		else if (position.equals("bottom-left")) {		
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);		
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);		
		}
		else if (position.equals("bottom-center")) {
			
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		}
		else if (position.equals("bottom-right")) {
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		}
		else {		
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		}
		
		//bannerViewLayout.addView(bannerView, params);
		bannerView.setLayoutParams(params);
		bannerViewLayout.addView(bannerView);
*/
    CGRect bannerFrame = bannerView.frame;
	if ([position isEqualToString:@"top-center"]) {		    
		bannerFrame.origin.y = 0;
	}
	else {
		bannerFrame.origin.y = self.webView.frame.size.height - bannerView.frame.size.height;
	}
    bannerFrame.origin.x = self.webView.frame.size.width * 0.5f - bannerView.frame.size.width * 0.5f;
    bannerView.frame = bannerFrame;
    //https://developer.apple.com/library/ios/documentation/uikit/reference/UIView_Class/UIView/UIView.html#//apple_ref/occ/cl/UIView
	[self.webView addSubview:bannerView];
}
- (void) _showBannerAd_split:(NSString *)position aSize:(NSString *)size
{
/*
		if (webView != null) {							
			ViewGroup parentView = (ViewGroup)webView.getParent();
			if (parentView != null) {
				if (position.equals("top-left") || position.equals("top-center")|| position.equals("top-right") || position.equals("left") || position.equals("center") || position.equals("right")) {	
					parentView.addView(bannerView, 0);
				}
				else {		
					parentView.addView(bannerView);
				}
				//parentView.bringToFront();
			}
		}
*/

}
- (void) _hideBannerAd
{
	if (isOverlap)
		[self _hideBannerAd_overlap];
	else
		[self _hideBannerAd_split];
}
- (void) _hideBannerAd_overlap
{
    if (bannerView != nil)
    {
        //https://developer.apple.com/library/ios/documentation/uikit/reference/UIView_Class/UIView/UIView.html#//apple_ref/occ/cl/UIView
        [self.bannerView removeFromSuperview];
		bannerView = nil;	
	}
}
- (void) _hideBannerAd_split
{
}
- (void) _preloadFullScreenAd
{    
    if (interstitialView == nil || self.interstitialView.hasBeenUsed){//ios only //An interstitial object can only be used once for ios
        self.interstitialView = [[GADInterstitial alloc] init];
        self.interstitialView.adUnitID = adUnitFullScreen;
        self.interstitialView.delegate = self;
    }	

	[self _reloadFullScreenAd];
}
- (void) _reloadFullScreenAd
{
    if (interstitialView == nil || self.interstitialView.hasBeenUsed){//ios only //An interstitial object can only be used once for ios
        self.interstitialView = [[GADInterstitial alloc] init];
        self.interstitialView.adUnitID = adUnitFullScreen;
        self.interstitialView.delegate = self;
    }
    
	if (interstitialView != nil) {
		GADRequest *request = [GADRequest request];
		if (isTest) {
		/*
			request.testDevices =
			[NSArray arrayWithObjects:
			GAD_SIMULATOR_ID,
			// TODO: Add your device/simulator test identifiers here. They are printed to the console when the app is launched.			
			nil];
		*/
			//https://github.com/acyl/phonegap-plugins-1/blob/master/iOS/AdMobPlugin/AdMobPlugin.m
			request.testing = YES;
		}		
		[self.interstitialView loadRequest:request];
	}
}
- (void) _showFullScreenAd
{
	if(fullScreenAdPreload) {
		[self.interstitialView presentFromRootViewController:self.viewController];
		
		fullScreenAdPreload = NO;
	}
	else{
		[self _preloadFullScreenAd];
	}		
}

//GADBannerViewDelegate
- (void)adViewDidReceiveAd:(GADBannerView *)view {
	NSLog(@"adViewDidReceiveAd");

	if(bannerAdPreload) {
		NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
		[dict setObject:@"onBannerAdPreloaded" forKey:@"result"];
		CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dict];
		[pr setKeepCallbackAsBool:YES];
		[self.commandDelegate sendPluginResult:pr callbackId:self.bannerViewCallbackId];
		//NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
		//[dict setObject:@"onBannerAdPreloaded" forKey:@"result"];
		//pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:dict];
		//[pr setKeepCallbackAsBool:YES];
		//[self.commandDelegate sendPluginResult:pr callbackId:self.bannerViewCallbackId];		
	}
	
	NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
	[dict setObject:@"onBannerAdLoaded" forKey:@"result"];
	CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dict];
	//[pr setKeepCallbackAsBool:YES];
	[self.commandDelegate sendPluginResult:pr callbackId:self.bannerViewCallbackId];
	//NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
	//[dict setObject:@"onBannerAdLoaded" forKey:@"result"];
	//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:dict];
	//[pr setKeepCallbackAsBool:YES];
	//[self.commandDelegate sendPluginResult:pr callbackId:self.bannerViewCallbackId];	
	
	bannerView.hidden = NO;
}
- (void)adView:(GADBannerView *)view didFailToReceiveAdWithError:(GADRequestError *)error {
	NSLog(@"adView: %@",error.description);
	bannerView.hidden = YES;
}
- (void)adViewWillLeaveApplication:(GADBannerView *)adView {
	NSLog(@"adViewWillLeaveApplication");
}
- (void)adViewWillPresentScreen:(GADBannerView *)adView {
	NSLog(@"adViewWillPresentScreen");
}
- (void)adViewWillDismissScreen:(GADBannerView *)adView {
	NSLog(@"adViewWillDismissScreen");
}
- (void)adViewDidDismissScreen:(GADBannerView *)adView {
	NSLog(@"adViewDidDismissScreen");
}
//GADInterstitialDelegate
- (void)interstitialDidReceiveAd:(GADInterstitial *)ad {
	NSLog(@"interstitialDidReceiveAd");
	
	if(fullScreenAdPreload) {
		NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
		[dict setObject:@"onFullScreenAdPreloaded" forKey:@"result"];
		CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dict];
		[pr setKeepCallbackAsBool:YES];
		[self.commandDelegate sendPluginResult:pr callbackId:self.interstitialViewCallbackId];
		//NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
		//[dict setObject:@"onFullScreenAdPreloaded" forKey:@"result"];
		//pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:dict];
		//[pr setKeepCallbackAsBool:YES];
		//[self.commandDelegate sendPluginResult:pr callbackId:self.interstitialViewCallbackId];			
	}
	
	NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
	[dict setObject:@"onFullScreenAdLoaded" forKey:@"result"];
	CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dict];
	[pr setKeepCallbackAsBool:YES];
	[self.commandDelegate sendPluginResult:pr callbackId:self.interstitialViewCallbackId];
	//NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
	//[dict setObject:@"onFullScreenAdLoaded" forKey:@"result"];
	//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:dict];
	//[pr setKeepCallbackAsBool:YES];
	//[self.commandDelegate sendPluginResult:pr callbackId:self.interstitialViewCallbackId];		
	
	if(!fullScreenAdPreload) {
		[self.interstitialView presentFromRootViewController:self.viewController];
	}
}
- (void)interstitial:(GADInterstitial *)ad didFailToReceiveAdWithError:(GADRequestError *)error {
	NSLog(@"interstitial: %@",error.description);
}
- (void)interstitialWillLeaveApplication:(GADInterstitial *)ad {
	NSLog(@"interstitialWillLeaveApplication");
}
- (void)interstitialWillPresentScreen:(GADInterstitial *)ad {
	NSLog(@"interstitialWillPresentScreen");
	
    NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
    [dict setObject:@"onFullScreenAdShown" forKey:@"result"];
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dict];
	[pr setKeepCallbackAsBool:YES];
	[self.commandDelegate sendPluginResult:pr callbackId:self.interstitialViewCallbackId];
    //NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
    //[dict setObject:@"onFullScreenAdShown" forKey:@"result"];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:dict];
	//[pr setKeepCallbackAsBool:YES];
	//[self.commandDelegate sendPluginResult:pr callbackId:self.interstitialViewCallbackId];		
}
- (void)interstitialWillDismissScreen:(GADInterstitial *)ad {
	NSLog(@"interstitialWillDismissScreen");
}
- (void)interstitialDidDismissScreen:(GADInterstitial *)ad {
	NSLog(@"interstitialDidDismissScreen");
	
    NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
    [dict setObject:@"onFullScreenAdHidden" forKey:@"result"];
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dict];
	//[pr setKeepCallbackAsBool:YES];
	[self.commandDelegate sendPluginResult:pr callbackId:self.interstitialViewCallbackId];	
    //NSMutableDictionary* dict = [NSMutableDictionary dictionaryWithCapacity:1];
    //[dict setObject:@"onFullScreenAdHidden" forKey:@"result"];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:dict];
	//[pr setKeepCallbackAsBool:YES];
	//[self.commandDelegate sendPluginResult:pr callbackId:self.interstitialViewCallbackId];	
}

- (void)dealloc {
	[[UIDevice currentDevice] endGeneratingDeviceOrientationNotifications];
	[[NSNotificationCenter defaultCenter]
     removeObserver:self
     name:UIDeviceOrientationDidChangeNotification
     object:nil];
}

@end
