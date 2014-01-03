package ca.purplemad.wallpaper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;


public class Wallpaper extends CordovaPlugin 
{
	public static final String SET_WALLPAPER = "setwallpaper";
	public static final String SAVE_WALLPAPER = "savewallpaper";
	URL url;
	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
        	JSONObject arg_object = args.getJSONObject(0);
        	String path = arg_object.getString("imagePath");
            String imageTitle = arg_object.getString("imageTitle");
            String folderName = arg_object.getString("folderName");
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.cordova.getActivity().getApplicationContext());
            File direct = new File(Environment.getExternalStorageDirectory()+"/"+folderName);
            if(!direct.exists()) {
            	direct.mkdir();
            }
            if(path.contains("https://") || path.contains("http://")){
            	ConnectivityManager connec = (ConnectivityManager) this.cordova.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = connec.getActiveNetworkInfo();
                if (ni == null) {
                	callbackContext.error("Internet not Available.");
    	            return false;
                }
                else{
                	if (SET_WALLPAPER.equals(action)) {
	                	
                		if(saveRemoteImage(direct,path,imageTitle)){
                			Bitmap setAsWallpaper = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + folderName + "/" + imageTitle + ".jpeg");
    	                    wallpaperManager.setBitmap(setAsWallpaper);
    	                    callbackContext.success();
    	    	            return true;
                		}
                	}
                	else if(SAVE_WALLPAPER.equals(action)){
                		if(saveRemoteImage(direct,path,imageTitle)){
    	                    callbackContext.success();
    	    	            return true;
                		}
                	}
                }
            }
            else{
            	if (SET_WALLPAPER.equals(action)) {
	            	InputStream ins = this.cordova.getActivity().getApplicationContext().getAssets().open(path);	            	
	            	wallpaperManager.setStream(ins);
	            	if(saveLocalImage(direct,ins,imageTitle)){
	            		callbackContext.success();
			            return true;
	            	}    
            	}
            	else if(SAVE_WALLPAPER.equals(action)){   	
                    InputStream ins = this.cordova.getActivity().getApplicationContext().getAssets().open(path);
                    if(saveLocalImage(direct,ins,imageTitle)){
                    	callbackContext.success();
                        return true;
                    }
            	}
            } 
		callbackContext.error("Invalid action");
		return false;    
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        } 
    }
	
	public void refresh_gallery(File file){
		MediaScannerConnection.scanFile(								
    		this.cordova.getActivity().getApplicationContext(), 
    	    new String[]{file.getAbsolutePath()}, 
    	    null, 
    	    new OnScanCompletedListener() {
    	       @Override
    	       public void onScanCompleted(String filepath, Uri uri) {
    	          Log.v("Gallery", "file" + filepath + " was scanned successfully: " + uri);
    	       }
    	});
	}
	public boolean saveLocalImage(File direct, InputStream ins, String imageTitle){
		try{
			OutputStream fos = null;
	        File file = new File(direct, imageTitle + ".jpeg");
	        Bitmap bm =BitmapFactory.decodeStream(ins);
	        fos = new FileOutputStream(file);
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
	        refresh_gallery(file);               
	        bos.flush();
	        bos.close();
	        return true;
		}catch(Exception e){
			System.err.println("Exception: " + e.getMessage());
			return false;
		}
	}
	public boolean saveRemoteImage(File direct,String path,String imageTitle){
		try{
		URL url = new URL(path); 
		String fname = imageTitle + ".jpeg";
		File file = new File (direct, fname);
		if (file.exists ()) file.delete (); 
		
		URLConnection ucon = url.openConnection();
		InputStream inputStream = null;
		HttpURLConnection httpConn = (HttpURLConnection)ucon;
		httpConn.setRequestMethod("GET");
		httpConn.connect();

		if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			inputStream = httpConn.getInputStream();
		}

        FileOutputStream fos = new FileOutputStream(file);
        int size = 1024*1024;
        int bytesDownloaded = 0;
        byte[] buf = new byte[size];
        int byteRead;
        while (((byteRead = inputStream.read(buf)) != -1)) {
            fos.write(buf, 0, byteRead);
            bytesDownloaded += byteRead;
        }
        fos.close();
        refresh_gallery(file);
        return true;
		}catch(Exception e){
			System.err.println("Exception: " + e.getMessage());
			return false;
		}
	}
}

