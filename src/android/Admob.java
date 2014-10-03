//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://www.github.com/cranberrygame
//License: MIT (http://opensource.org/licenses/MIT)
package com.cranberrygame.phonegap.plugin.ad;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import android.annotation.TargetApi;
import android.app.Activity;
import android.util.Log;
//
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.os.Build;
import android.provider.Settings;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.os.Handler;

//Util
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Surface;

class Util {

	//ex) Util.alert(cordova.getActivity(),"message");
	public static void alert(Activity activity, String message) {
		AlertDialog ad = new AlertDialog.Builder(activity).create();  
		ad.setCancelable(false); // This blocks the 'BACK' button  
		ad.setMessage(message);  
		ad.setButton("OK", new DialogInterface.OnClickListener() {  
			@Override  
			public void onClick(DialogInterface dialog, int which) {  
				dialog.dismiss();                      
			}  
		});  
		ad.show(); 		
	}
	
	//https://gitshell.com/lvxudong/A530_packages_app_Camera/blob/master/src/com/android/camera/Util.java
	public static int getDisplayRotation(Activity activity) {
	    int rotation = activity.getWindowManager().getDefaultDisplay()
	            .getRotation();
	    switch (rotation) {
	        case Surface.ROTATION_0: return 0;
	        case Surface.ROTATION_90: return 90;
	        case Surface.ROTATION_180: return 180;
	        case Surface.ROTATION_270: return 270;
	    }
	    return 0;
	}

	public static final String md5(final String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
        }
        return "";
    }
}

public class Admob extends CordovaPlugin {
	private static final String LOG_TAG = "Admob";
	//
	private String adUnit;
	private String adUnitFullScreen;
	private boolean isOverlap;
    private boolean isTest;
	//
	private AdView bannerView; 
	private RelativeLayout bannerViewLayout;
    private InterstitialAd interstitialView;
	public CallbackContext bannerViewCC;
	public CallbackContext interstitialViewCC;
	//
	public boolean bannerAdPreload;	
	public boolean fullScreenAdPreload;
    private String position;	
    private String size;
    private int lastOrientation;
	
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);

		lastOrientation = -1;
		addEvent();
    }
    //@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)	
	private void addEvent() {
		//http://stackoverflow.com/questions/24539578/cordova-plugin-listening-to-device-orientation-change-is-it-possible
		//http://developer.android.com/reference/android/view/View.OnLayoutChangeListener.html
		//https://gitshell.com/lvxudong/A530_packages_app_Camera/blob/master/src/com/android/camera/ActivityBase.java
    	webView.addOnLayoutChangeListener(new View.OnLayoutChangeListener(){
		    @Override
	        public void onLayoutChange(View v, int left, int top, int right, int bottom,
	                int oldLeft, int oldTop, int oldRight, int oldBottom) {
				if (left == oldLeft && top == oldTop && right == oldRight
						&& bottom == oldBottom) {
					return;
				}

				Log.d(LOG_TAG, "onLayoutChange");
				//Util.alert(cordova.getActivity(), "onLayoutChange");
				
				int orientation = Util.getDisplayRotation(cordova.getActivity());
				if(orientation != lastOrientation) {
					Log.d(LOG_TAG, String.format("orientation: %d", orientation));
					//Util.alert(cordova.getActivity(), String.format("orientation: %d", orientation));
					if (size != null && size.equals("SMART_BANNER")) {
						Log.d(LOG_TAG, String.format("position: %s, size: %s", position, size));
						//Util.alert(cordova.getActivity(), String.format("position: %s, size: %s", position, size));
						if (isOverlap)
							addEvent_overlap();
						else
							addEvent_split();
					}
				}
            
				lastOrientation = orientation;		
	        }		    
		});
    }
	private void addEvent_overlap() {
		//http://stackoverflow.com/questions/11281562/android-admob-resize-on-landscape
		if (bannerView != null) {							
			RelativeLayout bannerViewLayout = (RelativeLayout)bannerView.getParent();
			//if banner is showing
			if (bannerViewLayout != null) {
				//bannerViewLayout.removeView(bannerView);
				//bannerView.destroy();
				//bannerView = null;				
				Log.d(LOG_TAG, String.format("position: %s, size: %s", position, size));
				//Util.alert(cordova.getActivity(), String.format("position: %s, size: %s", position, size));

				//http://stackoverflow.com/questions/3072173/how-to-call-a-method-after-a-delay-in-android
				final Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						_showBannerAd(position, size);
					}
				}, 1);//after 1ms
			}
		}	
	}
	private void addEvent_split() {
		if (bannerView != null) {							
			ViewGroup parentView = (ViewGroup)bannerView.getParent();
			//if banner is showing
			if (parentView != null) {
				//parentView.removeView(bannerView);
				//bannerView.destroy();
				//bannerView = null;
				Log.d(LOG_TAG, String.format("position: %s, size: %s", position, size));
				//Util.alert(cordova.getActivity(), String.format("position: %s, size: %s", position, size));

				//http://stackoverflow.com/questions/3072173/how-to-call-a-method-after-a-delay-in-android
				final Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						_showBannerAd(position, size);
					}
				}, 1);//after 1ms				
			}
		}
	}
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		PluginResult result = null;
		
		//args.length()
		//args.getString(0)
		//args.getString(1)
		//args.getInt(0)
		//args.getInt(1)
		//args.getBoolean(0)
		//args.getBoolean(1)
		//JSONObject json = args.optJSONObject(0);
		//json.optString("adUnit")
		//json.optString("adUnitFullScreen")
		//JSONObject inJson = json.optJSONObject("inJson");

		if (action.equals("setUp")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			final String adUnit = args.getString(0);				
			Log.d(LOG_TAG, adUnit);
			final String adUnitFullScreen = args.getString(1);				
			Log.d(LOG_TAG, adUnitFullScreen);
			final boolean isOverlap = args.getBoolean(2);				
			Log.d(LOG_TAG, isOverlap?"true":"false");
			final boolean isTest = args.getBoolean(3);				
			Log.d(LOG_TAG, isTest?"true":"false");
			
			final CallbackContext delayedCC = callbackContext;		
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {						
					_setUp(adUnit, adUnitFullScreen, isOverlap, isTest);

					PluginResult pr = new PluginResult(PluginResult.Status.OK);
					//pr.setKeepCallback(true);
					delayedCC.sendPluginResult(pr);
					//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
					//pr.setKeepCallback(true);
					//delayedCC.sendPluginResult(pr);					
				}
			});

			return true;
		}			
		else if (action.equals("preloadBannerAd")) {
			//Activity activity=cordova.getActivity();
			//webView

			bannerAdPreload = true;
			
			final CallbackContext delayedCC = callbackContext;		
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {						
					_preloadBannerAd();				
				}
			});

			bannerViewCC = callbackContext;
			
			return true;
		}
		else if (action.equals("reloadBannerAd")) {
			//Activity activity=cordova.getActivity();
			//webView
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					_reloadBannerAd();
				}
			});
			
			bannerViewCC = callbackContext;
			
			return true;
		}			
		else if (action.equals("showBannerAd")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			final String position = args.getString(0);
			Log.d(LOG_TAG, position);
			final String size = args.getString(1);
			Log.d(LOG_TAG, size);
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {						
					_showBannerAd(position, size);
				}
			});				

			bannerViewCC = callbackContext;
			
			return true;
		}
		else if (action.equals("hideBannerAd")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {		            			            	
					_hideBannerAd();
				}
			});
			
			bannerViewCC = callbackContext;
			
			return true;
		}
		else if (action.equals("preloadFullScreenAd")) {
			//Activity activity=cordova.getActivity();
			//webView
			//

			fullScreenAdPreload = true;

			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					_preloadFullScreenAd();
				}
			});

			interstitialViewCC = callbackContext;
			
			return true;
		}
		else if (action.equals("reloadFullScreenAd")) {
			//Activity activity=cordova.getActivity();
			//webView
			//

			fullScreenAdPreload = true;			
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					_reloadFullScreenAd();
				}
			});

			interstitialViewCC = callbackContext;
			
			return true;
		}			
		else if (action.equals("showFullScreenAd")) {
			//Activity activity=cordova.getActivity();
			//webView
			//
			
			final CallbackContext delayedCC = callbackContext;
			cordova.getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					_showFullScreenAd();
				}
			});

			interstitialViewCC = callbackContext;
			
			return true;
		}
		
		return false; // Returning false results in a "MethodNotFound" error.
	}
	//-------------------------------------
	private void _setUp(String adUnit, String adUnitFullScreen, boolean isOverlap, boolean isTest){
		this.adUnit = adUnit;
		this.adUnitFullScreen = adUnitFullScreen;
		this.isOverlap = isOverlap;
		this.isTest = isTest;
	}
	private void _preloadBannerAd(){

		if (isOverlap)
			_preloadBannerAd_overlap();
		else
			_preloadBannerAd_split();
			
		if (bannerView == null) {
			//
			bannerView = new AdView(cordova.getActivity());//			
			bannerView.setAdUnitId(this.adUnit);
			bannerView.setAdListener(new MyBannerViewListener(bannerView, this));
			
			//https://developers.google.com/mobile-ads-sdk/docs/admob/android/banner
			if(size == null) {
				size = "SMART_BANNER";
			}
			if (size.equals("BANNER")) {
				bannerView.setAdSize(AdSize.BANNER);//Banner (320x50, Phones and Tablets)
			} 
			else if (size.equals("LARGE_BANNER")) {
				bannerView.setAdSize(AdSize.LARGE_BANNER);//Large banner (320x100, Phones and Tablets)
			}
			else if (size.equals("MEDIUM_RECTANGLE")) {
				bannerView.setAdSize(AdSize.MEDIUM_RECTANGLE);//Medium rectangle (300x250, Phones and Tablets)
			}
			else if (size.equals("FULL_BANNER")) {
				bannerView.setAdSize(AdSize.FULL_BANNER);//Full banner (468x60, Tablets)
			}
			else if (size.equals("LEADERBOARD")) {
				bannerView.setAdSize(AdSize.LEADERBOARD);//Leaderboard (728x90, Tablets)
			}
			else if (size.equals("SMART_BANNER")) {
				bannerView.setAdSize(AdSize.SMART_BANNER);//Smart banner (Auto size, Phones and Tablets) //https://developers.google.com/mobile-ads-sdk/docs/admob/android/banner#smart
			}
			else {
				bannerView.setAdSize(AdSize.SMART_BANNER);
			}		
		}

		_reloadBannerAd();
	}
	private void _preloadBannerAd_overlap(){
		if(bannerViewLayout == null) {
			bannerViewLayout = new RelativeLayout(cordova.getActivity());//	
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			//webView.addView(bannerViewLayout, params);
			bannerViewLayout.setLayoutParams(params);
			webView.addView(bannerViewLayout);
		}
		
		if (bannerView != null) {							
			RelativeLayout bannerViewLayout = (RelativeLayout)bannerView.getParent();
			//if banner is showing
			if (bannerViewLayout != null) {
				bannerViewLayout.removeView(bannerView);
				bannerView.destroy();
				bannerView = null;
			}
		}
	}
	private void _preloadBannerAd_split(){
		if (bannerView != null) {							
			ViewGroup parentView = (ViewGroup)bannerView.getParent();
			//if banner is showing
			if (parentView != null) {
				parentView.removeView(bannerView);
				bannerView.destroy();
				bannerView = null;
			}
		}	
	}
	private void _reloadBannerAd(){
		if (bannerView != null) {
			//https://developer.android.com/reference/com/google/android/gms/ads/AdRequest.Builder.html
			AdRequest.Builder builder = new AdRequest.Builder();
			if(isTest) {
				builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR); 
				//builder.addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE");
				//Java code to force all devices to show test ads
				//http://stackoverflow.com/questions/9028852/java-code-to-force-all-devices-to-show-test-ads
 				String ANDROID_ID = Settings.Secure.getString(cordova.getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
				String deviceId = Util.md5(ANDROID_ID).toUpperCase();
				builder.addTestDevice(deviceId);		
			}
			AdRequest request = builder.build();
			bannerView.loadAd(request);	            	
		}
	}
	private void _showBannerAd(String position, String size){
		this.position = position;	
		this.size = size;

		if(bannerAdPreload) {
			bannerAdPreload = false;
		}
		else{
			_preloadBannerAd();
		}

		if (isOverlap)
			_showBannerAd_overlap(position,size);
		else
			_showBannerAd_split(position,size);
	}
	private void _showBannerAd_overlap(String position, String size){
		//http://tigerwoods.tistory.com/11
		//http://developer.android.com/reference/android/widget/RelativeLayout.html
		//http://stackoverflow.com/questions/24900725/admob-banner-poitioning-in-android-on-bottom-of-the-screen-using-no-xml-relative
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(AdView.LayoutParams.WRAP_CONTENT, AdView.LayoutParams.WRAP_CONTENT);
		if (position.equals("top-left")) {
			Log.d(LOG_TAG, "top-left");		
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
	}	
	private void _showBannerAd_split(String position, String size){
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
	}	
	private void _hideBannerAd(){
		if (isOverlap)
			_hideBannerAd_overlap();
		else
			_hideBannerAd_split();
	}
	private void _hideBannerAd_overlap(){
		if (bannerView != null) {							
			RelativeLayout bannerViewLayout = (RelativeLayout)bannerView.getParent();
			//if banner is showing
			if (bannerViewLayout != null) {
				bannerViewLayout.removeView(bannerView);
				bannerView.destroy();
				bannerView = null;				
			}
		}		
	}	
	private void _hideBannerAd_split(){
		if (bannerView != null) {							
			ViewGroup parentView = (ViewGroup)bannerView.getParent();
			//if banner is showing
			if (parentView != null) {
				parentView.removeView(bannerView);
				bannerView.destroy();
				bannerView = null;
			}
		}	
	}	
	//
	private void _preloadFullScreenAd(){
		if (interstitialView == null) {
			interstitialView = new InterstitialAd(cordova.getActivity());
			interstitialView.setAdUnitId(this.adUnitFullScreen);
			interstitialView.setAdListener(new MyInterstitialViewListener(interstitialView, this));					
		}		
		
		_reloadFullScreenAd();		
	}
	private void _reloadFullScreenAd(){
		if (interstitialView != null) {	
			AdRequest.Builder builder = new AdRequest.Builder();
			if(isTest) {
				builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR); 
				//builder.addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE");				
 				String ANDROID_ID = Settings.Secure.getString(cordova.getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
				String deviceId = Util.md5(ANDROID_ID).toUpperCase();
				builder.addTestDevice(deviceId);		
			}
			AdRequest request = builder.build();			
			interstitialView.loadAd(request);	
		}
	}	
	private void _showFullScreenAd(){
		if(fullScreenAdPreload) {
			interstitialView.show();
			
			fullScreenAdPreload = false;
		}
		else{
			_preloadFullScreenAd();
		}		
	}
	//
    @Override
    public void onPause(boolean multitasking) {
        if (bannerView != null) {
            bannerView.pause();
        }
        super.onPause(multitasking);
    }    
    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        if (bannerView != null) {
            bannerView.resume();
        }
    }    
    @Override
    public void onDestroy() {
        if (bannerView != null) {
            bannerView.destroy();
        }
        super.onDestroy();
    }
}

//http://developer.android.com/reference/com/google/android/gms/ads/AdListener.html
class MyBannerViewListener extends AdListener {
	private static final String LOG_TAG = "MyBannerViewListener";
	//
	AdView bannerView;
	//
	Admob admob;
	
	MyBannerViewListener (AdView bannerView, Admob admob){
		this.bannerView = bannerView;
		this.admob = admob;			
	}	
	public void onAdLoaded() {
		Log.d(LOG_TAG, "onAdLoaded");

		if (admob.bannerAdPreload) {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onBannerAdPreloaded");
			pr.setKeepCallback(true);
			admob.bannerViewCC.sendPluginResult(pr);
			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
			//pr.setKeepCallback(true);
			//admob.bannerViewCC.sendPluginResult(pr);
		}
		
		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onBannerAdLoaded");
		//pr.setKeepCallback(true);
		admob.bannerViewCC.sendPluginResult(pr);
		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
		//pr.setKeepCallback(true);
		//admob.bannerViewCC.sendPluginResult(pr);
	}
	public void onAdFailedToLoad(int errorCode) {
		Log.d(LOG_TAG, "onAdFailedToLoad");
	}
	public void onAdOpened() {
		Log.d(LOG_TAG, "onAdOpened");//overlay screen opened
	}
	public void onAdClosed() {
		Log.d(LOG_TAG, "onAdClosed");
	}
	public void onAdLeftApplication() {
		Log.d(LOG_TAG, "onAdLeftApplication");
	}
}

class MyInterstitialViewListener extends AdListener {
	private static final String LOG_TAG = "MyInterstitialViewListener";
	//
	InterstitialAd interstitialView;
	//
	Admob admob;
	
	MyInterstitialViewListener (InterstitialAd interstitialView, Admob admob){
		this.interstitialView = interstitialView;
		this.admob = admob;		
	}	
	public void onAdLoaded() {
		Log.d(LOG_TAG, "onAdLoaded");
		
		if(admob.fullScreenAdPreload) {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdPreloaded");
			pr.setKeepCallback(true);
			admob.interstitialViewCC.sendPluginResult(pr);
			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
			//pr.setKeepCallback(true);
			//admob.interstitialViewCC.sendPluginResult(pr);
		}
		
		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdLoaded");
		pr.setKeepCallback(true);
		admob.interstitialViewCC.sendPluginResult(pr);
		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
		//pr.setKeepCallback(true);
		//admob.interstitialViewCC.sendPluginResult(pr);		
		
		if(!admob.fullScreenAdPreload) {
			interstitialView.show();
		}	
	}
	public void onAdFailedToLoad(int errorCode) {
		Log.d(LOG_TAG, "onAdFailedToLoad");
	}
	public void onAdLeftApplication() {
		Log.d(LOG_TAG, "onAdLeftApplication");
	}
	public void onAdOpened() {
		Log.d(LOG_TAG, "onAdOpened");
		
		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdShown");
		pr.setKeepCallback(true);
		admob.interstitialViewCC.sendPluginResult(pr);
		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
		//pr.setKeepCallback(true);
		//admob.interstitialViewCC.sendPluginResult(pr);		
	}
	public void onAdClosed() {
		Log.d(LOG_TAG, "onAdClosed");

		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdHidden");
		//pr.setKeepCallback(true);
		admob.interstitialViewCC.sendPluginResult(pr);
		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
		//pr.setKeepCallback(true);
		//admob.interstitialViewCC.sendPluginResult(pr);		
		
	}
}
