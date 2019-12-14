package com.tkkd.mealplanner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import com.tkkd.mealplanner.Database.Entities.Ingredient;

@Dao
public interface IngredientDAO {

    @Insert
    public void insertIngredient(Ingredient... ingredients);
}
