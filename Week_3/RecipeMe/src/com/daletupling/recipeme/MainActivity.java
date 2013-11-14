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

package com.daletupling.recipeme;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import com.daletupling.libs.WebData;

public class MainActivity extends Activity {
	Context mContext;
	String[] recipeList;
	TextView netConnectionText;
	EditText search;
	Button button;
	Boolean connection = false;


	String tempSearchString;
	String finalSearch;
	static String TAG = "NETWORK DATA - MAINACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;

		//Create Linear Layout
		LinearLayout linearL = new LinearLayout(mContext);
		//Create Linear Layout parameters
		LayoutParams layoutP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//Set layout Params
		linearL.setLayoutParams(layoutP);
		//Set Layout Orientation
		linearL.setOrientation(LinearLayout.VERTICAL);

		//EditText Instantiation and Params
		search = new EditText(mContext);
		LayoutParams searchParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		search.setLayoutParams(searchParam);

		//Button Instantiation and Params
		button = new Button(mContext);
		LayoutParams buttonP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		button.setLayoutParams(buttonP);
		button.setText("Search");
		
		
		button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("CLICK HANDLER", search.getText().toString());
                
                //Set search field text to temp string
                tempSearchString = search.getText().toString();
                //Tack temp string and replace all spaces with %20 for API URL
                finalSearch = tempSearchString.replaceAll(" ", "%20");
                
                Log.i("CLICK HANDLER", finalSearch);
                
                /*if (search.getField().getText().toString().length() != 5) {
                    //Create popup invalid zip
                    Toast toast = Toast.makeText(_context,"Invalid Zip", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    //Call getWeather method
                    getWeather(_search.getField().getText().toString());
                }*/
            }
        });
		
		
		//NetConnectionText Instantiation and Params
		netConnectionText = new TextView(mContext);
		LayoutParams netTextP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		netConnectionText.setLayoutParams(netTextP);
		
		//Check Network Status
		connection = WebData.getStatus(mContext);
		if(connection){
			Log.i("NETWORK", WebData.getType(mContext));
			//Set netConnection TextView text
			netConnectionText.setText("You are connected to:"+ WebData.getType(mContext).toString());

	}else{
		//Display dialog box for no connection

       AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No Internet Connection Detected. Check your connection and try again.")
               .setCancelable(false)
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                   }
               });
        AlertDialog alert = builder.create();
        alert.show();
		netConnectionText.setText("You are not connected to any network");
		
	}//network connection if statement closing bracket

		//Add text View
		linearL.addView(netConnectionText);
		//Add EditText to View
		linearL.addView(search);
		//Add Button to View
		linearL.addView(button);
		//Set content view to programmatic Linear Layout
		setContentView(linearL);
		
	}//onCreate Closing bracket

	//Get JSON Data and Filter through data to build arrays for Team name, location and nickname
    public class getData extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                    String responseString = "";
                    try {
                    	
                    		String initalURL = "http://food2fork.com/api/search?key=d93b07b1067a7a5a8add2ee2ab7005bd&q=";
                            URL url = new URL(initalURL+finalSearch);
                            responseString = WebData.getResponse(url);
                    } catch (MalformedURLException e) {
                            responseString = "UhOh we made a mistake";
                            Log.e(TAG, "ERROR:", e);

                    }
                    return responseString;
            }

            @Override
            protected void onPostExecute(String result) {
                    Log.i("TRYING JSON", "trying json");
                    try {

                            JSONObject jsonObject = new JSONObject(result);
                            // Get Array sports from JSONObject
                            JSONArray recipeArray = jsonObject.getJSONArray("recipe");
                            Log.i("THE RESULTS", recipeArray.toString());
                                    
                    } catch (JSONException e) {
                            Log.e("JSONException", "ERROR", e);
                            e.printStackTrace();
                    }
    
            }
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}//onCreateOptionsMenu closing bracket


}//MainActivity closing bracket
