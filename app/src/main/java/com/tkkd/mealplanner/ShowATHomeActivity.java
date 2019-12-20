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
import com.tkkd.mealplanner.Database.Inserts;

public class ShowATHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_athome);

        database = Room.databaseBuilder(this,AppDatabase.class,"MealPlanner")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        SharedPreferences preferences = getSharedPreferences("Opener", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(preferences.getInt("IfOpen",0) == 0){
            Inserts.populateDatabase(database);
            editor.putInt("IfOpen",1);
            editor.apply();
        }

        recyclerView = findViewById(R.id.recycler_test);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ShowATHomeRecyclerAdapter(database.getHomeDAO().getATHome());
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

    @Override
    protected void onRestart() {
        super.onRestart();
        mAdapter = new ShowATHomeRecyclerAdapter(database.getHomeDAO().getATHome());
        recyclerView.setAdapter(mAdapter);
    }
}