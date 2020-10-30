package com.example.tikets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAct extends AppCompatActivity {

    TextView create_new_account;
    EditText xusername, xpassword;
    Button btn_sign_in;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    DatabaseReference reference;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        create_new_account = findViewById(R.id.create_new_account);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        btn_sign_in = findViewById(R.id.btn_sign_in);



        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Loading...");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if (username.isEmpty()){
                    Toast.makeText(SignInAct.this, "Username Kosong...!", Toast.LENGTH_SHORT).show();
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                }else if (password.isEmpty()){
                    Toast.makeText(SignInAct.this, "Password kosong...!", Toast.LENGTH_SHORT).show();
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                }else {
                    reference = FirebaseDatabase.getInstance()
                            .getReference().child("Users")
                            .child(username);

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                            {
                                String passwordFromFirebase = snapshot.child("password").getValue().toString();

                                if (password.equals(passwordFromFirebase)){

                                    Intent intent = new Intent(SignInAct.this, HomeScreenAct.class);
                                    startActivity(intent);

                                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(username_key,xusername.getText().toString());
                                    editor.apply();

                                }else
                                    Toast.makeText(SignInAct.this, "Password salah..!", Toast.LENGTH_SHORT).show();
                                btn_sign_in.setEnabled(true);
                                btn_sign_in.setText("SIGN IN");
                            }else
                                Toast.makeText(SignInAct.this, "User tidak tersedia..", Toast.LENGTH_SHORT).show();
                            btn_sign_in.setEnabled(true);
                            btn_sign_in.setText("SIGN IN");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }



            }
        });

        create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInAct.this, RegisterOneAct.class);
                startActivity(intent);
            }
        });
    }
}