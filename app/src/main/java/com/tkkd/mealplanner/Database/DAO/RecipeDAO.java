package com.tkkd.mealplanner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tkkd.mealplanner.Database.Entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Insert
    void insertRecipe(Recipe... recipes);

    @Query("SELECT * FROM recipe")
    List<Recipe> getRecipes();

    @Query("SELECT * FROM recipe WHERE id = :recId")
    Recipe getOneRecipe(long recId);
}
