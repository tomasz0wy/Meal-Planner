package com.tkkd.mealplanner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.tkkd.mealplanner.Database.Entities.Home;
import java.util.List;

@Dao
public interface HomeDAO {

    @Insert
    void insertHome(Home... homes);

    @Query("" +
            "SELECT ingredient.ingredient_name AS ingName,at_home.expiration_time AS expTime," +
            " at_home.measure_id AS measure, at_home.quantity AS quantity, at_home.inserted_at AS insertTime " +
            "FROM at_home " +
            "INNER JOIN ingredient ON at_home.ingredient_id = ingredient.id " +
            "INNER JOIN measures ON ingredient.measure_id = measures.id " +
            "ORDER BY ingName ASC")
    List<ATHome> getATHome();

    @Query("DELETE FROM at_home WHERE quantity = :quantity AND inserted_at = :insertTime AND ingredient_id = :ingId")
    void deleteATHome(float quantity, long insertTime, long ingId);

    class ATHome{
        public String ingName;
        public long measure;
        public float quantity;
        public String expTime;
        public long insertTime;
    }
}
