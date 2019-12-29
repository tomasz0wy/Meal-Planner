package com.tkkd.mealplanner.Database;

import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import com.tkkd.mealplanner.Database.DAO.IngredientDAO;
import com.tkkd.mealplanner.Database.DAO.IngredientListDAO;
import com.tkkd.mealplanner.Database.DAO.MeasureDAO;
import com.tkkd.mealplanner.Database.DAO.RecipeDAO;
import com.tkkd.mealplanner.Database.Entities.Home;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.Measure;
import com.tkkd.mealplanner.Database.Entities.Recipe;

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

    private static void insertRecipe(AppDatabase database, String recipeName, String recDescription){
        RecipeDAO recipeDAO = database.getRecipeDAO();
        Recipe recipe = new Recipe();
        recipe.recName = recipeName;
        recipe.description = recDescription;
        recipeDAO.insertRecipe(recipe);
    }

    public static void populateDatabase(AppDatabase database){
        insertMeasure(database,"","kg","g","l","ml","can","pack");
        insertIngredient(database,"potatoes",2);
        insertIngredient(database,"tomatoes",1);
        insertIngredient(database,"cucumbers",1);
        insertIngredient(database,"lemons",1);
        insertIngredient(database,"milk",4);
        insertHome(database,1,5,"29/12/19");
        insertHome(database,2,3,"30/11/20");
        insertHome(database,1,6,"29/11/20");
        insertRecipe(database,"Jajecznica","No co no, jajecznica xD");
        insertRecipe(database,"Omlet","No co no, omlet xD");
        insertRecipe(database,"Obiad","No co no, obiad xD");
    }

    public static List<Ingredient> getRecipesIngredients(AppDatabase database, long recId){
        IngredientListDAO ingredientListDAO = database.getIngredientListDAO();
        List<Long> ingList = ingredientListDAO.getIngList(recId);
        IngredientDAO ingredientDAO = database.getIngredientDAO();
        List<Ingredient> ingredients = new ArrayList<>();
        for (long val :
                ingList) {
            ingredients.add(ingredientDAO.getOneIngredientById(val));
        }
        return ingredients;
    }
}
