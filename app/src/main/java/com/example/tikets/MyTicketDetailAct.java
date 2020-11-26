package com.example.tikets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyTicketDetailAct extends AppCompatActivity {

    TextView xnama_wisata, xlokasi, xdate_wisata, xtime_wisata, xbiaya_penanganan;
    LinearLayout btn_back;
    Button btn_lanjut_bayar;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);

        xnama_wisata = findViewById(R.id.xnama_wisata);
        xlokasi = findViewById(R.id.xlokasi);
        xdate_wisata = findViewById(R.id.xdate_wisata);
        xtime_wisata = findViewById(R.id.xtime_wisata);
        xbiaya_penanganan = findViewById(R.id.xbiaya_penanganan);
        btn_lanjut_bayar = findViewById(R.id.btn_lanjut_bayar);

        btn_back = findViewById(R.id.back);

        Bundle bundle = getIntent().getExtras();
        final String nama_wisata_baru = bundle.getString("nama_wisata");

        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata_baru);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                xnama_wisata.setText(snapshot.child("nama_wisata").getValue().toString());
                xlokasi.setText(snapshot.child("lokasi").getValue().toString());
                xdate_wisata.setText(snapshot.child("date_wisata").getValue().toString());
                xtime_wisata.setText(snapshot.child("time_wisata").getValue().toString());
                xbiaya_penanganan.setText(snapshot.child("biaya_penanganan").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_lanjut_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyTicketDetailAct.this, PaymentListAct.class);
                startActivity(intent);

            }
        });


    }
}