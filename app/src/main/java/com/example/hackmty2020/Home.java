package com.example.hackmty2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Home extends AppCompatActivity {

    Button GenerateQR, EscanBtn, ButtonTrack, bot;
    ImageView qrImage;
    String CurrentID;
    String CurrentTipo = "NULL";
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GenerateQR = findViewById(R.id.buttonGen);
        qrImage = findViewById(R.id.imagenview);
        EscanBtn = findViewById(R.id.ScanButton);
        ButtonTrack = findViewById(R.id.trackBtn);
        bot = findViewById(R.id.botButton);

        CurrentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        GenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupUserQRcode(FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
        });

        bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://bot.dialogflow.com/1d211aab-2466-4f2a-a36a-f924dd0f1ff4";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        ButtonTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Tracker.class);
                intent.putExtra("EXTRA_USER_ID", CurrentID);

                startActivity(intent);
            }
        });

        EscanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Home.this, CurrentID, Toast.LENGTH_SHORT).show();

                DocumentReference docRef = db.collection("usuarios").document(CurrentID);

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String currentTipo = document.getString("Tipo").trim();
                                if(currentTipo.equals("Doctor")){
                                    String CurrentDpt = document.getString("Departamento").trim();
                                    String Nombre = document.getString("Nombre").trim();
                                    Intent intent = new Intent(getBaseContext(), Escan.class);
                                    intent.putExtra("EXTRA_DPT_ID", CurrentDpt);
                                    intent.putExtra("EXTRA_NAME_ID", Nombre);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(Home.this, "No tiene los permisos para esta función" + currentTipo, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Home.this, "No hay documento", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Home.this, "Fallo" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void setupUserQRcode(String firebaseUID) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            BitMatrix bitMatrix = multiFormatWriter.encode(firebaseUID, BarcodeFormat.QR_CODE, 500, 500);
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}