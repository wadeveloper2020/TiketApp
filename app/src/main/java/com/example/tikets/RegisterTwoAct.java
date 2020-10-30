package com.example.tikets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwoAct extends AppCompatActivity {

    LinearLayout back;
    Button btn_register_two, btn_add_photo;
    ImageView icon_nopic;
    EditText nama_lengkap, bio;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        getUserNameLokal();

        back = findViewById(R.id.back);
        btn_register_two = findViewById(R.id.btn_register_two);
        btn_add_photo = findViewById(R.id.btn_add_photo);
        icon_nopic = findViewById(R.id.pic_photo_register_user);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio  = findViewById(R.id.bio);

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findPhoto();
            }
        });

        btn_register_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_register_two.setEnabled(true);
                btn_register_two.setText("Loading...");

                reference = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(username_key_new);

                storage = FirebaseStorage.getInstance().getReference()
                        .child("PhotoUsers").child(username_key_new);

                if (photo_location != null){
                    StorageReference storageReference = storage.child(System.currentTimeMillis()
                            +"."+ getFileExtention(photo_location));

                    storageReference.putFile(photo_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask=taskSnapshot.getMetadata().getReference().getDownloadUrl();                                               while(!uriTask.isSuccessful());
                            Uri downloadUri=uriTask.getResult();
                            final String download_url=downloadUri.toString();

                            //String url_photo = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            reference.getRef().child("url_photo_profile").setValue(download_url);
                            reference.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString());
                            reference.getRef().child("bio").setValue(bio.getText().toString());
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent intent = new Intent(RegisterTwoAct.this, SuccessRegisterAct.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterTwoAct.this, RegisterOneAct.class);
                startActivity(intent);
            }
        });

    }

    //untuk menyimpan photo ke firebase dalam bentuk url
    String getFileExtention(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic,photo_max );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==photo_max && resultCode == RESULT_OK && data!= null && data.getData() != null)
        {
            photo_location = data.getData();
            Picasso.get().load(photo_location).centerCrop().fit().into(icon_nopic);
        }
    }
    public void getUserNameLokal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}