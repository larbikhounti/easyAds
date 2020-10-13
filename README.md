# easyAds
show facebook or admob mobile ads  or both  

facebook
********

AdsConfig adsConfig; 

LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

adsConfig = new AdsConfig(getApplicationContext());

adsConfig.showFacebookBanner(adContainer,"325652518524302_326131715143049");

adsConfig.ShowAndLoadFacebookInterstitial();

****************
admob
*****
AdsConfig adsConfig;

adsConfig = new AdsConfig(getApplicationContext(),adview);

adsConfig.ShowAdmobBanner();

adsConfig.ShowAdmobInterstitialAd("unit-id");
************************************************

