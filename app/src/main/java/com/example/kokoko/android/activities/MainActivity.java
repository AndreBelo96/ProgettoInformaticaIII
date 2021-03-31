package com.example.kokoko.android.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kokoko.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;

public class MainActivity extends Activity implements View.OnClickListener{

    private EditText etxtUsername, etxtPassword;
    private Button btnLogIn, btnRegister;

    // Write a message to the database
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        InitializeControls();
    }

    private void InitializeControls(){
        firebaseAuth = FirebaseAuth.getInstance();
        etxtUsername = (EditText)findViewById(R.id.etxtUsername);
        if(sharedPreferences.getString("eMail", "") != "" )
            etxtUsername.setText(sharedPreferences.getString("eMail", ""));

        etxtPassword = (EditText)findViewById(R.id.etxtPassword);
        btnLogIn = (Button)findViewById(R.id.btnLogin);
        btnLogIn.setOnClickListener(this);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLogin){
            Login();
        }

        if(v.getId() == R.id.btnRegister){
            openRegisterMenu();
        }
    }


    private void Login(){
        final String eMail = etxtUsername.getText().toString().trim();
        String password = etxtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(eMail)){ // se è vuota o nulla true
            Toast.makeText(this,"Please enter a eMail", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(eMail,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Successfully signed in", Toast.LENGTH_LONG).show();

                    /** Per le preferencies e salvare la mail e non riscriverla ogni volta che accediamo **/
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("eMail", eMail);
                    editor.commit();

                    openMenuGame();
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, "Sign in failed!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void openMenuGame(){
        Intent iChangeActivity = new Intent(this, GdxActivity.class);
        startActivity(iChangeActivity);
    }

    private void openRegisterMenu(){
        Intent iChangeActivity = new Intent(this, RegisterActivity.class);
        startActivity(iChangeActivity);
    }

}