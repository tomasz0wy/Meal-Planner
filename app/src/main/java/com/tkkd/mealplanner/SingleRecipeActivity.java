package com.tkkd.mealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.DAO.IngredientListDAO;
import com.tkkd.mealplanner.Database.DAO.RecipeDAO;
import com.tkkd.mealplanner.Database.Entities.IngredientListForRecipe;
import com.tkkd.mealplanner.Database.Entities.Recipe;
import com.tkkd.mealplanner.Database.Entities.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class SingleRecipeActivity extends AppCompatActivity {

    public static final String INTENT_RECIPE = "key";

    private List<ShoppingList> toInsert = new ArrayList<>();
    private TinyDB tinyDB;
    private AppDatabase database;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);

        database = Room.databaseBuilder(this, AppDatabase.class,"MealPlanner")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        Intent intent = getIntent();
        long intentExtraLong = intent.getLongExtra(INTENT_RECIPE,0);
        RecipeDAO recipeDAO = database.getRecipeDAO();
        IngredientListDAO ingredientListDAO = database.getIngredientListDAO();
        Recipe recipe = recipeDAO.getOneRecipe(intentExtraLong);
        List<IngredientListForRecipe> ingredientListForRecipe = ingredientListDAO.getIngList(intentExtraLong);

        recyclerView = findViewById(R.id.ingredient_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new SingleRecipeRecyclerAdapter(ingredientListForRecipe,database);
        recyclerView.setAdapter(mAdapter);

        TextView recipeName = findViewById(R.id.recipe_name);
        TextView recipeDescription = findViewById(R.id.recipe_description);
        TextView instructionList = findViewById(R.id.instruction_list);

        tinyDB = new TinyDB(this);

        ArrayList<Object> tempList = tinyDB.getListObject("toInsert", ShoppingList.class);
        for(Object o : tempList){
            toInsert.add((ShoppingList) o);
        }

        toInsert.size();

        recipeName.setText(recipe.recName);
        recipeDescription.setText(recipe.description);
        instructionList.setText(recipe.instructions);
    }
}
