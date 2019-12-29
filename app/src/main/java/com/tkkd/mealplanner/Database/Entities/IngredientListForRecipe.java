package com.tkkd.mealplanner.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "ing_recipe_ref",primaryKeys = {"recipe_id","ingredient_for_recipe_id"})
public class IngredientListForRecipe {

    @ColumnInfo(name = "recipe_id")
    public long recipeId;

    @ColumnInfo(name = "ingredient_for_recipe_id")
    public long ingredientId;
}
