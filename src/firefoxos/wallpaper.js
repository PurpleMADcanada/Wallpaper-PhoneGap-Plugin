// Author: Gearóid Moroney <gearoid@syncostyle.com>

var firefoxos = require('cordova/platform');
var cordova = require('cordova');

module.exports = {
	// Heavily based on http://stackoverflow.com/questions/23021724/how-can-i-change-the-wallpaper-on-a-firefox-os-device
	setwallpaper: function(success, fail, args) {
		var path = args[0].imagePath,
			imageForWallpaper = new Image();

		// For in-app files, the Android version of the plugin requires www/ to be prepended, but FxOS does not, so we just take it out
		imageForWallpaper.src = path.indexOf('www/') === 0 ? path.replace('www/', '') : path;
		imageForWallpaper.crossOrigin = 'anonymous';

		imageForWallpaper.onload = function() {
			var canvas = document.createElement("canvas");
			canvas.width = imageForWallpaper.width;
			canvas.height = imageForWallpaper.height;

			var canvasContext = canvas.getContext("2d");
			canvasContext.drawImage(imageForWallpaper, 0, 0);

			// Export to blob and share through a Web Activitiy
			canvas.toBlob(function (blob) {
				var activity = new MozActivity({
					name: "share",
					data: {
						type: "image/*",
						number: 1,
						blobs: [blob]
					}
				});
				activity.onsuccess = function() {
					if (typeof success === 'function') { success(); }
				};
				activity.onerror = function() {
					if(typeof fail === 'function') { fail(); }
				};
			});
		};

		imageForWallpaper.onerror = function() {
			if(typeof fail === 'function') { fail(); }
		};
	},
	savewallpaper: function(success, fail, args) {
		console.log('This feature is not supported on FxOS');
		if(typeof fail === 'function') { fail(); }
	}
};

require("cordova/exec/proxy").add("Wallpaper", module.exports);