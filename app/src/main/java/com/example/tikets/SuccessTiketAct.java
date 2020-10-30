package com.example.tikets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessTiketAct extends AppCompatActivity {

    Button btn_view_tiket, btn_tomydashboard;
    ImageView app_icon;
    TextView app_title, app_subtitle;
    Animation btt, ttb, app_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_tiket);

        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        btn_view_tiket = findViewById(R.id.btn_view_tikets);
        btn_tomydashboard = findViewById(R.id.btn_mydashboard);
        app_icon = findViewById(R.id.app_icon);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);

        app_icon.startAnimation(app_splash);
        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(btt);
        btn_view_tiket.startAnimation(btt);
        btn_tomydashboard.startAnimation(btt);

        btn_view_tiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessTiketAct.this, HomeScreenAct.class);
                startActivity(intent);
            }
        });

        btn_tomydashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessTiketAct.this, HomeScreenAct.class);
                startActivity(intent);
            }
        });
    }
}