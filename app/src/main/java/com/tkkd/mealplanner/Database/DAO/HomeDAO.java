package com.tkkd.mealplanner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tkkd.mealplanner.Database.Entities.Home;

import java.util.Date;
import java.util.List;

@Dao
public interface HomeDAO {

    @Insert
    void insertHome(Home... homes);

    @Query("" +
            "SELECT ingredient.ingredient_name AS ingName,ingredient.expiration_time AS expTime," +
            " measures.measure_name AS measure, at_home.quantity AS quantity, at_home.inserted_at AS insertTime " +
            "FROM at_home " +
            "INNER JOIN ingredient ON at_home.ingredient_id = ingredient.id " +
            "INNER JOIN measures ON ingredient.measure_id = measures.id")
    List<ATHome> getATHome();

    class ATHome{
        public String ingName;
        public String measure;
        public int quantity;
        public int expTime;
        public long insertTime;
    }
}
