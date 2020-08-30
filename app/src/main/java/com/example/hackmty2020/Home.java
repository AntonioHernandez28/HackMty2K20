package com.example.hackmty2020;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Home extends AppCompatActivity {

    Button GenerateQR;
    ImageView qrImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GenerateQR = findViewById(R.id.buttonGen);
        qrImage = findViewById(R.id.imagenview);

        GenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupUserQRcode(FirebaseAuth.getInstance().getCurrentUser().getUid());
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