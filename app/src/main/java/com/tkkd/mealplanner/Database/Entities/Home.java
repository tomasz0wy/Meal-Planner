package com.tkkd.mealplanner.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index("ingredient_id"),tableName = "at_home",
        foreignKeys = @ForeignKey(entity = Ingredient.class,parentColumns = "id", childColumns = "ingredient_id"))
public class Home {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public float quantity;

    @ColumnInfo(name = "ingredient_id")
    public long ingId;

    @ColumnInfo(name = "inserted_at")
    public long insertTime;

    @ColumnInfo(name = "expiration_time")
    public String expTime;

    @ColumnInfo(name = "measure_id")
    public long mesId;
}
