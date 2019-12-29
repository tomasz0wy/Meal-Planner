package com.tkkd.mealplanner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import java.util.List;

@Dao
public interface IngredientDAO {

    @Insert
    void insertIngredient(Ingredient... ingredients);

    @Query("SELECT * FROM ingredient")
    List<Ingredient> getIngredients();

    @Query("SELECT * FROM ingredient WHERE ingredient_name = :ingName")
    Ingredient getOneIngredient(String ingName);

    @Query("SELECT * FROM ingredient WHERE ingredient_name = :ingId")
    Ingredient getOneIngredientById(long ingId);
}
