package com.tkkd.mealplanner.Database;

import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import com.tkkd.mealplanner.Database.DAO.IngredientDAO;
import com.tkkd.mealplanner.Database.DAO.MeasureDAO;
import com.tkkd.mealplanner.Database.Entities.Home;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.Measure;

import java.util.Date;

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

    public static void insertHome(AppDatabase database, long ingId, int quantity, String expTime){
        HomeDAO homeDAO = database.getHomeDAO();
        Home home = new Home();
        home.ingId = ingId;
        home.quantity = quantity;
        home.insertTime = new Date().getTime();
        home.expTime = expTime;
        homeDAO.insertHome(home);
    }

    public static void populateDatabase(AppDatabase database){
        insertMeasure(database,"","kg","g","l","ml","can","pack");
        insertIngredient(database,"potatoes",2);
        insertIngredient(database,"tomatoes",1);
        insertIngredient(database,"cucumbers",1);
        insertIngredient(database,"lemons",1);
        insertIngredient(database,"milk",4);
        insertHome(database,1,5,"29/12/19");
        insertHome(database,2,3,"29/12/19");
        insertHome(database,1,6,"29/12/19");
    }
}
