package com.example.tikets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailTiketAct extends AppCompatActivity {

    Button btn_buy_tiket;
    TextView title_ticket, location_ticket,
            photo_spot_ticket, wifi_ticket,
            festival_ticket, short_desc_ticket;
    LinearLayout btn_back;
    ImageView header_ticket_detail;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tiket);

        btn_buy_tiket = findViewById(R.id.btn_bay_tiket);

        title_ticket = findViewById(R.id.title_ticket);
        location_ticket = findViewById(R.id.location_ticket);
        photo_spot_ticket = findViewById(R.id.photo_spot_ticket);
        wifi_ticket = findViewById(R.id.wifi_ticket);
        festival_ticket = findViewById(R.id.festival_ticket);
        short_desc_ticket = findViewById(R.id.short_desc_ticket);
        header_ticket_detail = findViewById(R.id.header_ticket_detail);
        btn_back = findViewById(R.id.back);

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket = bundle.getString("jenis_ticket");

        //mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                title_ticket.setText(snapshot.child("nama_wisata").getValue().toString());
                location_ticket.setText(snapshot.child("lokasi").getValue().toString());
                photo_spot_ticket.setText(snapshot.child("is_photo_spot").getValue().toString());
                wifi_ticket.setText(snapshot.child("is_wifi").getValue().toString());
                festival_ticket.setText(snapshot.child("is_festival").getValue().toString());
                short_desc_ticket.setText(snapshot.child("short_desc").getValue().toString());
                Picasso.get().load(snapshot.child("url_thumbnail")
                        .getValue().toString()).centerCrop().fit().into(header_ticket_detail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_buy_tiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetailTiketAct.this, CheckoutAct.class);
                intent.putExtra("jenis_ticket",jenis_tiket);
                startActivity(intent);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}