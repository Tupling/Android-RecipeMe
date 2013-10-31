package com.daletupling.recipe;

import org.json.JSONException;
import org.json.JSONObject;

public class JSON {

	public static JSONObject buildJSON(){
		//Create Recipe Objects
		JSONObject recipeObjects = new JSONObject();

		//Try/Catch statement try JSON or fail with JSONException
		try{
			//Query JSON
			JSONObject queryJSON = new JSONObject();

			//Loop through Recipe ENUMS in Recipe Class
			for(Recipes recipe : Recipes.values()){
				//Create REcipe JSON Object from Recipe ENUMS
				JSONObject recipeObject = new JSONObject();

				//Place recipeName into JSONObject
				recipeObject.put("recipeName",recipe.setRecipe());
				//Place ingredients into JSONObject
				recipeObject.put("ingredients",recipe.setIngredients());
				queryJSON.put(recipe.name().toString(), recipeObject);

			}//For loop closing Bracket

			recipeObjects.put("query", queryJSON);

		} catch (JSONException e){
			e.printStackTrace();
		}//catch closing bracket

		return recipeObjects;
	}//builJSON closing bracket
	
	public static String readJSON(String selected){
		String recipeResults, recipeName, ingredients;

		//JSON Object recipe is data return from buildJSON function
		JSONObject recipe = buildJSON();
		
		//Try/Catch statement
		try{
			//try to query and retrieve recipeName and ingredients
			recipeName = recipe.getJSONObject("query").getJSONObject(selected).getString("recipeName");
			ingredients = recipe.getJSONObject("query").getJSONObject(selected).getString("ingredients");
			
			//string returned and displayed in recipeText on MainActivity.
			recipeResults = "Course: "+recipeName + "\r\n\r\nIngredients:\r\n" + ingredients;
			
			
		//error out if invalid try
		}catch (JSONException e){
			e.printStackTrace();
			recipeResults = e.toString();
		}
		//return recipeResults to be displayed on MainActivity upon use recipe selection
		return recipeResults;
		
	}

}//JSON closing bracket
