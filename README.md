
How to Install:

cordova plugin add com.cranberrygame.phonegap.plugin.ad.admob.overlay

Change Log:
<pre>
2014.9.17
	supports SKYSCRAPER size (120x600, Tablets, ipad only)
	Added additional example (example/banner_position_size/index.html)
2014.9.18
	supports isTest
	supports other position: 'top-left', 'top-right', 'left', 'center', 'right', 'bottom-left', 'bottom-right'.
	supports SMART_BANNER resize when orientation changes.
2014.9.20
	supports isOverlap
</pre>
To-Do:
<pre>
	final full test
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
window.admob.refreshBannerAd();
window.admob.hideBannerAd();

window.admob.preloadFullScreenAd();
window.admob.showFullScreenAd();
window.admob.refreshFullScreenAd();

</pre>

Example:

<a href="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.ad.admob.overlay/blob/master/example/basic/index.html">example/basic/index.html</a>
<a href="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.ad.admob.overlay/blob/master/example/banner_position_size/index.html">example/banner_position_size/index.html</a>

