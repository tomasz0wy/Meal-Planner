package com.tkkd.mealplanner.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index("measure_id"),tableName = "ingredient",
foreignKeys = @ForeignKey(entity = Measure.class,parentColumns = "id",childColumns = "measure_id"))
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "ingredient_name")
    public String ingName;

    @ColumnInfo(name = "measure_id")
    public long mesId;

    @NonNull
    public String toString(){
        return ingName.substring(0,1).toUpperCase() + ingName.substring(1);
    }
}
