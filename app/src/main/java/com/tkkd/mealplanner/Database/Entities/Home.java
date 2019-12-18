package com.tkkd.mealplanner.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "at_home",
        foreignKeys = @ForeignKey(entity = Ingredient.class,parentColumns = "id", childColumns = "ingredient_id"))
public class Home {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public int quantity;

    @ColumnInfo(name = "ingredient_id")
    public long ingId;

    @ColumnInfo(name = "inserted_at")
    public long insertTime;
}
