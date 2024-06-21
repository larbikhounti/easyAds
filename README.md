# EasyAds

## Overview

EasyAds is a simple library that enables you to easily display mobile ads from Facebook or AdMob, or both, in your Android applications. With EasyAds, you can integrate banner ads and interstitial ads from both ad networks with minimal effort.

## Features

- **Facebook Ads**: Display Facebook banner ads and interstitial ads.
- **AdMob Ads**: Display AdMob banner ads and interstitial ads.
- **Flexible Integration**: Use either Facebook ads, AdMob ads, or both according to your preference.

## Getting Started

### Facebook Ads Integration

1. Initialize `AdsConfig` and create a container for the banner ad:
    ```java
    AdsConfig adsConfig;
    LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
    ```

2. Set up the `AdsConfig` and show Facebook banner and interstitial ads:
    ```java
    adsConfig = new AdsConfig(getApplicationContext());
    
    // Show Facebook banner ad
    adsConfig.showFacebookBanner(adContainer, "325652518524302_326131715143049");
    
    // Show Facebook interstitial ad
    adsConfig.ShowAndLoadFacebookInterstitial();
    ```

### AdMob Ads Integration

1. Initialize `AdsConfig`:
    ```java
    AdsConfig adsConfig;
    ```

2. Set up the `AdsConfig` and show AdMob banner and interstitial ads:
    ```java
    adsConfig = new AdsConfig(getApplicationContext(), adview);
    
    // Show AdMob banner ad
    adsConfig.ShowAdmobBanner();
    
    // Show AdMob interstitial ad
    adsConfig.ShowAdmobInterstitialAd("unit-id");
    ```

## Usage Example

### Facebook Ads Example

```java
AdsConfig adsConfig;
LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

adsConfig = new AdsConfig(getApplicationContext());

// Show Facebook banner ad
adsConfig.showFacebookBanner(adContainer, "325652518524302_326131715143049");

// Show Facebook interstitial ad
adsConfig.ShowAndLoadFacebookInterstitial();
