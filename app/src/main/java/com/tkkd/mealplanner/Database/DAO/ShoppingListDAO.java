package com.tkkd.mealplanner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tkkd.mealplanner.Database.Entities.ShoppingList;

import java.util.List;

@Dao
public interface ShoppingListDAO {

    @Insert
    void insertShopList(ShoppingList... shoppingLists);

    @Query("SELECT * FROM shopping_list")
    List<ShoppingList> getShopList();
}
