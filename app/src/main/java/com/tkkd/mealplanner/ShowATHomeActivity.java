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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import com.tkkd.mealplanner.Database.Inserts;

import java.util.Collections;
import java.util.List;

public class ShowATHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private static TextView message;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase database;
    private List<HomeDAO.ATHome> data;

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

        message = findViewById(R.id.empty_message);

        recyclerView = findViewById(R.id.recycler_test);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        data = database.getHomeDAO().getATHome();
        mAdapter = new ShowATHomeRecyclerAdapter(data,database);
        recyclerView.setAdapter(mAdapter);

        isEmpty();

        ToggleButton sortButton = findViewById(R.id.sort_button);
        sortButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    data = database.getHomeDAO().getATHome();
                    data = sortList(data);
                    mAdapter = new ShowATHomeRecyclerAdapter(data,database);
                    recyclerView.setAdapter(mAdapter);
                }else {
                    data = database.getHomeDAO().getATHome();
                    mAdapter = new ShowATHomeRecyclerAdapter(data,database);
                    recyclerView.setAdapter(mAdapter);
                }
            }
        });

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
        mAdapter = new ShowATHomeRecyclerAdapter(database.getHomeDAO().getATHome(),database);
        recyclerView.setAdapter(mAdapter);
        isEmpty();
    }

    public static void isEmpty(){
        if (ShowATHomeActivity.mAdapter.getItemCount() == 0) {
            ShowATHomeActivity.message.setVisibility(View.VISIBLE);
        } else {
            ShowATHomeActivity.message.setVisibility(View.INVISIBLE);
        }
    }

    List<HomeDAO.ATHome> sortList(List<HomeDAO.ATHome> list){
        boolean sorted = false;
        while (!sorted){
            sorted = true;
            for(int i=0;i<list.size()-1;i++){
                int test1 = Integer.valueOf(list.get(i).expTime.substring(6,8));
                int test2 = Integer.valueOf(list.get(i+1).expTime.substring(6,8));
                if(test1 > test2){
                    Collections.swap(list,i,i+1);
                    sorted = false;
                }else if(test1 == test2){
                    int test3 = Integer.valueOf(list.get(i).expTime.substring(3,5));
                    int test4 = Integer.valueOf(list.get(i+1).expTime.substring(3,5));
                    if(test3 > test4){
                        Collections.swap(list,i,i+1);
                        sorted = false;
                    }else if(test3 == test4){
                        int test5 = Integer.valueOf(list.get(i).expTime.substring(0,2));
                        int test6 = Integer.valueOf(list.get(i+1).expTime.substring(0,2));
                        if(test5 > test6){
                            Collections.swap(list,i,i+1);
                            sorted = false;
                        }
                    }
                }
            }
        }
        return list;
    }
}