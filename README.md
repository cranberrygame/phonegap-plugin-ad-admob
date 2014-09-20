
How to Install:

cordova plugin add com.cranberrygame.phonegap.plugin.ad.admob

Change Log:
<pre>
2014.9.17
	supports SKYSCRAPER size (120x600, Tablets, ipad only)
	Added additional example (example/banner_position_size/index.html)
2014.9.18
	supports isTest
	supports other position: 'top-left', 'top-right', 'left', 'center', 'right', 'bottom-left', 'bottom-right' on android, wp8.
	supports SMART_BANNER resize when orientation changes on android.
2014.9.20
	supports isOverlap on android, ios, wp8
</pre>
To-Do:
<pre>
	supports other position: 'top-left', 'top-right', 'left', 'center', 'right', 'bottom-left', 'bottom-right' on ios.
	supports banner reposition when orientation changes on ios.
	supports SMART_BANNER resize when orientation changes on ios.	
	supports ios split mode
	supports wp8 split mode
</pre>	
How to Use:

<pre>
var adUnit = "REPLACE_THIS_WITH_YOUR_AD_UNIT";
var adUnitFullScreen = "REPLACE_THIS_WITH_YOUR_AD_UNIT";
var isOverlap = true; //true: overlap, false: split
var isTest = true;

document.addEventListener("deviceready", function(){
	window.admob.setUp(adUnit, adUnitFullScreen, isOverlap, isTest);
		
	window.admob.onFullScreenAdLoaded = function() {
		alert('onFullScreenAdLoaded');
	};
	window.admob.onFullScreenAdShown = function() {
		alert('onFullScreenAdShown');
	};
	window.admob.onFullScreenAdClosed = function() {
		alert('onFullScreenAdClosed');
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
window.admob.reloadFullScreenAd();

</pre>

Example:

<a href="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.ad.admob/blob/master/example/basic/index.html">example/basic/index.html</a>
<a href="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.ad.admob/blob/master/example/banner_position_size/index.html">example/banner_position_size/index.html</a>

Support:

This is 100% Free.<br>
<a href="https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=F9MJ2UY9EKXRN&lc=KR&item_name=Phonegap%20admob%20plugin%20donation&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted">You can support maintenance by donation</a>
