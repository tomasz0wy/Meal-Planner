package com.tkkd.mealplanner.Database;

import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import com.tkkd.mealplanner.Database.DAO.IngredientDAO;
import com.tkkd.mealplanner.Database.DAO.MeasureDAO;
import com.tkkd.mealplanner.Database.Entities.Home;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.Measure;

import java.util.Date;

//Dla Klaudii, żony mojej kochanej najbardziej na świecie najlepszej lepszej nie znajdę
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

    public static void insertIngredient(AppDatabase database, String ingName, long mesID,int expTime){
        IngredientDAO ingredientDAO = database.getIngredientDAO();
        Ingredient ingredient = new Ingredient();
        ingredient.ingName = ingName;
        ingredient.mesId = mesID;
        ingredient.expTime = expTime;
        ingredientDAO.insertIngredient(ingredient);
    }

    public static void insertHome(AppDatabase database, long ingId, int quantity){
        HomeDAO homeDAO = database.getHomeDAO();
        Home home = new Home();
        home.ingId = ingId;
        home.quantity = quantity;
        home.insertTime = new Date().getTime();
        homeDAO.insertHome(home);
    }

    public static void populateDatabase(AppDatabase database){
        insertMeasure(database,"kg","g","L");
        insertIngredient(database,"ziemniaki",1,7);
        insertIngredient(database,"mleko",3);
        insertHome(database,1,5);
        insertHome(database,2,3);
        insertHome(database,1,6);
    }
}
