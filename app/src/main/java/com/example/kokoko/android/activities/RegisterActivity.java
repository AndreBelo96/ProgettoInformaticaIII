package com.example.kokoko.android.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kokoko.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity  extends AppCompatActivity implements View.OnClickListener {
    private EditText etxtUsername, etxtPassword, etxtConfirmPass;
    private Button btnRegister;
    // Write a message to the database
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitializeControls();
    }

    private void InitializeControls(){
        firebaseAuth = FirebaseAuth.getInstance();
        etxtUsername = (EditText)findViewById(R.id.etxtUsername);
        etxtPassword = (EditText)findViewById(R.id.etxtPassword);
        etxtConfirmPass = (EditText)findViewById(R.id.etxtConfirmPassword);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnRegister){
            Register();
        }
    }

    private void Register(){
        String eMail = etxtUsername.getText().toString().trim();
        String password = etxtPassword.getText().toString().trim();
        String confirmPassword = etxtConfirmPass.getText().toString().trim();

        if(TextUtils.isEmpty(eMail)){ // se è vuota o nulla true
            Toast.makeText(this,"Please enter a eMail", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(this,"Please confirm your password", Toast.LENGTH_SHORT).show();
            return;
        }else if(password.length() < 6){
            Toast.makeText(this,"Password too short", Toast.LENGTH_SHORT).show();
        }else if(!password.equals(confirmPassword)){
            Toast.makeText(this,"Your passwords don't match", Toast.LENGTH_SHORT).show();
        }else if(!isValidEmail(eMail)){
            Toast.makeText(this,"Invalid E-mail", Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(eMail,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                    updateUI();
                    openMenuGame();
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Sign up failed!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private Boolean isValidEmail(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()); // serve per validare l'email <username>@<mail-server>.<mail-servertype or server-location>
    }


    public void openMenuGame(){
        Intent iChangeActivity = new Intent(this, GdxActivity.class);
        startActivity(iChangeActivity);
    }

    public void updateUI(){
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String userUid = currentUser.getUid();
        myRef.child(userUid).child("Data").child("Complete Level").setValue(0);
        myRef.child(userUid).child("Data").child("Total Points").setValue(0);
        myRef.child(userUid).child("Data").child("Name").setValue("Guest");

    }

}