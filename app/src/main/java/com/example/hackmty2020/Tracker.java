package com.example.hackmty2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Tracker extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    RecyclerView mFirestoreList;

    FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.firestore_list);

        // Query
        Query query = firebaseFirestore.collection("checks");

        //Recycler Options
        FirestoreRecyclerOptions<CheckModel> options = new FirestoreRecyclerOptions.Builder<CheckModel>()
                .setQuery(query, CheckModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<CheckModel, CheckViewHolder>(options) {
            @NonNull
            @Override
            public CheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);

                return new CheckViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CheckViewHolder holder, int position, @NonNull CheckModel model) {
                holder.Name.setText(model.getDoctor_Encargado().trim());
                holder.Dpt.setText(model.getDepartamento().trim());
                holder.Date.setText(model.getTimeStmp().toDate().toString());

                Toast.makeText(Tracker.this, model.getTimeStmp().toDate().toString(), Toast.LENGTH_SHORT).show();
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mFirestoreList.setAdapter(adapter);







        }

    private class CheckViewHolder extends RecyclerView.ViewHolder{
        private TextView Name;
        private TextView Dpt;
        private TextView Date;

        public CheckViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.DoctorName);
            Dpt = itemView.findViewById(R.id.Dep);
            Date = itemView.findViewById(R.id.Time);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
