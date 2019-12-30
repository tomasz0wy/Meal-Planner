package com.tkkd.mealplanner.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list")
public class ShoppingList {

    @PrimaryKey
    public long id;

    @ColumnInfo(name = "ingredient_id")
    public long ingredientId;

    @ColumnInfo(name = "quantity_shop")
    public float quantityShop;

    @ColumnInfo(name = "measure_id")
    public long mesId;
}
