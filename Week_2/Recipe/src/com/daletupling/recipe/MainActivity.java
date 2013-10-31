package com.daletupling.recipe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	Context mContext;
	String[] recipeList;
	TextView recipeInfoText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
       //Set recipeList to resource
        recipeList = getResources().getStringArray(R.array.recipeArr);
        
        //Create Linear Layout
        LinearLayout linearL = new LinearLayout(mContext);
        //Create Linear Layout parameters
        LayoutParams layoutP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //Set layout Params
        linearL.setLayoutParams(layoutP);
        //Set Layout Orientation
        linearL.setOrientation(LinearLayout.VERTICAL);
        
        //Create ListView Adapter
        ArrayAdapter<String>listA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipeList);
        
        //Create ListView of Recipes
        ListView listV = new ListView(mContext);
        //Set Listview Adapter
        listV.setAdapter(listA);
        //Setup LayoutParams for ListView
        LayoutParams listLayoutP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //Set Layout Params for ListView
        listV.setLayoutParams(listLayoutP);
        
        //ListView onClickListener
        listV.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//Get user selection and place into RecipeText string
				String recipeText = recipeList[position].toString();
				//Formatted recipeInfoText set to JSON read results and String recipeText
				recipeInfoText.setText("Recipe: "+ recipeText +"\r\n\r\n"+ JSON.readJSON(recipeText));
				
			}
        	
        });//setOnItemClickListener closing bracket
        
        //Setup TextView
        recipeInfoText = new TextView(mContext);
        LayoutParams infoLayoutP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        recipeInfoText.setLayoutParams(infoLayoutP);
        
        
        //Add ListView to Layout
        linearL.addView(listV);
        //Add TextView to layout
        linearL.addView(recipeInfoText);
        //Set content view to programmed Linear Layout
        setContentView(linearL);
  
    }//onCreate closing bracket


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }//onCreateOptionsMenu closing bracket
    
}
