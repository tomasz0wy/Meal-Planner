package com.tkkd.mealplanner;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MealPlannerRecyclerAdapter extends RecyclerView.Adapter<MealPlannerRecyclerAdapter.ViewHolder> {
    String[] test1;
    String[] test2;
    String[] test3;

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;

        public ViewHolder(LinearLayout linearLayout){
            super(linearLayout);
            this.linearLayout = linearLayout;
        }
    }

    public MealPlannerRecyclerAdapter(String[] number, String[] name,String[] quantity){
        test1 = number;
        test2 = name;
        test3 = quantity;
    }

    @Override
    public int getItemCount() {
        return test1.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_row,parent,false);
        return new ViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayout linearLayout = holder.linearLayout;
        TextView number = linearLayout.findViewById(R.id.number_text_view);
        TextView name = linearLayout.findViewById(R.id.name_text_view);
        TextView expiry = linearLayout.findViewById(R.id.expiry_text_view);

        number.setText(test1[position]);
        name.setText(test2[position]);
        expiry.setText(test3[position]);
    }
}
