package com.tkkd.mealplanner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tkkd.mealplanner.Database.Entities.IngredientListForRecipe;

import java.util.List;

@Dao
public interface IngredientListDAO {

    @Insert
    void insertList(IngredientListForRecipe... ingredientListForRecipes);

    @Query("SELECT * FROM ing_recipe_ref WHERE recipe_id = :recipeId")
    List<IngredientListForRecipe> getIngList(long recipeId);
}
