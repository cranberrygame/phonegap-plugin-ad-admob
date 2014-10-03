
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
		var self = this;	
        cordova.exec(
            function (result) {
				if (result == "onBannerAdPreloaded" || result['result'] == "onBannerAdPreloaded" ) {
					self.onBannerAdPreloaded();
				}
				else if (result == "onBannerAdLoaded" || result['result'] == "onBannerAdLoaded" ) {
					self.onBannerAdLoaded();
				}
			},
            errorCallback,
            'Admob',
            'preloadBannerAd',
            []
        ); 
    },
	reloadBannerAd: function(successCallback, errorCallback) {
		var self = this;	
        cordova.exec(
            function (result) {
				if (result == "onBannerAdPreloaded" || result['result'] == "onBannerAdPreloaded" ) {
					self.onBannerAdPreloaded();
				}
				else if (result == "onBannerAdLoaded" || result['result'] == "onBannerAdLoaded" ) {
					self.onBannerAdLoaded();
				}
			},
            errorCallback,
            'Admob',
            'reloadBannerAd',
            []
        ); 
    },
    showBannerAd: function(position, size, successCallback, errorCallback) {
		var self = this;	
        cordova.exec(
            function (result) {
				if (result == "onBannerAdPreloaded" || result['result'] == "onBannerAdPreloaded" ) {
					self.onBannerAdPreloaded();
				}
				else if (result == "onBannerAdLoaded" || result['result'] == "onBannerAdLoaded" ) {
					self.onBannerAdLoaded();
				}
			},
            errorCallback,
            'Admob',
            'showBannerAd',
            [position, size]
        ); 
    },
    hideBannerAd: function(successCallback, errorCallback) {
		var self = this;	
        cordova.exec(
            function (result) {
				if (result == "onBannerAdPreloaded" || result['result'] == "onBannerAdPreloaded" ) {
					self.onBannerAdPreloaded();
				}
				else if (result == "onBannerAdLoaded" || result['result'] == "onBannerAdLoaded" ) {
					self.onBannerAdLoaded();
				}
			},
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
				if (result == "onFullScreenAdPreloaded" || result['result'] == "onFullScreenAdPreloaded" ) {
					self.onFullScreenAdPreloaded();
				}
				else if (result == "onFullScreenAdLoaded" || result['result'] == "onFullScreenAdLoaded" ) {
					self.onFullScreenAdLoaded();
				}
				else if (result == "onFullScreenAdShown" || result['result'] == "onFullScreenAdShown" ) {
					self.onFullScreenAdShown();
				}
				else if (result == "onFullScreenAdHidden" || result['result'] == "onFullScreenAdHidden" ) {
					 if (self.onFullScreenAdClosed)
						self.onFullScreenAdClosed(); //deprecated
					 if (self.onFullScreenAdHidden)
						self.onFullScreenAdHidden();
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
				if (result == "onFullScreenAdPreloaded" || result['result'] == "onFullScreenAdPreloaded" ) {
					self.onFullScreenAdPreloaded();
				}
				else if (result == "onFullScreenAdLoaded" || result['result'] == "onFullScreenAdLoaded" ) {
					self.onFullScreenAdLoaded();
				}
				else if (result == "onFullScreenAdShown" || result['result'] == "onFullScreenAdShown" ) {
					self.onFullScreenAdShown();
				}
				else if (result == "onFullScreenAdHidden" || result['result'] == "onFullScreenAdHidden" ) {
					 if (self.onFullScreenAdClosed)
						self.onFullScreenAdClosed(); //deprecated
					 if (self.onFullScreenAdHidden)
						self.onFullScreenAdHidden();
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
				if (result == "onFullScreenAdPreloaded" || result['result'] == "onFullScreenAdPreloaded" ) {
					self.onFullScreenAdPreloaded();
				}
				else if (result == "onFullScreenAdLoaded" || result['result'] == "onFullScreenAdLoaded" ) {
					self.onFullScreenAdLoaded();
				}
				else if (result == "onFullScreenAdShown" || result['result'] == "onFullScreenAdShown" ) {
					self.onFullScreenAdShown();
				}
				else if (result == "onFullScreenAdHidden" || result['result'] == "onFullScreenAdHidden" ) {
					 if (self.onFullScreenAdClosed)
						self.onFullScreenAdClosed(); //deprecated
					 if (self.onFullScreenAdHidden)
						self.onFullScreenAdHidden();
				}
			},
            errorCallback,
            'Admob',
            'showFullScreenAd',
            []
        ); 
    },
	onBannerAdPreloaded: null,
	onBannerAdLoaded: null,
	onFullScreenAdPreloaded: null,
	onFullScreenAdLoaded: null,
	onFullScreenAdShown: null,
	onFullScreenAdClosed: null, //deprecated
	onFullScreenAdHidden: null
};
