/*
 * Project:	RecipeMe
 * 
 * Package: com.daletupling.recipeme
 * 
 * Author: 	Dale Tupling
 * 
 * Date:	November 8th, 2013
 * 
 */

package com.daletupling.libs;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;




public class WebData {
	//Network Boolean
	static Boolean networkConnection = false;
	static String networkType;
	static NetworkInfo netInfo;
	static String TAG = "NETWORK DATA - WEBDATA";
	//Check and get Network Type/ RETURNS networkType
	public static String getType(Context context) {
		
		//Call the checkNetworkStatus method and return networkType
		checkNetworkStatus(context);
		return networkType;
	}

	//Check and get Network Status/ RETURNS netStatus
	public static Boolean getStatus(Context context) {
		
		//Call the checkNetworkStatus method and return networkConnection
		checkNetworkStatus(context);

		return networkConnection;
	}

	//CheckNetworkStatus check and return status and type
	private static void checkNetworkStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		netInfo = cm.getActiveNetworkInfo();
		//Check in netInfo exits and is not null
		if (netInfo != null) {
			//Check if device is connected to a network
			if (netInfo.isConnected()) {
				//Get string for networkType
				networkType = netInfo.getTypeName();
				//Change networkConnection to true
				networkConnection = true;
			}
		}
	}
	
	//Get response from URL of API
    public static String getResponse(URL url) {
        String response = "";
        try {
                URLConnection urlConnection = url.openConnection();
                BufferedInputStream bin = new BufferedInputStream(
                                urlConnection.getInputStream());
                byte[] contextByte = new byte[2048];
                int byteRead = 0;
                StringBuffer responseBuffer = new StringBuffer();
                while ((byteRead = bin.read(contextByte)) != -1) {
                        response = new String(contextByte, 0, byteRead);
                        responseBuffer.append(response);
                }
                response = responseBuffer.toString();
                Log.i(TAG, response);
        } catch (IOException e) {
                response = "Something happened and we didn't get the input";
                Log.e(TAG, "Oops we didn't get that", e);
                e.printStackTrace();
        }
        return response;
}

}


