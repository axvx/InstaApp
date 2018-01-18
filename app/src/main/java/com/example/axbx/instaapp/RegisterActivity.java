package com.example.axbx.instaapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameField;
    private  EditText emailField;
    private  EditText passField;
    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        nameField=(EditText)findViewById(R.id.nameField);
        passField=(EditText)findViewById(R.id.passField);
        emailField=(EditText)findViewById(R.id.emailField);

        mAuth=FirebaseAuth.getInstance();
        //Registrar datos de los usuarios
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");



    }

    public void registerButtonClicked(View view){
        final String name=nameField.getText().toString().trim();
         final String email=emailField.getText().toString().trim();
         final String pass=passField.getText().toString().trim();


        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){

            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override

                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {



                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabase.child(user_id);
                        current_user_db.child("Name").setValue(name);
                        current_user_db.child("image").setValue("default");

                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);

                    }

                    else {
                        // If sign in fails, display a message to the user.
                        Log.w("ERROR_AUTH", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

    }

}
