package com.tkkd.mealplanner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tkkd.mealplanner.Database.Entities.ShoppingList;

import java.util.List;

@Dao
public interface ShoppingListDAO {

    @Insert
    void insertShopList(ShoppingList... shoppingLists);

    @Update
    void updateList(ShoppingList... shoppingLists);

    @Query("SELECT * FROM shopping_list")
    List<ShoppingList> getShopList();

    @Query("SELECT * FROM shopping_list WHERE ingredient_id = :ingId")
    ShoppingList getOneShopList(long ingId);
}
