package com.tkkd.mealplanner.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import com.tkkd.mealplanner.Database.DAO.IngredientDAO;
import com.tkkd.mealplanner.Database.DAO.MeasureDAO;
import com.tkkd.mealplanner.Database.Entities.Home;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.Measure;

@Database(entities = {Measure.class, Ingredient.class, Home.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MeasureDAO getMeasureDAO();
    public abstract IngredientDAO getIngredientDAO();
    public abstract HomeDAO getHomeDAO();
}
