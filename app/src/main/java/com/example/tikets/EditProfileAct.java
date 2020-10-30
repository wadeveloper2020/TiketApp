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

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfileAct extends AppCompatActivity {

    EditText xnama_lengkap, xbio, xusername, xpassword, xemail_address;
    ImageView photo_edit_profile;
    Button btn_save, btn_add_photo_new;
    LinearLayout btn_back;

    DatabaseReference reference;
    StorageReference storage;

    Uri photo_location;
    Integer photo_max = 1;

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
        photo_edit_profile = findViewById(R.id.photo_edit_profile);

        btn_save = findViewById(R.id.btn_save);
        btn_add_photo_new = findViewById(R.id.btn_add_photo_new);
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
                Picasso.get().load(snapshot.child("url_photo_profile")
                        .getValue().toString()).centerCrop().fit().into(photo_edit_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_save.setEnabled(true);
                btn_save.setText("Loading...");

                reference.addValueEventListener(new ValueEventListener() {
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

                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent intent = new Intent(EditProfileAct.this, MyProfileAct.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        btn_add_photo_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });
    }

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
            Picasso.get().load(photo_location).centerCrop().fit().into(photo_edit_profile);
        }
    }

    public void getUserNameLokal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}