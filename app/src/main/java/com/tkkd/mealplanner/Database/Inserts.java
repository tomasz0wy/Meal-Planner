package com.tkkd.mealplanner.Database;

import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import com.tkkd.mealplanner.Database.DAO.IngredientDAO;
import com.tkkd.mealplanner.Database.DAO.MeasureDAO;
import com.tkkd.mealplanner.Database.Entities.Home;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.Measure;

public class Inserts {
    public static void insertMeasure(AppDatabase database,String... name){
        MeasureDAO measureDAO = database.getMeasureDAO();

        for(int i=0;i<name.length;i++){
            Measure measure = new Measure();
            measure.mesName = name[i];
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

    public static void insertHome(AppDatabase database, long ingId, int quantity){
        HomeDAO homeDAO = database.getHomeDAO();
        Home home = new Home();
        home.ingId = ingId;
        home.quantity = quantity;
        homeDAO.insertHome(home);
    }
}
