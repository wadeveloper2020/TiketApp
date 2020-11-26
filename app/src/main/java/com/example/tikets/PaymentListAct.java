package com.example.tikets;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentListAct extends AppCompatActivity {

    Button btn_lanjut_bayar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);

        btn_lanjut_bayar = findViewById(R.id.btn_lanjut_bayar);

        btn_lanjut_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}