package com.tkkd.mealplanner.Database;

import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import com.tkkd.mealplanner.Database.DAO.IngredientDAO;
import com.tkkd.mealplanner.Database.DAO.IngredientListDAO;
import com.tkkd.mealplanner.Database.DAO.MeasureDAO;
import com.tkkd.mealplanner.Database.DAO.RecipeDAO;
import com.tkkd.mealplanner.Database.DAO.ShoppingListDAO;
import com.tkkd.mealplanner.Database.Entities.Home;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.IngredientListForRecipe;
import com.tkkd.mealplanner.Database.Entities.Measure;
import com.tkkd.mealplanner.Database.Entities.Recipe;
import com.tkkd.mealplanner.Database.Entities.ShoppingList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Inserts {
    private static void insertMeasure(AppDatabase database,String... name){
        MeasureDAO measureDAO = database.getMeasureDAO();

        for (String oneName : name) {
            Measure measure = new Measure();
            measure.mesName = oneName;
            measureDAO.insertMeasure(measure);
        }
    }

    public static void insertIngredient(AppDatabase database, String ingName, long mesID){
        IngredientDAO ingredientDAO = database.getIngredientDAO();
        Ingredient ingredient = new Ingredient();
        ingredient.ingName = ingName;
        ingredient.mesId = mesID;
        ingredientDAO.insertIngredient(ingredient);
    }

    public static void insertHome(AppDatabase database, long ingId, float quantity, String expTime){
        HomeDAO homeDAO = database.getHomeDAO();
        Home home = new Home();
        home.ingId = ingId;
        home.quantity = quantity;
        home.insertTime = new Date().getTime();
        home.expTime = expTime;
        homeDAO.insertHome(home);
    }

    private static void insertRecipe(AppDatabase database, String recipeName, String recDescription, String instructions){
        RecipeDAO recipeDAO = database.getRecipeDAO();
        Recipe recipe = new Recipe();
        recipe.recName = recipeName;
        recipe.description = recDescription;
        recipe.instructions = instructions;
        recipeDAO.insertRecipe(recipe);
    }

    private static void insertIngList(AppDatabase database, long recId, String name, float quantity, long mesId){
        IngredientListDAO ingredientListDAO = database.getIngredientListDAO();
        IngredientDAO ingredientDAO = database.getIngredientDAO();
        IngredientListForRecipe ingredientListForRecipe = new IngredientListForRecipe();
        Ingredient ingredient = ingredientDAO.getOneIngredient(name);
        ingredientListForRecipe.recipeId = recId;
        ingredientListForRecipe.ingredientId = ingredient.id;
        ingredientListForRecipe.quantity = quantity;
        ingredientListForRecipe.mesId = mesId;
        ingredientListDAO.insertList(ingredientListForRecipe);
    }

    public static void insertShoppingList(AppDatabase database, long ingId, long mesId, int quantity){
        ShoppingListDAO shoppingListDAO = database.getShoppingListDAO();
        ShoppingList tester = shoppingListDAO.getOneShopList(ingId);
        if(tester == null){
            ShoppingList shoppingList = new ShoppingList();
            shoppingList.ingredientId = ingId;
            shoppingList.mesId = mesId;
            shoppingList.quantityShop = quantity;
            shoppingListDAO.insertShopList(shoppingList);
        }else {
            tester.quantityShop += quantity;
            shoppingListDAO.updateList(tester);
        }
    }

    public static void populateDatabase(AppDatabase database){
        insertMeasure(database,"","kg","g","l","ml","cup","can","pack","pinch","tsp","tbsp");
        insertIngredient(database,"potatoes",2);
        insertIngredient(database,"tomatoes",1);
        insertIngredient(database,"cucumbers",1);
        insertIngredient(database,"lemons",1);
        insertIngredient(database,"milk",4);
        insertIngredient(database,"eggs",1);
        insertIngredient(database,"salt",3);
        insertIngredient(database,"pepper",3);
        insertIngredient(database,"butter",3);
        insertIngredient(database,"olive oil",4);
        insertIngredient(database,"bacon",1);
        insertIngredient(database,"onion",1);
        insertIngredient(database,"celery stick",1);
        insertIngredient(database,"carrot",1);
        insertIngredient(database,"garlic clove",1);
        insertIngredient(database,"beef mince",2);
        insertIngredient(database,"tomato puree",4);
        insertIngredient(database,"chopped tomatoes",7);
        insertIngredient(database,"honey",5);
        insertIngredient(database,"lasagne sheets",5);
        insertIngredient(database,"creme fraiche",2);
        insertIngredient(database,"mozzarella",3);
        insertIngredient(database,"parmesan",3);
        insertHome(database,1,5,"29/12/19");
        insertHome(database,2,3,"30/11/20");
        insertHome(database,1,6,"29/11/20");
        insertRecipe(database,"Scrambled eggs","Perfect breakfast for everyone",
                "1. Beat eggs, milk, salt and pepper in medium bowl until blended. \n\n" +
                        "2. Heat butter in large nonstick skillet over medium heat until hot. Pour" +
                        " in egg mixture. As eggs begin to set, gently pull the eggs across the pan" +
                        " with a spatula, forming large soft curds.\n\n" +
                        "3. Continue cooking—pulling, lifting and folding eggs—until thickened " +
                        "and no visible liquid egg remains. Do not stir constantly. Remove from heat." +
                        " Serve immediately.");
        insertRecipe(database,"Lasagne","Kids will love to help assemble this" +
                " easiest ever pasta bake with streaky bacon, beef mince, a crème fraîche sauce and" +
                " gooey mozzarella","" +
                "1. Heat the oil in a large saucepan. Use kitchen scissors to snip the bacon into" +
                " small pieces, or use a sharp knife to chop it on a chopping board. Add the bacon" +
                " to the pan and cook for just a few mins until starting to turn golden. Add the" +
                " onion, celery and carrot, and cook over a medium heat for 5 mins, stirring" +
                " occasionally, until softened.\n\n" +
                "2. Add the garlic and cook for 1 min, then tip in the mince and cook, stirring and" +
                " breaking it up with a wooden spoon, for about 6 mins until browned all over.\n\n" +
                "3. Stir in the tomato purée and cook for 1 min, mixing in well with the beef and" +
                " vegetables. Tip in the chopped tomatoes. Fill each can half full with water to" +
                " rinse out any tomatoes left in the can, and add to the pan. Add the honey and" +
                " season to taste. Simmer for 20 mins.\n\n" +
                "4. Heat oven to 200C/180C fan/gas 6. To assemble the lasagne, ladle a little of the" +
                " ragu sauce into the bottom of the roasting tin or casserole dish, spreading the " +
                "sauce all over the base. Place 2 sheets of lasagne on top of the sauce overlapping " +
                "to make it fit, then repeat with more sauce and another layer of pasta. Repeat with" +
                " a further 2 layers of sauce and pasta, finishing with a layer of pasta.\n\n" +
                "5. Put the crème fraîche in a bowl and mix with 2 tbsp water to loosen it and make" +
                " a smooth pourable sauce. Pour this over the top of the pasta, then top with" +
                " the mozzarella. Sprinkle Parmesan over the top and bake for 25–30 mins until golden" +
                " and bubbling. Serve scattered with basil, if you like.");
        insertIngList(database,1,"eggs",4,1);
        insertIngList(database,1,"milk",0.25f,6);
        insertIngList(database,1,"salt",1,9);
        insertIngList(database,1,"pepper",1,9);
        insertIngList(database,1,"butter",2,10);
        insertIngList(database,2,"olive oil",1,11);
        insertIngList(database,2,"bacon",2,1);
        insertIngList(database,2,"onion",1,1);
        insertIngList(database,2,"celery stick",1,1);
        insertIngList(database,2,"carrot",1,1);
        insertIngList(database,2,"garlic clove",2,1);
        insertIngList(database,2,"beef mince",500,3);
        insertIngList(database,2,"tomato puree",1,11);
        insertIngList(database,2,"chopped tomatoes",2,7);
        insertIngList(database,2,"honey",1,11);
        insertIngList(database,2,"lasagne sheets",500,3);
        insertIngList(database,2,"creme fraiche",400,5);
        insertIngList(database,2,"mozzarella",125,3);
        insertIngList(database,2,"parmesan",50,3);
    }
}
