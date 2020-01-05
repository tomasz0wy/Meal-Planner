package com.tkkd.mealplanner;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import com.tkkd.mealplanner.Database.Entities.Measure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowATHomeRecyclerAdapter extends RecyclerView.Adapter<ShowATHomeRecyclerAdapter.ViewHolder> {
    private List<HomeDAO.ATHome> ATHomeList;
    private AppDatabase database;

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;

        ViewHolder(LinearLayout linearLayout){
            super(linearLayout);
            this.linearLayout = linearLayout;
        }
    }

    ShowATHomeRecyclerAdapter(List<HomeDAO.ATHome> list,AppDatabase database){
        this.ATHomeList = list;
        this.database = database;
    }

    @Override
    public int getItemCount() {
        return this.ATHomeList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.at_home_recycler_row,parent,false);
        return new ViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final LinearLayout linearLayout = holder.linearLayout;
        long expTimeLong;
        String dateString;
        Date expDate = new Date();
        SimpleDateFormat sdf;
        Measure aTHomeMeasure = database.getMeasureDAO().getOneMeasureById(ATHomeList.get(position).measure);

        String afterConversion = MeasureConverter.convertToBigger(ATHomeList.get(position).quantity,aTHomeMeasure.mesName);
        float afterConversionFloat = Float.valueOf(afterConversion.substring(0,afterConversion.length()-2));
        Measure afterConversionMes = database.getMeasureDAO().getOneMeasure(afterConversion.substring(afterConversion.length()-2).trim());

        if(ATHomeList.get(position).expTime.equals("")){
            expTimeLong = ATHomeList.get(position).insertTime + 7*86400000;
            expDate.setTime(expTimeLong);
        }else{
            dateString = ATHomeList.get(position).expTime;
            try {
                Date date = new SimpleDateFormat("dd/MM/yy",Locale.US).parse(dateString);
                assert date != null;
                expDate.setTime(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (expDate.getYear() == new Date().getYear()) {
            sdf = new SimpleDateFormat("dd/MM",Locale.US);
        } else {
            sdf = new SimpleDateFormat("MM/yyyy",Locale.US);
        }
        String ingredient = ATHomeList.get(position).ingName.substring(0,1).toUpperCase() + ATHomeList.get(position).ingName.substring(1);

        //Get rows' objects
        TextView number = linearLayout.findViewById(R.id.number_text_view);
        TextView name = linearLayout.findViewById(R.id.name_text_view);
        TextView measure = linearLayout.findViewById(R.id.measure_text_view);
        TextView quantity = linearLayout.findViewById(R.id.quantity_text_view);
        TextView expTime = linearLayout.findViewById(R.id.exp_time_text_view);
        ImageButton del = linearLayout.findViewById(R.id.delete_button);
        View color = linearLayout.findViewById(R.id.color_rect);

        long test = expDate.getTime();
        long test2 = new Date().getTime();

        long colorPicker = (test - test2);

        if(afterConversionFloat == Math.floor(afterConversionFloat)){
            int afterConversionInt = (int) afterConversionFloat;
            quantity.setText(String.format(Locale.US,"%d",afterConversionInt));
        }else {
            quantity.setText(String.format(Locale.US,"%.1f",afterConversionFloat));
        }

        //Set objects' display
        number.setText(String.format(Locale.US,"%d",position+1));
        name.setText(ingredient);
        measure.setText(afterConversionMes.mesName);
        expTime.setText(sdf.format(expDate));


        if(colorPicker <= -86400000){
            color.setBackgroundColor(Color.BLACK);
        }
        else if(colorPicker < (2*86400000)){
            color.setBackgroundColor(Color.RED);
        }
        else if(colorPicker < (6*86400000)){
            color.setBackgroundColor(Color.YELLOW);
        }
        else{
            color.setBackgroundColor(Color.GREEN);
        }

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long ingIndex = database.getIngredientDAO().getOneIngredient(ATHomeList.get(position).ingName).id;
                String ingName = database.getIngredientDAO().getOneIngredient(ATHomeList.get(position).ingName).ingName;
                ingName = ingName.substring(0,1).toUpperCase() + ingName.substring(1);
                Toast.makeText(linearLayout.getContext(),ingName + " deleted",Toast.LENGTH_LONG).show();
                database.getHomeDAO().deleteATHome(ATHomeList.get(position).quantity, ATHomeList.get(position).insertTime,ingIndex);
                ATHomeList.remove(position);
                notifyDataSetChanged();
                ShowATHomeActivity.isEmpty();
            }
        });
    }
}
