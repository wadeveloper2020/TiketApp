package com.example.tikets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {

    Button btn_continue;
    LinearLayout btn_back;
    EditText username, password, email_address;

    String USERNAME_KEY = "usernamekey";
    String username_key ="";

    DatabaseReference reference,reference_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        btn_continue = findViewById(R.id.btn_continue);
        btn_back = findViewById(R.id.back);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);



        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_continue.setEnabled(false);
                btn_continue.setText("Loading...");

                reference_username = FirebaseDatabase.getInstance()
                        .getReference().child("Users")
                        .child(username.getText().toString());

                reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){

                            Toast.makeText(RegisterOneAct.this, "Username sudah terdaftar!", Toast.LENGTH_SHORT).show();

                            btn_continue.setEnabled(true);
                            btn_continue.setText("CONTINUE");

                        }else {
                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(username_key, username.getText().toString());
                            editor.apply();

                            reference = FirebaseDatabase.getInstance().getReference()
                                    .child("Users").child(username.getText().toString());
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    snapshot.getRef().child("username").setValue(username.getText().toString());
                                    snapshot.getRef().child("password").setValue(password.getText().toString());
                                    snapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                                    snapshot.getRef().child("user_balance").setValue(800);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Intent intent = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}