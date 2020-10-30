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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileAct extends AppCompatActivity {

    EditText xnama_lengkap, xbio, xusername, xpassword, xemail_address;

    Button btn_save;
    LinearLayout btn_back;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getUserNameLokal();

        xnama_lengkap = findViewById(R.id.xnama_lengkap);
        xbio = findViewById(R.id.xbio);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        xemail_address = findViewById(R.id.xemail_address);

        btn_save = findViewById(R.id.btn_save);
        btn_back = findViewById(R.id.back_myprofile);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                xnama_lengkap.setText(snapshot.child("nama_lengkap").getValue().toString());
                xbio.setText(snapshot.child("bio").getValue().toString());
                xusername.setText(snapshot.child("username").getValue().toString());
                xpassword.setText(snapshot.child("password").getValue().toString());
                xemail_address.setText(snapshot.child("email_address").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference.getRef().child("nama_lengkap").setValue(xnama_lengkap.getText().toString());
                        reference.getRef().child("bio").setValue(xbio.getText().toString());
                        reference.getRef().child("username").setValue(xusername.getText().toString());
                        reference.getRef().child("password").setValue(xpassword.getText().toString());
                        reference.getRef().child("email_address").setValue(xemail_address.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(EditProfileAct.this, MyProfileAct.class);
                startActivity(intent);
            }
        });



    }
    public void getUserNameLokal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}