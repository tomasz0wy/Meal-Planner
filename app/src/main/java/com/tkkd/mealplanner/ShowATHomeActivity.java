package com.tkkd.mealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import com.tkkd.mealplanner.Database.DAO.IngredientDAO;
import com.tkkd.mealplanner.Database.DAO.MeasureDAO;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.Measure;
import com.tkkd.mealplanner.Database.Inserts;

import java.util.List;

public class ShowATHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_athome);

        AppDatabase database = Room.databaseBuilder(this,AppDatabase.class,"MealPlanner")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        SharedPreferences preferences = getSharedPreferences("Opener", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(preferences.getInt("IfOpen",0) == 0){
            Inserts.insertMeasure(database,"kg","g","L");
            Inserts.insertIngredient(database,"ziemniaki",1);
            Inserts.insertIngredient(database,"mleko",3);
            Inserts.insertHome(database,1,5);
            Inserts.insertHome(database,2,3);
            Inserts.insertHome(database,1,6);

            editor.putInt("IfOpen",1);
            editor.apply();
        }

        recyclerView = findViewById(R.id.recycler_test);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MealPlannerRecyclerAdapter(database.getHomeDAO().getATHome());
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fabAddATHome);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowATHomeActivity.this,AddATHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
