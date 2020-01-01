package com.tkkd.mealplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.Measure;
import com.tkkd.mealplanner.Database.Entities.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListRecyclerAdapter extends RecyclerView.Adapter<ShoppingListRecyclerAdapter.ViewHolder> {

    private List<ShoppingList> data;
    private List<ShoppingList> toInsertList = new ArrayList<>();
    private AppDatabase database;

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;

        ViewHolder(LinearLayout linearLayout){
            super(linearLayout);
            this.linearLayout = linearLayout;
        }
    }

    ShoppingListRecyclerAdapter(List<ShoppingList> data, AppDatabase database){
        this.data = data;
        this.database = database;
    }

    @NonNull
    @Override
    public ShoppingListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_recycler_row,
                parent,false);
        return new ViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListRecyclerAdapter.ViewHolder holder, final int position) {
        LinearLayout linearLayout = holder.linearLayout;
        final CheckBox shopCheckBox = linearLayout.findViewById(R.id.is_bought_box);
        TextView shopListName = linearLayout.findViewById(R.id.ing_name_shopping);
        TextView shopListQuantity = linearLayout.findViewById(R.id.quantity_shopping);
        TextView shopListMeasure = linearLayout.findViewById(R.id.mes_name_shopping);
        Ingredient ingredient = database.getIngredientDAO().getOneIngredientById(data.get(position).ingredientId);
        Measure measure = database.getMeasureDAO().getOneMeasureById(data.get(position).mesId);
        String ingName = ingredient.ingName.substring(0,1).toUpperCase() + ingredient.ingName.substring(1);

        shopCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shopCheckBox.isChecked()){
                    toInsertList.add(data.get(position));
                }else {
                    toInsertList.remove(data.get(position));
                }
            }
        });
        shopListName.setText(ingName);
        shopListQuantity.setText(String.valueOf(data.get(position).quantityShop));
        shopListMeasure.setText(measure.mesName);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
