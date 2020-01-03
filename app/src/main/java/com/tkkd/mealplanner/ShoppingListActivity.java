package com.tkkd.mealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.os.Bundle;
import android.view.View;

import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.Entities.ShoppingList;
import com.tkkd.mealplanner.Database.Inserts;

import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase database;
    private List<ShoppingList> data;
    private List<ShoppingList> toInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        database = Room.databaseBuilder(this, AppDatabase.class,"MealPlanner")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        data = database.getShoppingListDAO().getShopList();

        recyclerView = findViewById(R.id.shop_list_recycler);
        mAdapter = new ShoppingListRecyclerAdapter(data,database);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mAdapter = new ShoppingListRecyclerAdapter(data,database);
        recyclerView.setAdapter(mAdapter);
    }

    public void endShop(View view) {
        toInsert = ShoppingListRecyclerAdapter.toInsertList;
        for (int i = 0; i < toInsert.size();i++){
            ShoppingList list = toInsert.get(i);
            Inserts.insertHome(database,list.ingredientId,list.quantityShop,"29/12/19",list.mesId);
            database.getShoppingListDAO().removeList(list);
        }
        data = database.getShoppingListDAO().getShopList();
        mAdapter = new ShoppingListRecyclerAdapter(data,database);
        recyclerView.setAdapter(mAdapter);
        ShoppingListRecyclerAdapter.toInsertList.clear();
    }
}
