package com.tkkd.mealplanner.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient",
foreignKeys = @ForeignKey(entity = Measure.class,parentColumns = "id",childColumns = "measure_id"))
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long id;

    @ColumnInfo(name = "ingredient_name")
    public String ingName;

    @ColumnInfo(name = "measure_id")
    public long mesId;
}
