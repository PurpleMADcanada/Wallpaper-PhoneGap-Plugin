var wallpaper =  {
    setImage: function(imagePath, imageTitle, folderName, successCallback, errorCallback) {
        cordova.exec(
            successCallback, 	// success callback function
            errorCallback, 		// error callback function
            'Wallpaper', 		// mapped to our native Java class called "Wallpaper"
            'setwallpaper', 	// with this action name
            [{                  // and this array of custom arguments to create our entry
                "imagePath": imagePath,
                "imageTitle": imageTitle,
                "folderName": folderName
            }]
        );
            
    },
    saveImage: function(imagePath, imageTitle, folderName, successCallback, errorCallback) {
        cordova.exec(
            successCallback, 	// success callback function
            errorCallback, 		// error callback function
            'Wallpaper', 		// mapped to our native Java class called "Wallpaper"
            'savewallpaper', 	// with this action name
            [{                  // and this array of custom arguments to create our entry
                "imagePath": imagePath,
                "imageTitle": imageTitle,
                "folderName": folderName
            }]
        );
            
    }
};
module.exports = wallpaper;