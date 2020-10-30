package com.example.tikets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class CheckoutAct extends AppCompatActivity {

    Button btn_pay_now;
    ImageView btnminus, btnplus, notis;
    TextView textjumlahtiket, textmybalace, texttotalharga, nama_wisata, lokasi, ketentuan;
    LinearLayout btn_back;
    Integer valuejumlahtiket = 1;
    Integer valuetextmybalance = 0;
    Integer hargatiket = 75;
    Integer valuetexttotalharga = 0;
    Integer sisa_balance = 0;

    Integer nomor_transaksi = new Random().nextInt();

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata = "";
    String time_wisata = "";

    DatabaseReference reference, reference2, reference3, reference4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_ticket");

        getUserNameLokal();

        btn_pay_now = findViewById(R.id.btn_pay_now);
        btnminus = findViewById(R.id.btnminus);
        btnplus = findViewById(R.id.btnplus);
        textjumlahtiket = findViewById(R.id.textjumlahtiket);
        textmybalace = findViewById(R.id.textmybalance);
        texttotalharga = findViewById(R.id.texttotalharga);
        notis = findViewById(R.id.notis);
        btn_back = findViewById(R.id.back);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        textjumlahtiket.setText(valuejumlahtiket.toString());

        btnminus.animate().alpha(0).setDuration(300).start();
        btnminus.setEnabled(false);
        notis.setVisibility(View.GONE);

        reference2 = FirebaseDatabase.getInstance()
                .getReference().child("Users")
                .child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                valuetextmybalance = Integer.valueOf(snapshot.child("user_balance").getValue().toString());
                textmybalace.setText("US$ "+valuetextmybalance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nama_wisata.setText(snapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(snapshot.child("lokasi").getValue().toString());
                ketentuan.setText(snapshot.child("ketentuan").getValue().toString());
                date_wisata = snapshot.child("date_wisata").getValue().toString();
                time_wisata = snapshot.child("time_wisata").getValue().toString();
                hargatiket = Integer.valueOf(snapshot.child("harga_tiket").getValue().toString());

                valuetexttotalharga = valuejumlahtiket * hargatiket;
                texttotalharga.setText("US$ "+valuetexttotalharga+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuejumlahtiket +=1;
                textjumlahtiket.setText(valuejumlahtiket.toString());
                if (valuejumlahtiket > 1){
                    btnminus.animate().alpha(1).setDuration(300).start();
                    btnminus.setEnabled(true);
                }
                valuetexttotalharga = valuejumlahtiket * hargatiket;
                texttotalharga.setText("US$ "+valuetexttotalharga+"");
                if (valuetexttotalharga > valuetextmybalance){
                    btn_pay_now.animate().translationY(300).alpha(0).setDuration(400).start();
                    btn_pay_now.setEnabled(false);
                    textmybalace.setTextColor(Color.parseColor("#D1206B"));
                }
            }
        });

        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuejumlahtiket -=1;
                textjumlahtiket.setText(valuejumlahtiket.toString());
                if (valuejumlahtiket < 2){
                    btnminus.animate().alpha(0).setDuration(250).start();
                    btnminus.setEnabled(false);

                }
                valuetexttotalharga = valuejumlahtiket * hargatiket;
                texttotalharga.setText("US$ "+valuetexttotalharga+"");
                if (valuetexttotalharga < valuetextmybalance){
                    btn_pay_now.animate().translationY(0).alpha(1).setDuration(400).start();
                    btn_pay_now.setEnabled(true);
                    textmybalace.setTextColor(Color.parseColor("#203DD1"));
                }
            }

        });

        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference3 = FirebaseDatabase.getInstance()
                        .getReference().child("MyTicket")
                        .child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference3.getRef().child("id_tiket_wisata").setValue(nama_wisata.getText().toString()+ nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);
                        reference3.getRef().child("jumlah_tiket").setValue(valuejumlahtiket.toString());

                        Intent intent = new Intent(CheckoutAct.this, SuccessTiketAct.class);
                        intent.putExtra("jenis_ticket","myticket");
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                reference4 = FirebaseDatabase.getInstance()
                        .getReference().child("Users")
                        .child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sisa_balance = valuetextmybalance - valuetexttotalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void getUserNameLokal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}