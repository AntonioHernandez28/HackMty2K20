package com.example.hackmty2020;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1beta1.DocumentTransform;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Escan extends AppCompatActivity {

    Button EscanBtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escan);

        EscanBtn = findViewById(R.id.ScanButton);

        EscanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(Escan.this).initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if(result.getContents() != null){
                Map<String, Object> check = new HashMap<>();
                check.put("Departamento", getIntent().getStringExtra("EXTRA_DPT_ID"));
                check.put("Doctor Encargado", getIntent().getStringExtra("EXTRA_NAME_ID"));
                check.put("PacienteID", result.getContents());
                check.put("timeStmp", FieldValue.serverTimestamp());

                db.collection("checks").add(check);

            }
        }
    }
}