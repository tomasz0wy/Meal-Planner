package com.tkkd.mealplanner;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tkkd.mealplanner.Database.DAO.HomeDAO;

import java.util.ArrayList;
import java.util.List;

public class MealPlannerRecyclerAdapter extends RecyclerView.Adapter<MealPlannerRecyclerAdapter.ViewHolder> {
    private List<HomeDAO.ATHome> ATHomeList;

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;

        ViewHolder(LinearLayout linearLayout){
            super(linearLayout);
            this.linearLayout = linearLayout;
        }
    }

    MealPlannerRecyclerAdapter(List<HomeDAO.ATHome> list){
        this.ATHomeList = list;
    }

    @Override
    public int getItemCount() {
        return this.ATHomeList.size();
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
        TextView measure = linearLayout.findViewById(R.id.measure_text_view);
        TextView quantity = linearLayout.findViewById(R.id.quantity_text_view);

        number.setText(Integer.toString(position+1));
        name.setText(ATHomeList.get(position).ingName);
        measure.setText(ATHomeList.get(position).measure);
        quantity.setText(Integer.toString(ATHomeList.get(position).quantity));
    }
}
