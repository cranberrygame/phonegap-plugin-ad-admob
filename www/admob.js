
module.exports = {

	setUp: function(adUnit, adUnitFullScreen, isOverlap, isTest, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'Admob',
            'setUp',			
            [adUnit, adUnitFullScreen, isOverlap, isTest]
        ); 
    },
	preloadBannerAd: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'Admob',
            'preloadBannerAd',
            []
        ); 
    },
	reloadBannerAd: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'Admob',
            'reloadBannerAd',
            []
        ); 
    },
    showBannerAd: function(position, size, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'Admob',
            'showBannerAd',
            [position, size]
        ); 
    },
    hideBannerAd: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'Admob',
            'hideBannerAd',
            []
        ); 
    },
	//
	preloadFullScreenAd: function(successCallback, errorCallback) {
		var self = this;	
        cordova.exec(
            function (result) {
				if (result == "onFullScreenAdLoaded" || result['result'] == "onFullScreenAdLoaded" ) {
					self.onFullScreenAdLoaded();
				}
				else if (result == "onFullScreenAdShown" || result['result'] == "onFullScreenAdShown" ) {
					self.onFullScreenAdShown();
				}
				else if (result == "onFullScreenAdClosed" || result['result'] == "onFullScreenAdClosed" ) {
					self.onFullScreenAdClosed();
				}
			},
            errorCallback,
            'Admob',
            'preloadFullScreenAd',
            []
        ); 
    },
	reloadFullScreenAd: function(successCallback, errorCallback) {
		var self = this;	
        cordova.exec(
            function (result) {
				if (result == "onFullScreenAdLoaded" || result['result'] == "onFullScreenAdLoaded" ) {
					self.onFullScreenAdLoaded();
				}
				else if (result == "onFullScreenAdShown" || result['result'] == "onFullScreenAdShown" ) {
					self.onFullScreenAdShown();
				}
				else if (result == "onFullScreenAdClosed" || result['result'] == "onFullScreenAdClosed" ) {
					self.onFullScreenAdClosed();
				}
			},
            errorCallback,
            'Admob',
            'reloadFullScreenAd',
            []
        ); 
    },	
    showFullScreenAd: function(successCallback, errorCallback) {
		var self = this;
		cordova.exec(
            function (result) {
				if (result == "onFullScreenAdLoaded" || result['result'] == "onFullScreenAdLoaded" ) {
					self.onFullScreenAdLoaded();
				}
				else if (result == "onFullScreenAdShown" || result['result'] == "onFullScreenAdShown" ) {
					self.onFullScreenAdShown();
				}
				else if (result == "onFullScreenAdClosed" || result['result'] == "onFullScreenAdClosed" ) {
					self.onFullScreenAdClosed();
				}
			},
            errorCallback,
            'Admob',
            'showFullScreenAd',
            []
        ); 
    },
	onFullScreenAdLoaded: null,
	onFullScreenAdShown: null,
	onFullScreenAdClosed: null
};
