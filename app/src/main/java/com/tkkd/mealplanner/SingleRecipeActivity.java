package com.tkkd.mealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.DAO.IngredientListDAO;
import com.tkkd.mealplanner.Database.DAO.RecipeDAO;
import com.tkkd.mealplanner.Database.Entities.IngredientListForRecipe;
import com.tkkd.mealplanner.Database.Entities.Recipe;
import com.tkkd.mealplanner.Database.Entities.ShoppingList;
import com.tkkd.mealplanner.Database.Inserts;
import java.util.ArrayList;
import java.util.List;

public class SingleRecipeActivity extends AppCompatActivity {

    public static final String INTENT_RECIPE = "key";

    public static List<ShoppingList> toInsert = new ArrayList<>();
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

        recipeName.setText(recipe.recName);
        recipeDescription.setText(recipe.description);
        instructionList.setText(recipe.instructions);
    }

    public void add(View view) {
        for(int i = 0; i < toInsert.size();i++){
            ShoppingList shoppingList = toInsert.get(i);
            Inserts.insertShoppingList(database,shoppingList.ingredientId,shoppingList.mesId,shoppingList.quantityShop);
        }
        Toast.makeText(this,"Added to shopping list",Toast.LENGTH_LONG).show();
    }
}
