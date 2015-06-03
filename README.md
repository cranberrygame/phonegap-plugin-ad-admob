Phonegap AdMob plugin
====================
# Overview #
show admob banner and full screen ad

[android, ios, wp8] [cordova cli] [xdk]

requires admob account http://www.google.com/ads/admob/

this is open source cordova plugin.

You can see Plugins For Cordova in one page: http://cranberrygame.github.io?referrer=github

Changed package name to com.cranberrygame.cordova.plugin.ad.admob

# Change log #
```c
2014.9.17
	supports SKYSCRAPER size (120x600, Tablets, ipad only)
	Added additional example (example/banner_position_size/index.html)
2014.9.18
	supports isTest
	supports other position: 'top-left', 'top-right', 'left', 'center', 'right', 'bottom-left', 'bottom-right' on android, wp8.
	supports SMART_BANNER resize when orientation changes on android.
2014.9.20
	supports isOverlap on android, ios, wp8
2014.9.24
	supports banner ad callback (onBannerAdLoaded)
	supports full screen ad callback (onFullScreenAdLoaded)
2014.10.3
	supports banner ad callback (onBannerAdPreloaded)
	supports full screen ad callback (onFullScreenAdPreloaded)
1.0.31
	Updated Admob SDK
		iOS 6.12.2
1.0.32
	Further update and support will be done frome here: http://plugins.cordova.io/#/package/com.cranberrygame.cordova.plugin.ad.admob (or https://github.com/cranberrygame/com.cranberrygame.cordova.plugin.ad.admob)

To-Do:

	supports ios split mode
	supports wp8 split mode
	supports other position: 'top-left', 'top-right', 'left', 'center', 'right', 'bottom-left', 'bottom-right' on ios.
	supports banner reposition when orientation changes on ios.
	supports SMART_BANNER resize when orientation changes on ios.	
```

# Install plugin #

## Phonegap cli ##
```c
cordova plugin add com.cranberrygame.phonegap.plugin.ad.admob

cf)apple app store meta data

xport Compliance
	Have you added or made changes to encryption features since your last submission of this app? (No)

Advertising Identifier
	Does this app use the Advertising Identifier (IDFA)? (Yes)
	
	This app uses the Advertising Identifier to (select all that apply):
		Serve advertisements within the app (check)
	
	Limit Ad Tracking setting in iOS (check)	
	
Previous Purchase Restrictions
	Are you updating this app because of a significant usability issue or for a legal issue, such as an infringement claim? (No)
```
## Xdk ##
```c
XDK PORJECTS - your_xdk_project - CORDOVA 3.X HYBRID MOBILE APP SETTINGS - PLUGINS AND PERMISSIONS - Third Party Plugins - Add a Third Party Plugin - Get Plugin from the Web -

Name: admob
Plugin ID: com.cranberrygame.phonegap.plugin.ad.admob
[v] Plugin is located in the Apache Cordova Plugins Registry

cf)apple app store meta data

xport Compliance
	Have you added or made changes to encryption features since your last submission of this app? (No)

Advertising Identifier
	Does this app use the Advertising Identifier (IDFA)? (Yes)
	
	This app uses the Advertising Identifier to (select all that apply):
		Serve advertisements within the app (check)
	
	Limit Ad Tracking setting in iOS (check)	
	
Previous Purchase Restrictions
	Are you updating this app because of a significant usability issue or for a legal issue, such as an infringement claim? (No)
```
## Phonegap build service (config.xml) ##
```c
<gap:plugin name="com.cranberrygame.phonegap.plugin.ad.admob" source="plugins.cordova.io" />
```

# Server setting #
```c
```

# API #
```javascript
var adUnit = "REPLACE_THIS_WITH_YOUR_AD_UNIT";
var adUnitFullScreen = "REPLACE_THIS_WITH_YOUR_AD_UNIT";
var isOverlap = true; //true: overlap, false: split
var isTest = true;
/*
var adUnit;
var adUnitFullScreen;
var isOverlap = true; //true: overlap, false: split
var isTest = true;
//android
if (navigator.userAgent.match(/Android/i)) {
	adUnit = "REPLACE_THIS_WITH_YOUR_ANDROID_AD_UNIT";
	adUnitFullScreen = "REPLACE_THIS_WITH_YOUR_ANDROID_AD_UNIT";
}
//ios
else if (navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPad/i)) {
	adUnit = "REPLACE_THIS_WITH_YOUR_IOS_AD_UNIT";
	adUnitFullScreen = "REPLACE_THIS_WITH_YOUR_IOS_AD_UNIT";
}
//wp8
else if( navigator.userAgent.match(/Windows Phone/i) ) {
	adUnit = "REPLACE_THIS_WITH_YOUR_WP8_AD_UNIT";
	adUnitFullScreen = "REPLACE_THIS_WITH_YOUR_WP8_AD_UNIT";
}
*/

document.addEventListener("deviceready", function(){
	window.admob.setUp(adUnit, adUnitFullScreen, isOverlap, isTest);

	//banner ad callback
	window.admob.onBannerAdPreloaded = function() {
		alert('onBannerAdPreloaded');
	};
	window.admob.onBannerAdLoaded = function() {
		alert('onBannerAdLoaded');
	};
	//full screen ad callback
	window.admob.onFullScreenAdPreloaded = function() {
		alert('onFullScreenAdPreloaded');
	};
	window.admob.onFullScreenAdLoaded = function() {
		alert('onFullScreenAdLoaded');
	};
	window.admob.onFullScreenAdShown = function() {
		alert('onFullScreenAdShown');
	};
	window.admob.onFullScreenAdHidden = function() {
		alert('onFullScreenAdHidden');
	};
}, false);

window.admob.preloadBannerAd();
/*
position: 'top-left', 'top-center', 'top-right', 'left', 'center', 'right', 'bottom-left', 'bottom-center', 'bottom-right'
size: 	'BANNER' (320x50, Phones and Tablets)
		'LARGE_BANNER' (320x100, Phones and Tablets)
		'MEDIUM_RECTANGLE' (300x250, Phones and Tablets)
		'FULL_BANNER' (468x60, Tablets)
		'LEADERBOARD' (728x90, Tablets)
		'SKYSCRAPER' (120x600, Tablets, ipad only)
		'SMART_BANNER' (Auto size, Phones and Tablets, recommended)
*/
window.admob.showBannerAd('top-center', 'SMART_BANNER');
window.admob.showBannerAd('bottom-center', 'SMART_BANNER');
window.admob.reloadBannerAd();
window.admob.hideBannerAd();

window.admob.preloadFullScreenAd();
window.admob.showFullScreenAd();
```
# Examples #
<a href="https://github.com/cranberrygame/phonegap-plugin-ad-admob/blob/master/example/basic/index.html">example/basic/index.html</a><br>
<a href="https://github.com/cranberrygame/phonegap-plugin-ad-admob/blob/master/example/advanced/index.html">example/advanced/index.html</a>

# Test #

[![](http://img.youtube.com/vi/xXrVb8E8gMM/0.jpg)](https://www.youtube.com/watch?v=xXrVb8E8gMM&feature=youtu.be "Youtube")

You can also run following test apk. https://dl.dropboxusercontent.com/u/186681453/pluginsforcordova/admob/apk.html

# Useful links #

Plugins For Cordova<br>
http://cranberrygame.github.io?referrer=github

# Credits #
