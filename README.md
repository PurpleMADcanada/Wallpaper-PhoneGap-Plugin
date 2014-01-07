Wallpaper-PhoneGap-Plugin
=========================

for Android, by [PurpleMAD](http://www.purplemad.ca/)

1. [Description](https://github.com/PurpleMADcanada/Wallpaper-PhoneGap-Plugin.git#1-description)
2. [Installation](https://github.com/PurpleMADcanada/Wallpaper-PhoneGap-Plugin.git#2-installation)
	2. [Automatically (CLI / Plugman)](https://github.com/PurpleMADcanada/Wallpaper-PhoneGap-Plugin.git#automatically-cli--plugman)
	2. [Manually](https://github.com/PurpleMADcanada/Wallpaper-PhoneGap-Plugin.git#manually)
	2. [PhoneGap Build](https://github.com/PurpleMADcanada/Wallpaper-PhoneGap-Plugin.git#phonegap-build)
3. [Usage](https://github.com/PurpleMADcanada/Wallpaper-PhoneGap-Plugin.git#3-usage)

## 1. Description

This plugin allows a user to save an image residing in the application folder or from a remote URL into the internal storage of the device. It also allows to set the image as a wallpaper which includes saving the image into the storage of the device.

* Works with PhoneGap >= 3.0.
* Compatible with [Cordova Plugman](https://github.com/apache/cordova-plugman).
* [Officially supported by PhoneGap Build](https://build.phonegap.com/plugins/386).

### Android specifics
* There are two functions as below:<br/>
a. wallpaper.setImage (for saving image and setting it as wallpaper)<br/>
b. wallpaper.saveImage (just for saving image) 

User need to pass parameters as (imagePath, imageTitle, folderName, success, error) where<br/> 
	1. imagePath = local/remote image path (in case of remote URL protocol can only be http/https)<br/>
	2. imageTitle = image title you wanna provide<br/>
	3. folderName = folder name you want to create on internal storage<br/>
	4. success (function to be called on success)<br/>
	5. error (function to be called on error)

* Tested on Android >= 4.

## 2. Installation

### Automatically (CLI / Plugman)
Wallpaper is compatible with [Cordova Plugman](https://github.com/apache/cordova-plugman) and ready for the [PhoneGap 3.0 CLI](http://docs.phonegap.com/en/3.0.0/guide_cli_index.md.html#The%20Command-line%20Interface_add_features), here's how it works with the CLI:

```
$ phonegap local plugin add https://github.com/PurpleMADcanada/Wallpaper-PhoneGap-Plugin.git
```
or
```
$ cordova plugin add https://github.com/PurpleMADcanada/Wallpaper-PhoneGap-Plugin.git
```
don't forget to run this command afterwards:
```
$ cordova build
```

### Manually

1\. Add the following xml to your `config.xml`:
```xml
<!-- for Android -->
<feature name="Wallpaper">
	<param name="android-package" value="ca.purplemad.wallpaper.Wallpaper"/>
</feature>
```

2\. Grab a copy of wallpaper.js, add it to your project and reference it in `index.html`:
```html
<script type="text/javascript" src="wallpaper.js"></script>
```

3\. Download the source files for Android and copy them to your project.

Android: Copy `Wallpaper.java` to `platforms/android/src/ca/purplemad/wallpaper` (create the folders)

4\. Set below permissions in AndroidManifest.xml file.

1. SET_WALLPAPER
2. WRITE_EXTERNAL_STORAGE
3. INTERNET
4. ACCESS_NETWORK_STATE

### PhoneGap Build

Using Wallpaper with PhoneGap Build requires these simple steps:

1\. Add the following xml to your `config.xml` to always use the latest version of this plugin:
```xml
<gap:plugin name="ca.purplemad.wallpaper" />
```
or to use this exact version:
```xml
<gap:plugin name="ca.purplemad.wallpaper" version="0.2.0" />
```

2\. Reference the JavaScript code in your `index.html`:
```html
<!-- below <script src="phonegap.js"></script> -->
<script src="js/plugins/Wallpaper.js"></script>
```


## 3. Usage

Basic operations, you'll want to copy-paste this for testing purposes:

```javascript
  // prep some variables
  var imagePath = "www/img/christmas.jpeg";				// Mention the complete path to your image. If it contains under multiple folder then mention the path from level "www" to the level your image contains with its name including its extension.
  var imageTitle = "christmas";						// Set title of your choice.
  var folderName = "PluginImages";					// Set folder Name of your choice. 
  var success = function() { alert("Success"); };			// Do something on success return.
  var error = function(message) { alert("Oopsie! " + message); };	// Do something on error return.

  // For setting wallpaper & saving image
  wallpaper.setImage(imagePath, imageTitle, folderName, success, error);
  
  // For saving image
  wallpaper.saveImage(imagePath, imageTitle, folderName, success, error);	
```