package com.example.currencyconverter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public ArrayList<Value> myData;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView currencyFrom, currencyTo;
        public MyViewHolder(View v) {
            super(v);
            currencyTo=v.findViewById(R.id.currencyTo);
            currencyFrom=v.findViewById(R.id.currencyFrom);
        }
    }

    public MyAdapter(ArrayList<Value> myData){
        this.myData=myData;
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.style, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Value value=myData.get(position);
        String key=value.getKey();
        holder.currencyFrom.setText("From Currency :"+value.getCurrencyFrom());
        holder.currencyTo.setText("To Currency :"+value.getCurrencyTo());
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

}
