package com.daletupling.recipe;

public enum Recipes {
	//Recipe ENUM's
	ChickenTendoori("Dinner", "Cayenne Pepper\r\nGarlic\r\nLime Juice\r\nSalt\r\nChicken Breast\r\nGround Coriander\r\nFresh Ginger\r\nPaprika\r\nFresh Ginger\r\nGround Cumin\r\nPlain Yogurt"),
	IndianPudding("Dinner", "Butter\r\nMilk\r\nCinnamon\r\nMolasses\r\nCorn Meal\r\nSalt\r\nGround Ginger"),
	AppleDip("Snack", "Caramel Sauce\r\nHeath Bar\r\nCream Cheese\r\nHeavy Whipping Cream\r\nGranny Smith Apple"),
	TortelliniSoup("Lunch/Dinner", "Creame Cheese\r\nFrozen Tortellini\r\nDice Tomatoes\r\nItalian Sausage\r\nFresh Spinach\r\nVegetable Broth"),
	BreadPudding("Dinner", "Bread\r\nMilk\r\nVanilla Extract\r\nEggs\r\nNutmeg\r\nGranulated Sugar\r\nRaisins\r\nSalt");
	
	//String Variables
	private String recipeName;
	private String ingredients;
	
	//Recipe pass recipeName and ingredients
	private Recipes(String recipeName, String ingredients){
		//set recipe variables
		this.recipeName = recipeName;
		this.ingredients = ingredients;
	}
	
	//Set recipeName return recipeName
	public String setRecipe() {
		// TODO Auto-generated method stub
		return recipeName;
	}
	
	//Set recipe ingredients and return ingredients
	public String setIngredients() {
		// TODO Auto-generated method stub
		return ingredients;
	}

}
