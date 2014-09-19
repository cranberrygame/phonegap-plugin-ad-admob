// Copyright (c) 2014 cranberrygame
// Email: cranberrygame@yahoo.com
// Phonegap plugin: http://www.github.com/cranberrygame
// Construct2 phonegap plugin: https://www.scirra.com/forum/viewtopic.php?f=153&t=109586
// License: MIT (http://opensource.org/licenses/MIT)
using System.Windows;
using System.Runtime.Serialization;
using WPCordovaClassLib.Cordova;
using WPCordovaClassLib.Cordova.Commands;
using WPCordovaClassLib.Cordova.JSON;
using System.Diagnostics; //Debug.WriteLine
//
using GoogleAds;
using System.Windows.Controls;
using Microsoft.Phone.Controls;

namespace Cordova.Extension.Commands
{
    public class Admob : BaseCommand
    {
		private string adUnit;
		private string adUnitFullScreen;
		private boolean isOverlap;
		private boolean isTest;
		//
        private AdView bannerView;
        private InterstitialAd interstitialView;
		//
		public bool bannerAdPreloaded;	
		public bool fullScreenAdPreloaded;	
		private string position;
		private string size;
		private int lastOrientation;
	
		public void setUp(string args)
        {
            string adUnit = JsonHelper.Deserialize<string[]>(args)[0];
            Debug.WriteLine("adUnit: " + adUnit);
            string adUnitFullScreen = JsonHelper.Deserialize<string[]>(args)[1];
            Debug.WriteLine("adUnitFullScreen: " + adUnitFullScreen);
            boolean isOverlap = JsonHelper.Deserialize<string[]>(args)[2];
            Debug.WriteLine("isOverlap: " + isOverlap);
            boolean isTest = JsonHelper.Deserialize<string[]>(args)[3];
            Debug.WriteLine("isTest: " + isTest);

            Deployment.Current.Dispatcher.BeginInvoke(() =>
            {   
                _setUp(adUnit, adUnitFullScreen, isOverlap, isTest);
                
				DispatchCommandResult(new PluginResult(PluginResult.Status.OK));			
				//DispatchCommandResult(new PluginResult(PluginResult.Status.ERROR));
            });					
        }
        public void preloadBannerAd(string args)
        {
			bannerAdPreloaded = true;	

            Deployment.Current.Dispatcher.BeginInvoke(() =>
            {   
                _preloadBannerAd();
                
				DispatchCommandResult(new PluginResult(PluginResult.Status.OK));			
				//DispatchCommandResult(new PluginResult(PluginResult.Status.ERROR));
            });
        }
        public void refreshBannerAd(string args)
        {
			bannerAdPreloaded = true;

            Deployment.Current.Dispatcher.BeginInvoke(() =>
            {
                _refreshBannerAd();

				DispatchCommandResult(new PluginResult(PluginResult.Status.OK));			
				//DispatchCommandResult(new PluginResult(PluginResult.Status.ERROR));
            });
        }		
        public void showBannerAd(string args)
        {
			string position=JsonHelper.Deserialize<string[]>(args)[0];
			Debug.WriteLine(position);
			string size=JsonHelper.Deserialize<string[]>(args)[1];
			Debug.WriteLine(size);

            Deployment.Current.Dispatcher.BeginInvoke(() =>
            {
                _showBannerAd(position, size);

				DispatchCommandResult(new PluginResult(PluginResult.Status.OK));			
				//DispatchCommandResult(new PluginResult(PluginResult.Status.ERROR));
            });
        }
        public void hideBannerAd(string args)
        {
            Deployment.Current.Dispatcher.BeginInvoke(() =>
            {
                _hideBannerAd();

				DispatchCommandResult(new PluginResult(PluginResult.Status.OK));			
				//DispatchCommandResult(new PluginResult(PluginResult.Status.ERROR));
            });	
        }
        public void preloadFullScreenAd(string args)
        {
			fullScreenAdPreloaded = true;			

            Deployment.Current.Dispatcher.BeginInvoke(() =>
            {
                _preloadFullScreenAd();

				//DispatchCommandResult(new PluginResult(PluginResult.Status.OK));			
				//DispatchCommandResult(new PluginResult(PluginResult.Status.ERROR));

				PluginResult pr = new PluginResult(PluginResult.Status.OK);
				pr.KeepCallback = true;
				DispatchCommandResult(pr);				
            });
        }
        public void refreshFullScreenAd(string args)
        {
			fullScreenAdPreloaded = true;			

            Deployment.Current.Dispatcher.BeginInvoke(() =>
            {
                _refreshFullScreenAd();

				//DispatchCommandResult(new PluginResult(PluginResult.Status.OK));			
				//DispatchCommandResult(new PluginResult(PluginResult.Status.ERROR));
				
				PluginResult pr = new PluginResult(PluginResult.Status.OK);
				pr.KeepCallback = true;
				DispatchCommandResult(pr);				
            });
        }		
        public void showFullScreenAd(string args)
        {
            Deployment.Current.Dispatcher.BeginInvoke(() =>
            {
                _showFullScreenAd();

				//DispatchCommandResult(new PluginResult(PluginResult.Status.OK));			
				//DispatchCommandResult(new PluginResult(PluginResult.Status.ERROR));
				
				PluginResult pr = new PluginResult(PluginResult.Status.OK);
				pr.KeepCallback = true;
				DispatchCommandResult(pr);
            });		
        }
		//---------------------------
        private void _setUp(string adUnit, string adUnitFullScreen, boolean isOverlap, boolean isTest)
        {
			this.adUnit = adUnit;
			this.adUnitFullScreen = adUnitFullScreen;
			this.isOverlap = isOverlap;
			this.isTest = isTest;
        }		
        private void _preloadBannerAd()
        {
            if (bannerView != null)
            {
                PhoneApplicationFrame frame = Application.Current.RootVisual as PhoneApplicationFrame;
                if (frame != null)
                {
                    PhoneApplicationPage page = frame.Content as PhoneApplicationPage;
                    if (page != null)
                    {
                        Grid grid = page.FindName("LayoutRoot") as Grid;
                        if (grid != null)
                        {
                            grid.Children.Remove(bannerView);
                        }
                    }
                }
            }
			
            //if (bannerView == null)
            //{
				if(size == null) {
					size = "SMART_BANNER";
				}			
				//
                AdFormats format = AdFormats.Banner;
				//https://developers.google.com/mobile-ads-sdk/docs/admob/wp/banner		
				if (size.Equals("BANNER")) {
					format = AdFormats.Banner;//Banner (320x50, Phones and Tablets)
				}
				else if (size.Equals("SMART_BANNER")) {
					format = AdFormats.SmartBanner;//Smart banner (Auto size, Phones and Tablets) //https://developers.google.com/mobile-ads-sdk/docs/admob/android/banner#smart
				} 				
				else {
					format = AdFormats.SmartBanner;
				}
			
				//
                bannerView = new AdView
                {
                    //Format = AdFormats.Banner,
                    //Format = AdFormats.SmartBanner,
                    Format = format,
                    AdUnitID = this.adUnit
                };
                bannerView.ReceivedAd += OnBannerViewReceivedAd;
                bannerView.FailedToReceiveAd += OnBannerViewFailedToReceiveAd;
            //}
			
			_refreshBannerAd();
        }
        private void _refreshBannerAd()
        {
            if (bannerView != null)
			{
                AdRequest adRequest = new AdRequest();
				if(isTest) {
					adRequest.ForceTesting = true;
				}					
                bannerView.LoadAd(adRequest);
            }
        }		
        private void _showBannerAd(string position, string size)
        {
			this.position = position;
			this.size = size;
			
			if(bannerAdPreloaded) {
				bannerAdPreloaded = false;
			}
			else{
				_preloadBannerAd();
			}			
			
            PhoneApplicationFrame frame = Application.Current.RootVisual as PhoneApplicationFrame;
            if (frame != null)
            {
                PhoneApplicationPage page = frame.Content as PhoneApplicationPage;
                if (page != null)
                {
                    Grid grid = page.FindName("LayoutRoot") as Grid;
                    if (grid != null)
                    {
/*					
                        if (position == "top-center")
                        {
							bannerView.VerticalAlignment = VerticalAlignment.Top;//
                        }
                        else{
                            bannerView.VerticalAlignment = VerticalAlignment.Bottom;
                        }
*/						
						if (position.equals("top-left")) {		
						}
						else if (position.equals("top-center")) {		
                            bannerView.VerticalAlignment = VerticalAlignment.Top;
                            bannerView.HorizontalAlignment = VerticalAlignment.Center;
						}
						else if (position.equals("top-right")) {		
                            bannerView.VerticalAlignment = VerticalAlignment.Top;
                            bannerView.HorizontalAlignment = VerticalAlignment.Right;
						}
						else if (position.equals("left")) {
                            bannerView.VerticalAlignment = VerticalAlignment.Center;
                            bannerView.HorizontalAlignment = VerticalAlignment.Left;
						}
						else if (position.equals("center")) {
						    bannerView.VerticalAlignment = VerticalAlignment.Center;
                            bannerView.HorizontalAlignment = VerticalAlignment.Center;
						}
						else if (position.equals("right")) {		
						    bannerView.VerticalAlignment = VerticalAlignment.Center;
                            bannerView.HorizontalAlignment = VerticalAlignment.Right;
						}
						else if (position.equals("bottom-left")) {
						    bannerView.VerticalAlignment = VerticalAlignment.Bottom;
                            bannerView.HorizontalAlignment = VerticalAlignment.Left;						
						}
						else if (position.equals("bottom-center")) {
						    bannerView.VerticalAlignment = VerticalAlignment.Bottom;
                            bannerView.HorizontalAlignment = VerticalAlignment.Center;
						}
						else if (position.equals("bottom-right")) {		
						    bannerView.VerticalAlignment = VerticalAlignment.Bottom;
                            bannerView.HorizontalAlignment = VerticalAlignment.Right;
						}
						else {		
                            bannerView.VerticalAlignment = VerticalAlignment.Top;
                            bannerView.HorizontalAlignment = VerticalAlignment.Center;
						}
				
                        grid.Children.Add(bannerView);
                    }
                }
            }
        }
        private void _hideBannerAd()
        {
            if (bannerView != null)
            {
                PhoneApplicationFrame frame = Application.Current.RootVisual as PhoneApplicationFrame;
                if (frame != null)
                {
                    PhoneApplicationPage page = frame.Content as PhoneApplicationPage;
                    if (page != null)
                    {
                        Grid grid = page.FindName("LayoutRoot") as Grid;
                        if (grid != null)
                        {
                            grid.Children.Remove(bannerView);
                        }
                    }
                }
            }
        }
        private void _preloadFullScreenAd()
        {
            if (interstitialView == null)
            {
                //interstitialView = new InterstitialAd("ca-app-pub-4906074177432504/4879304879");//x cf) wp8
                //interstitialView = new InterstitialAd("ca-app-pub-4906074177432504/5150650074");//o cf) android
                interstitialView = new InterstitialAd(this.adUnitFullScreen);
				//http://forums.xamarin.com/discussion/849/binding-library-for-inneractive-sdk
                interstitialView.ReceivedAd += OnInterstitialViewReceivedAd;
                interstitialView.FailedToReceiveAd += OnInterstitialViewFailedToReceiveAd;
                interstitialView.ShowingOverlay += OnInterstitialViewShowingOverlay;
				interstitialView.DismissingOverlay += OnInterstitialViewDismissingOverlay;
            }
			
			_refreshFullScreenAd();
        }
        private void _refreshFullScreenAd()
        {
			if (interstitialView != null) {			
				AdRequest adRequest = new AdRequest();
				if(isTest) {
					adRequest.ForceTesting = true;
				}				
				interstitialView.LoadAd(adRequest);
			}
        }
        private void _showFullScreenAd()
        {
			if(fullScreenAdPreloaded) {
				if (interstitialView == null) {
					_preloadFullScreenAd();
				}
			
				interstitialView.ShowAd();
				
				fullScreenAdPreloaded = false;
			}
			else{
				_preloadFullScreenAd();
			}
        }
		//
        private void OnBannerViewReceivedAd(object sender, AdEventArgs e)
        {
            Debug.WriteLine("OnBannerViewReceivedAd");
        }

        private void OnBannerViewFailedToReceiveAd(object sender, AdErrorEventArgs errorCode)
        {
            Debug.WriteLine("OnBannerViewFailedToReceiveAd " + errorCode.ErrorCode);
        }
		//interstitialView.ReceivedAd
        private void OnInterstitialViewReceivedAd(object sender, AdEventArgs e)
        {
            Debug.WriteLine("OnInterstitialViewReceivedAd");

			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdLoaded");
			pr.KeepCallback = true;
			DispatchCommandResult(pr);	
				
			if(fullScreenAdPreloaded) {
			}
			else {
				interstitialView.ShowAd();
			}
        }
		//interstitialView.FailedToReceiveAd
        private void OnInterstitialViewFailedToReceiveAd(object sender, AdErrorEventArgs errorCode)
        {
            Debug.WriteLine("OnInterstitialViewFailedToReceiveAd " + errorCode.ErrorCode);
        }
        //interstitialView.ShowingOverlay
        private void OnInterstitialViewShowingOverlay(object sender, AdEventArgs e)
        {
            Debug.WriteLine("OnInterstitialViewPresentScreen");
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdShown");
			pr.KeepCallback = true;
			DispatchCommandResult(pr);			
        }
        //interstitialView.DismissingOverlay
        private void OnInterstitialViewDismissingOverlay(object sender, AdEventArgs e)
        {
            Debug.WriteLine("OnInterstitialViewDismissScreen");		
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdClosed");
			pr.KeepCallback = true;
			DispatchCommandResult(pr);
        }

	}
}