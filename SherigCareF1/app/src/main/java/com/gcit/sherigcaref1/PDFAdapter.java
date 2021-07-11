package com.gcit.sherigcaref1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PDFAdapter extends FirebaseRecyclerAdapter<putPDF, PDFAdapter.viewholder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PDFAdapter(@NonNull FirebaseRecyclerOptions<putPDF> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder holder, int i, @NonNull putPDF put) {
        holder.textView.setText(put.getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.textView.getContext(),PDFViewActivity.class);
                intent.putExtra("PDFImage",put.getUrl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.textView.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_single_view,parent,false);
        return new viewholder(v);
    }

    class viewholder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear);
            textView = itemView.findViewById(R.id.name);
        }
    }
}
