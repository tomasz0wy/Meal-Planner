package com.tkkd.mealplanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.IngredientListForRecipe;
import com.tkkd.mealplanner.Database.Entities.Measure;
import com.tkkd.mealplanner.Database.Entities.ShoppingList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SingleRecipeRecyclerAdapter extends RecyclerView.Adapter<SingleRecipeRecyclerAdapter.ViewHolder> {

    private List<IngredientListForRecipe> data;
    private AppDatabase database;

    private List<ShoppingList> shopList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;

        ViewHolder(LinearLayout linearLayout){
            super(linearLayout);
            this.linearLayout = linearLayout;
        }
    }

    SingleRecipeRecyclerAdapter(List<IngredientListForRecipe> data, AppDatabase database){
        this.data = data;
        this.database = database;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SingleRecipeRecyclerAdapter.ViewHolder holder, int position) {
        final LinearLayout linearLayout = holder.linearLayout;

        TextView quantityList = linearLayout.findViewById(R.id.quantity_list);
        TextView measureList = linearLayout.findViewById(R.id.measure_list);
        TextView nameList = linearLayout.findViewById(R.id.name_list);

        float quantity = data.get(position).quantity;
        if(quantity == Math.round(quantity)){
            int quantitySmall = (int) quantity;
            quantityList.setText(String.valueOf(quantitySmall));
        }else {
            quantityList.setText(String.format(Locale.US,"%.2f",quantity));
        }

        String mesName = database.getMeasureDAO().getOneMeasureById(data.get(position).mesId).mesName;
        if(mesName.equals("")){
            measureList.setText(mesName);
        }else {
            measureList.setText(" " + mesName);
        }
        nameList.setText(" "+database.getIngredientDAO().getOneIngredientById(data.get(position).ingredientId).toString());

        List<HomeDAO.ATHome> compareList = database.getHomeDAO().getATHome();
        linearLayout.setBackgroundColor(Color.RED);
        Ingredient ingredient = database.getIngredientDAO().getOneIngredientById(data.get(position).ingredientId);
        Measure measure = database.getMeasureDAO().getOneMeasureById(data.get(position).mesId);

        boolean isInHome = false;
        boolean isEnough = false;
        HomeDAO.ATHome tester = new HomeDAO.ATHome();
        for(int i = 0;i < compareList.size();i++){
            String afterConversion = MeasureConverter.convert(data.get(position).quantity,measure.mesName);
            String compareQuantity = MeasureConverter.convert(compareList.get(i).quantity,compareList.get(i).measure);
            int afterConversionInt = (int) Math.floor(Float.valueOf(afterConversion.substring(0,afterConversion.length()-2)));
            int compareQuantityInt = (int) Math.floor(Float.valueOf(compareQuantity.substring(0,compareQuantity.length()-2)));
            if(ingredient.ingName.equals(compareList.get(i).ingName)){
                isInHome = true;
                if(afterConversionInt <= compareQuantityInt){
                    isEnough = true;
                    linearLayout.setBackgroundColor(Color.GREEN);
                }else {
                    tester = compareList.get(i);
                }
            }
        }
        if(!isInHome){
            ShoppingList shoppingList = new ShoppingList();
            shoppingList.ingredientId = data.get(position).ingredientId;

            String afterConversion = MeasureConverter.convert(data.get(position).quantity,measure.mesName);
            shoppingList.quantityShop = (int) Math.floor(Float.valueOf(afterConversion.substring(0,afterConversion.length()-2)));

            String afterConversionMes = afterConversion.substring(afterConversion.length()-2);
            Measure toInsertMes = database.getMeasureDAO().getOneMeasure(afterConversionMes.trim());
            shoppingList.mesId = toInsertMes.id;

            if(!shopList.contains(shoppingList)){
                shopList.add(shoppingList);
            }
        }else{
            if(!isEnough){
                String afterConversion = MeasureConverter.convert(data.get(position).quantity,measure.mesName);
                String compareQuantity = MeasureConverter.convert(tester.quantity,tester.measure);
                int afterConversionInt = (int) Math.floor(Float.valueOf(afterConversion.substring(0,afterConversion.length()-2)));
                int compareQuantityInt = (int) Math.floor(Float.valueOf(compareQuantity.substring(0,compareQuantity.length()-2)));
                int toInsert = afterConversionInt - compareQuantityInt;
                String afterConversionMes = afterConversion.substring(afterConversion.length()-2);

                Measure toInsertMes = database.getMeasureDAO().getOneMeasure(afterConversionMes.trim());

                ShoppingList shoppingList = new ShoppingList();
                shoppingList.ingredientId = data.get(position).ingredientId;
                shoppingList.quantityShop = toInsert;
                shoppingList.mesId = toInsertMes.id;
                if(!shopList.contains(shoppingList)){
                    shopList.add(shoppingList);
                }
            }
        }
        if(position == getItemCount()-1){
            TinyDB tinyDB = new TinyDB(linearLayout.getContext());
            ArrayList<Object> shopListObjects = new ArrayList<>();

            shopListObjects.addAll(shopList);

            tinyDB.putListObject("toInsert",shopListObjects);
        }
    }

    @NonNull
    @Override
    public SingleRecipeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recipe_recycler_row,
                parent,false);
        return new ViewHolder(linearLayout);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
