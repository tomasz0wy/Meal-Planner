package com.tkkd.mealplanner.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "recipe_name")
    public String recName;

    public String description;
}
