package com.tkkd.mealplanner.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(indices = @Index("measure_id"),tableName = "ing_recipe_ref",
        primaryKeys = {"recipe_id","ingredient_for_recipe_id"})
public class IngredientListForRecipe {

    @ColumnInfo(name = "recipe_id")
    public long recipeId;

    @ColumnInfo(name = "ingredient_for_recipe_id")
    public long ingredientId;

    @ColumnInfo(name = "rec_quantity")
    public float quantity;

    @ColumnInfo(name = "measure_id")
    public long mesId;
}
