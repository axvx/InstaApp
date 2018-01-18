package com.example.axbx.instaapp;
//Permite autenticarse
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST=2;
    public static final int PICK_IMAGE = 1;

    private Uri uri=null;
    private ImageButton imageButton;
    private EditText editName;
    private EditText editDec;
    private StorageReference storageReference;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        editName=(EditText)findViewById(R.id.editName);
        editDec=(EditText)findViewById(R.id.editDesc);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference=database.getInstance().getReference().child("InstaApp");

    }
    public void imageButtonClicked(View view){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);


//        Intent galleryintent=new Intent(Intent.ACTION_GET_CONTENT);
  //      galleryintent.setType("Image/*");
    //    startActivityForResult(galleryintent,GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {

            uri = data.getData();
            Toast.makeText(getBaseContext().getApplicationContext(),uri.toString(),Toast.LENGTH_LONG).show();
            imageButton = (ImageButton) findViewById(R.id.imageButton);
            imageButton.setImageURI(uri);

        }

    }



    public void submitButtonClicked(View view){

        final String titleValue=editName.getText().toString().trim();
        final String titleDec=editDec.getText().toString().trim();

        if(!TextUtils.isEmpty(titleValue)&& !TextUtils.isEmpty(titleDec)){

            StorageReference filePath= storageReference.child("PostImage").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadurl=taskSnapshot.getDownloadUrl();
                    Toast.makeText(PostActivity.this,"Upload complete",Toast.LENGTH_LONG).show();
                    DatabaseReference newPost=databaseReference.push();
                    newPost.child("tittle").setValue(titleValue);
                    newPost.child("desc").setValue(titleDec);
                    newPost.child("image").setValue(downloadurl.toString());

                }
            });



        }

    }
}
