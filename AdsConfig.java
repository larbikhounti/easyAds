package com.legends.wheelsgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AdsConfig {
    private SharedPreferences pref;
    private  SharedPreferences.Editor editor;
    private AdRequest adRequest;
    private  Context context;
    private  InterstitialAd mInterstitialAd;
    private  AdView banner;
    private  com.facebook.ads.InterstitialAd interstitialAd;
    private com.facebook.ads.AdSize adsize;
    private  com.facebook.ads.AdView fbadview;

    public AdsConfig(Context c,AdView adView ) {
        this.pref =  c.getSharedPreferences("ads", Context.MODE_PRIVATE);;
        this.editor = pref.edit();
        this.context = c;
        this.banner = adView;
    }
    public AdsConfig(Context c) {
        this.pref =  c.getSharedPreferences("ads", Context.MODE_PRIVATE);;
        this.editor = pref.edit();
        this.context = c;

    }


    /*
==============================================================
==============================================================
                    =                   =
                    =   Admob ADS    =
                    =                   =
==============================================================
==============================================================
*   */
    // when the user enter for the first time
    public  void FirstTimeUsingTheApp(){
        if (pref.getInt("AdsClicks", 0) == 0) { // if clicks equals to 0 make it
            // 3 clicks
            editor.putInt("AdsClicks",2); //clicks
            // 7 second delay
            editor.putInt("AdsDelay", Integer.parseInt("6000")); // delay 7 second
            // not used
            // editor.putInt("AdsBanHourNow",0); // ban 1 hour
            // don't touch this xD
            editor.putInt("isItFistBan",1); // true

            editor.apply();
            Log.i("AdsConfig", "AdsClicks added");
            Log.i("AdsConfig", "AdsDelay added");
            Log.i("AdsConfig", "isItFistBan added");

        }

    }
    // ban the user
    public void BanTheUserFromSeeingAds(AdView banner){
        if (pref.getInt("isItFistBan",0) == 0){
            editor.putInt("isItFistBan",0); // false
            editor.apply(); // apply
        }
    }
    // hide  Admob Ads if The User Is banned
    public void HideAdmobAds(AdView banner){
        if (pref.getInt("isItFistBan",0) == 0){
            banner.setVisibility(View.INVISIBLE); // hide ads

        }
    }
    // show  banner after little of time (7000 millis)
    public void ShowAdmobBanner(){
        Log.i("adsconfig","admob - admob  banner stared");
        Log.i("adsconfig", String.valueOf(pref.getInt("isItFistBan",0) != 0));
        if (pref.getInt("isItFistBan",0) != 0){

            CountDownTimer countDownTimer = new CountDownTimer(7000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    //  Log.i("AdsClicks", String.valueOf(pref.getInt("AdsClicks", 0)));
                    // show ads after delay

                        banner.setVisibility(View.VISIBLE);
                        adRequest=new AdRequest.Builder().build();
                        banner.loadAd(adRequest);
                        ListenToBanner();

                }
            }.start();
        }
    }
    // admob banner Listener
    public void ListenToBanner(){
        Log.i("AdsConfig"," - Admob we are listing for user intractions");
        banner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("AdsConfig"," - Admob banner ad is loaded");


            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("AdsConfig"," - Admob banner Failed To Load ");
            }

            @Override
            public void onAdOpened() {
                Log.i("AdsConfig"," - Admob the banner is opened !");
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                int cliks =  ((pref.getInt("AdsClicks", 0) - 3 )); //clicks
                editor.putInt("AdsClicks",cliks); //clicks
                editor.apply();
                Log.i("AdsConfig", "  - Admob number of ads clicks "  + String.valueOf(cliks));

                // if the user clicks more than 3 times on ads then
                if(pref.getInt("AdsClicks", 0) <= -4 && pref.getInt("isItFistBan",0) == 1){


                    Log.i("AdsConfig", String.valueOf(pref.getInt("AdsBanHourNow",0) ) + "0 = not banned/ 1 = banned");
                    // hide ads becouse the user clicks 3 times on ads
                    banner.setVisibility(View.INVISIBLE);
                    // ban the user
                    editor.putInt("isItFistBan",0); // false
                    editor.apply();
                    Log.i("AdsConfig", "- system : user is banned from seeing admob ads");
                }



            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.i("AdsConfig"," - admob banner Ad is Clicked");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("AdsConfig","- admob user Ad Left the Application");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.i("AdsConfig","- admob ads closed");
            }
        });
    } // end of listener
    // build InterstitialAd
     public void ShowAdmobInterstitialAd(String unitid){
       mInterstitialAd = new InterstitialAd(context);
       mInterstitialAd.setAdUnitId(unitid);
       mInterstitialAd.loadAd(new AdRequest.Builder().build());
       AdmobInterstitialListener();
   }
    // Show Admob Interstitial Ad
    public void AdmobInterstitialListener(){
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.i("AdsConfig"," - Admob Interstitial is Loaded ");
                // Code to be executed when an ad finishes loading.
                if(pref.getInt("isItFistBan",0) != 0){
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.i("AdsConfig"," - Admob Interstitial is shown ");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.i("AdsConfig"," - Admob Inter Ad Clicked");
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                int cliks =  ((pref.getInt("AdsClicks", 0) - 3 )); //clicks
                editor.putInt("AdsClicks",cliks); //clicks
                editor.apply();
                Log.i("AdsConfig","Number Of Clicks After inter clicked" +  String.valueOf(cliks));

                // if the user clicks more than 3 times on ads then
                if(pref.getInt("AdsClicks", 0) <= -4 && pref.getInt("isItFistBan",0) == 1){

                    // hide ads becouse the user clicks 3 times on ads
                    banner.setVisibility(View.INVISIBLE);
                    // ban the user
                    editor.putInt("isItFistBan",0); // false
                    editor.apply();
                    Log.i("AdsConfig", "- system : user is banned  " +String.valueOf(pref.getInt("AdsBanHourNow",0) ));

                }
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });
    } // interListener
    /*
    ==============================================================
    ==============================================================
                        =                   =
                        =   FACEBOOK ADS    =
                        =                   =
    ==============================================================
    ==============================================================
    *   */

    public void ShowAndLoadFacebookInterstitial(){
        AudienceNetworkAds.initialize(context);
        interstitialAd = new com.facebook.ads.InterstitialAd(context, "325652518524302_326207668468787");
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.i("AdsConfig", "facebook : Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.i("AdsConfig", "facebook : Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.i("AdsConfig", "- facebook : Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.i("AdsConfig", "- facebook : Interstitial ad is loaded and ready to be displayed!");
                if(interstitialAd.isAdLoaded()){
                    interstitialAd.show();
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d("AdsConfig", "- facebook : Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.i("AdsConfig", "- facebook : Interstitial ad impression logged!");
            }
        });
        interstitialAd.loadAd();
    }

    public  void showFacebookBanner(LinearLayout adContainer,String fbUnit){
        AudienceNetworkAds.initialize(context);
        AdSettings.addTestDevice("817ac684-2462-4f33-ab6a-a813cd415a36");
        fbadview = new com.facebook.ads.AdView(context, fbUnit, adsize.BANNER_HEIGHT_50);
        // Find the Ad Container

        // Add the ad view to your activity layout
        adContainer.addView(fbadview);

        // Request an ad
        fbadview.loadAd();

    }









} // end of class
