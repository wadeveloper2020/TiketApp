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

public class SuccessRegisterAct extends AppCompatActivity {

    Button btn_success_register;
    ImageView app_icon;
    TextView app_title, app_subtitle;
    Animation btt, ttb, app_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        btn_success_register = findViewById(R.id.btn_success_register);
        app_icon = findViewById(R.id.app_icon);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);

        app_icon.startAnimation(app_splash);
        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(btt);
        btn_success_register.startAnimation(btt);

        btn_success_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessRegisterAct.this, HomeScreenAct.class);
                startActivity(intent);
            }
        });
    }
}