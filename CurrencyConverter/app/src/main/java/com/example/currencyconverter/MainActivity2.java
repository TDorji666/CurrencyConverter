package com.example.currencyconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseDatabase database;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Animation anim= AnimationUtils.loadAnimation(this,R.anim.animation);
        recyclerView.setAnimation(anim);


        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Country");

        ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ArrayList<Value> list=new ArrayList<>();
                    for (DataSnapshot ds: snapshot.getChildren())
                    {
                        String key=ds.getKey();
                        Value value=new Value(ds.child("currencyFrom").getValue(String.class)
                                ,ds.child("currencyTo").getValue(String.class),key);
                        list.add(value);
                    }
                    MyAdapter myAdapter=new MyAdapter(list);
                    recyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addListenerForSingleValueEvent(eventListener);




    }


}