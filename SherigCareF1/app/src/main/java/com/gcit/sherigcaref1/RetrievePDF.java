package com.gcit.sherigcaref1;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RetrievePDF extends AppCompatActivity {
    RecyclerView recyclerView;
    PDFAdapter pDFAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_p_d_f);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<putPDF> options =
                new FirebaseRecyclerOptions.Builder<putPDF>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("pdf"),putPDF.class)
                        .build();

        pDFAdapter = new PDFAdapter(options);
        recyclerView.setAdapter(pDFAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pDFAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pDFAdapter.stopListening();
    }
}