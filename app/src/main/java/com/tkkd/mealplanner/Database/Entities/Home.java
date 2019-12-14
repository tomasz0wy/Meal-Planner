package com.tkkd.mealplanner.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "at_home")
public class Home {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long id;

    public int quantity;

    @ColumnInfo(name = "ingredient_id")
    public long ingId;
}
