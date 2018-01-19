package com.example.axbx.instaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class SingleInstaActivity extends AppCompatActivity {

    private String post_key=null;
    private DatabaseReference mDatabase;
    private ImageView singlePostImage;
    private TextView siglePostTitle;
    private TextView singlePostDesc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_insta);

        String post_key=getIntent().getExtras().getString("Postid");
        mDatabase= FirebaseDatabase.getInstance().getReference().child("InstaApp");
        siglePostTitle=(TextView)findViewById(R.id.singleTitle);
        singlePostDesc=(TextView)findViewById(R.id.singleDesc);
        singlePostImage=(ImageView)findViewById(R.id.singleImageView);

        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //datos que se pueden mostrar
                String post_title=(String) dataSnapshot.child("tittle").getValue();
                String post_desc=(String) dataSnapshot.child("desc").getValue();
                String post_image=(String) dataSnapshot.child("image").getValue();
                String post_uid=(String) dataSnapshot.child("uid").getValue();

                siglePostTitle.setText(post_title);
                singlePostDesc.setText(post_desc);
                Picasso.with(SingleInstaActivity.this).load(post_image).into(singlePostImage);            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
