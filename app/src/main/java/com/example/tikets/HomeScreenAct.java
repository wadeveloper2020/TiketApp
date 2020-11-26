package com.example.tikets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeScreenAct extends AppCompatActivity {

    LinearLayout btn_ticket_cangkuang, btn_ticket_papandayan, btn_ticket_darajat,
                 btn_ticket_bagendit, btn_ticket_sanghyang, btn_ticket_santolo;

    CircleView to_profile;
    ImageView photo_home_user;
//    WebView img_cangkuang, img_papandayan;
//            //img_darajat, img_bagendit, img_sanghyang, img_santolo;

    WebView webView;

    TextView nama_lengkap, bio, user_balance;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        getUserNameLokal();

        btn_ticket_cangkuang =  findViewById(R.id.btn_ticket_cangkuang);
        btn_ticket_papandayan =  findViewById(R.id.btn_ticket_papandayan);
        btn_ticket_darajat =  findViewById(R.id.btn_ticket_darajat);
        btn_ticket_bagendit =  findViewById(R.id.btn_ticket_bagendit);
        btn_ticket_sanghyang =  findViewById(R.id.btn_ticket_sanghyang);
        btn_ticket_santolo =  findViewById(R.id.btn_ticket_santolo);

//        img_cangkuang = findViewById(R.id.img_cangkuang);
//        img_papandayan = findViewById(R.id.img_papandayan);
//        img_darajat = findViewById(R.id.img_darajat);
//        img_bagendit = findViewById(R.id.img_bagendit);
//        img_sanghyang = findViewById(R.id.img_sanghyang);
//        img_santolo = findViewById(R.id.img_santolo);
//        webView = findViewById(R.id.webview);

        to_profile = findViewById(R.id.to_profile);
        photo_home_user = findViewById(R.id.photo_home_user);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        user_balance = findViewById(R.id.user_balance);

        //mengambil data dari firebase
        reference = FirebaseDatabase.getInstance()
                .getReference().child("Users")
                .child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nama_lengkap.setText(snapshot.child("nama_lengkap").getValue().toString());
                bio.setText(snapshot.child("bio").getValue().toString());
                user_balance.setText("US$ "+snapshot.child("user_balance").getValue().toString());
                Picasso.get().load(snapshot.child("url_photo_profile")
                        .getValue().toString()).centerCrop().fit().into(photo_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btn_ticket_cangkuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //mengirim data dengan intent
                Intent intent = new Intent(HomeScreenAct.this, DetailTiketsAct.class);
                intent.putExtra("jenis_ticket","Cangkuang");
                startActivity(intent);
            }
        });

        btn_ticket_papandayan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeScreenAct.this, DetailTiketsAct.class);
                intent.putExtra("jenis_ticket","Papandayan");
                startActivity(intent);
            }
        });

        btn_ticket_darajat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeScreenAct.this, DetailTiketsAct.class);
                intent.putExtra("jenis_ticket","Darajat");
                startActivity(intent);
            }
        });

        btn_ticket_bagendit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeScreenAct.this, DetailTiketsAct.class);
                intent.putExtra("jenis_ticket","Bagendit");
                startActivity(intent);
            }
        });

        btn_ticket_sanghyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeScreenAct.this, DetailTiketsAct.class);
                intent.putExtra("jenis_ticket","Sanghyang");
                startActivity(intent);
            }
        });

        btn_ticket_santolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeScreenAct.this, DetailTiketsAct.class);
                intent.putExtra("jenis_ticket","Santolo");
                startActivity(intent);
            }
        });

        to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenAct.this, MyProfileAct.class);
                startActivity(intent);
            }
        });

    }
    public void getUserNameLokal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

}