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

public class GetStartedAct extends AppCompatActivity {

    Button btn_sign_in, btn_new_account;
    TextView app_tag_line;
    ImageView app_logo;
    Animation btt,ttb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_new_account = findViewById(R.id.btn_new_account);
        app_tag_line = findViewById(R.id.app_tag_line);
        app_logo = findViewById(R.id.app_logo);

        btn_sign_in.startAnimation(btt);
        btn_new_account.startAnimation(btt);
        app_logo.startAnimation(ttb);
        app_tag_line.startAnimation(ttb);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetStartedAct.this, SignInAct.class);
                startActivity(intent);
            }
        });

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetStartedAct.this, RegisterOneAct.class);
                startActivity(intent);
            }
        });
    }
}