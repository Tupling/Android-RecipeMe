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


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daletupling.libs.APIData;
import com.daletupling.libs.APIData.getData;
import com.daletupling.libs.WebData;

public class MainActivity extends Activity {
	public static Context mContext;
	String[] recipeList;
	TextView netConnectionText;
	EditText search;
	Button button;
	Boolean connection = false;
	ListView listV;
	LinearLayout linearL;

	String tempSearchString;
	public static String finalSearch;
	static String TAG = "NETWORK DATA - MAINACTIVITY";

	public static String initialURL = "http://api.yummly.com/v1/api/recipes?_app_id=ab039d47&_app_key=a1af8755ddf6db91e8549e052a9c1bcd&q=";
	public static String finalURL;
	
	public static ArrayAdapter<String>listA;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// Create Linear Layout
		linearL = new LinearLayout(mContext);
		// Create Linear Layout parameters
		LayoutParams layoutP = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// Set layout Params
		linearL.setLayoutParams(layoutP);
		// Set Layout Orientation
		linearL.setOrientation(LinearLayout.VERTICAL);

		// EditText Instantiation and Params
		search = new EditText(mContext);
		LayoutParams searchParam = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		search.setHint(R.string.searchHint);
		search.setLayoutParams(searchParam);
		//Create ListView Adapter
        
		listA = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, APIData.recipeList);
        //Create ListView of Recipes
        listV = new ListView(mContext);
        //Set Listview Adapter
        listV.setAdapter(listA);
        //Setup LayoutParams for ListView
        LayoutParams listLayoutP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //Set Layout Params for ListView
        listV.setLayoutParams(listLayoutP);
        
        
		// Button Instantiation and Params
		button = new Button(mContext);
		LayoutParams buttonP = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		button.setLayoutParams(buttonP);
		button.setText(R.string.buttonText);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				listA.clear();
				// Set search field text to temp string
				tempSearchString = search.getText().toString();
				String searchCheck = tempSearchString;
				//regex for search string. Only accepts letters and spaces
				Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");
				//Match pattern to search string if acceptable search string continue
				Matcher match = pattern.matcher(searchCheck);
				if (!match.matches()) {

					// Display alert if search string contains any other character other than pattern regex

					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							mContext);
					alertDialog.setMessage(
							"Your search contains invalid characters please only use letters.")
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
				} else {
					//Get temp string and replace all spaces with %20 for API
					finalSearch = tempSearchString.replaceAll(" ", "%20");
					//Call getData method in APIData.java file
					getData data = new getData();
					//Create final URL String from initialURL and Search String
					finalURL = (initialURL+finalSearch);
					
					//Execute Data with finalURL Param
					data.execute(finalURL);
					
					Log.i("SEARCH QUERY", finalSearch);
					InputMethodManager imm = (InputMethodManager)getSystemService(
						      Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(linearL.getWindowToken(), 0);
	
				}// else closing bracket
				
			}// button onClick closing bracket
		});// onClickListener closing bracket
		
        
		// NetConnectionText Instantiation and Params
		netConnectionText = new TextView(mContext);
		
		LayoutParams netTextP = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		netConnectionText.setLayoutParams(netTextP);

		// Check Network Status
		connection = WebData.getStatus(mContext);
		if (connection) {
			Log.i("NETWORK", WebData.getType(mContext));
			// Set netConnection TextView text
			netConnectionText.setText("You are connected to:"
					+ WebData.getType(mContext).toString());

		} else {
			// Display dialog box for no connection
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"No Internet Connection Detected. Check your connection and try again.")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
			//set netConnection text if no network found
			netConnectionText.setText(R.string.noConnection);

		}// network connection if statement closing bracket

		// Add text View
		linearL.addView(netConnectionText);
		// Add EditText to View
		linearL.addView(search);
		// Add Button to View
		linearL.addView(button);
		//Add Listview to View
		linearL.addView(listV);
		// Set content view to programmatic Linear Layout
		setContentView(linearL);

	}// onCreate Closing bracket


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}// onCreateOptionsMenu closing bracket

}// MainActivity closing bracket
