package com.tkkd.mealplanner.Database;

import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import com.tkkd.mealplanner.Database.DAO.IngredientDAO;
import com.tkkd.mealplanner.Database.DAO.IngredientListDAO;
import com.tkkd.mealplanner.Database.DAO.MeasureDAO;
import com.tkkd.mealplanner.Database.DAO.RecipeDAO;
import com.tkkd.mealplanner.Database.DAO.ShoppingListDAO;
import com.tkkd.mealplanner.Database.Entities.Home;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.IngredientListForRecipe;
import com.tkkd.mealplanner.Database.Entities.Measure;
import com.tkkd.mealplanner.Database.Entities.Recipe;
import com.tkkd.mealplanner.Database.Entities.ShoppingList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Inserts {
    private static void insertMeasure(AppDatabase database,String... name){
        MeasureDAO measureDAO = database.getMeasureDAO();

        for (String oneName : name) {
            Measure measure = new Measure();
            measure.mesName = oneName;
            measureDAO.insertMeasure(measure);
        }
    }

    public static void insertIngredient(AppDatabase database, String ingName, long mesID){
        IngredientDAO ingredientDAO = database.getIngredientDAO();
        Ingredient ingredient = new Ingredient();
        ingredient.ingName = ingName;
        ingredient.mesId = mesID;
        ingredientDAO.insertIngredient(ingredient);
    }

    public static void insertHome(AppDatabase database, long ingId, float quantity, String expTime){
        HomeDAO homeDAO = database.getHomeDAO();
        Home home = new Home();
        home.ingId = ingId;
        home.quantity = quantity;
        home.insertTime = new Date().getTime();
        home.expTime = expTime;
        homeDAO.insertHome(home);
    }

    private static void insertRecipe(AppDatabase database, String recipeName, String recDescription, String instructions){
        RecipeDAO recipeDAO = database.getRecipeDAO();
        Recipe recipe = new Recipe();
        recipe.recName = recipeName;
        recipe.description = recDescription;
        recipe.instructions = instructions;
        recipeDAO.insertRecipe(recipe);
    }

    private static void insertIngList(AppDatabase database, long recId, String name, float quantity, long mesId){
        IngredientListDAO ingredientListDAO = database.getIngredientListDAO();
        IngredientDAO ingredientDAO = database.getIngredientDAO();
        IngredientListForRecipe ingredientListForRecipe = new IngredientListForRecipe();
        Ingredient ingredient = ingredientDAO.getOneIngredient(name);
        ingredientListForRecipe.recipeId = recId;
        ingredientListForRecipe.ingredientId = ingredient.id;
        ingredientListForRecipe.quantity = quantity;
        ingredientListForRecipe.mesId = mesId;
        ingredientListDAO.insertList(ingredientListForRecipe);
    }

    public static void insertShoppingList(AppDatabase database, long ingId, long mesId, int quantity){
        ShoppingListDAO shoppingListDAO = database.getShoppingListDAO();
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.ingredientId = ingId;
        shoppingList.mesId = mesId;
        shoppingList.quantityShop = quantity;
        shoppingListDAO.insertShopList(shoppingList);
    }

    public static void populateDatabase(AppDatabase database){
        insertMeasure(database,"","kg","g","l","ml","cup","can","pack","pinch","tsp");
        insertIngredient(database,"potatoes",2);
        insertIngredient(database,"tomatoes",1);
        insertIngredient(database,"cucumbers",1);
        insertIngredient(database,"lemons",1);
        insertIngredient(database,"milk",4);
        insertIngredient(database,"eggs",1);
        insertIngredient(database,"salt",3);
        insertIngredient(database,"pepper",3);
        insertIngredient(database,"butter",3);
        insertHome(database,1,5,"29/12/19");
        insertHome(database,2,3,"30/11/20");
        insertHome(database,1,6,"29/11/20");
        insertRecipe(database,"Scrambled eggs","Perfect breakfast for everyone",
                "1. Beat eggs, milk, salt and pepper in medium bowl until blended. \n\n" +
                        "2. Heat butter in large nonstick skillet over medium heat until hot. Pour" +
                        " in egg mixture. As eggs begin to set, gently pull the eggs across the pan" +
                        " with a spatula, forming large soft curds.\n\n" +
                        "3. Continue cooking—pulling, lifting and folding eggs—until thickened " +
                        "and no visible liquid egg remains. Do not stir constantly. Remove from heat." +
                        " Serve immediately.");
        insertRecipe(database,"Omlet","No co no, omlet xD","");
        insertRecipe(database,"Obiad","No co no, obiad xD","");
        insertIngList(database,1,"eggs",4,1);
        insertIngList(database,1,"milk",0.25f,6);
        insertIngList(database,1,"salt",1,9);
        insertIngList(database,1,"pepper",1,9);
        insertIngList(database,1,"butter",2,10);
    }
}
