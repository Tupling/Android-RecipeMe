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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.daletupling.recipeme.MainActivity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public class APIData {

	static Context context;
	public static ArrayList<String> recipeList = new ArrayList<String>();
	static String TAG = "NETWORK DATA - WEBDATA";
	 //Static variable for if check on data
	 static JSONObject recipeInfo;
	// Get response from URL of API
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
		}// getResponse closing bracket

		// Get JSON Data and Filter through data to build arrays for Team name,
		// location and nickname
		public static class getData extends AsyncTask<String, Void, String> {
			
			@Override
			protected String doInBackground(String... params) {
				String responseString = "";
				try {

					URL url = new URL(MainActivity.finalURL);
					Log.i("URL", url.toString());
					responseString = getResponse(url);
				} catch (MalformedURLException e) {
					responseString = "UhOh we made a mistake";
					Log.e(TAG, "ERROR:", e);

				}
				return responseString;
			}

			@Override
			protected void onPostExecute(String result) {
				
				Log.i("TRYING JSON", "trying json");
				Log.i("RESPONSE", result);
				try {

					JSONObject jsonObject = new JSONObject(result);
					// Get Array sports from JSONObject
					JSONArray recipeArray = jsonObject.getJSONArray("matches");
	
					//Loop through recipeArray and get objects "title"
					for(int i=0; i< recipeArray.length(); i++){
						recipeInfo = recipeArray.getJSONObject(i);
						Log.i("Recipe Names: ", recipeInfo.getString("recipeName"));
						Log.i("Recipe Ingredients:", recipeInfo.getString("ingredients"));
						//add each recipe to recipeList Array
						recipeList.add(recipeInfo.getString("recipeName"));
						
						
					}//for loop closing bracket
					//check if recipelist is null. If not update listview
					if(recipeList.size() != 0){
						MainActivity.listA.notifyDataSetChanged();
					}else{
						//Alert for 0 recipes found from search query
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
								MainActivity.mContext);
						alertDialog.setMessage(
								"Your search returned 0 recipes. Please try another search.")
								.setCancelable(false)
								.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog, int id) {
												dialog.cancel();
											}// dialog onClick closing bracket
										});// setPositiveButton closing bracket
						AlertDialog alert = alertDialog.create();
						alert.show();
					}//else closing bracket

				} catch (JSONException e) {
					Log.e("JSONException", "ERROR", e);
					e.printStackTrace();
				}

			}
		}// getData closing bracket
}
