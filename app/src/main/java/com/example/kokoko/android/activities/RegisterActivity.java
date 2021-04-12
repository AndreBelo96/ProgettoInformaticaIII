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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kokoko.Constant;
import com.example.kokoko.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/** Classe activity per la registrazione */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etxtUsername;
    private EditText etxtPassword;
    private EditText etxtConfirmPass;
    private Button btnRegister;
    // Write a message to the database
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeControls();
    }

    private void initializeControls() {
        firebaseAuth = FirebaseAuth.getInstance();
        etxtUsername = (EditText) findViewById(R.id.etxtUsername);
        etxtPassword = (EditText) findViewById(R.id.etxtPassword);
        etxtConfirmPass = (EditText) findViewById(R.id.etxtConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {
            register();
        }
    }

    private void register() {
        final String eMail = etxtUsername.getText().toString().trim();
        final String password = etxtPassword.getText().toString().trim();
        final String confirmPassword = etxtConfirmPass.getText().toString().trim();

        if (TextUtils.isEmpty(eMail)) { // se è vuota o nulla true
            Toast.makeText(this, "Please enter a eMail", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
        } else if (password.length() < Constant.MIN_PASS_CHAR) {
            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Your passwords don't match", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(eMail)) {
            Toast.makeText(this, "Invalid E-mail", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            firebaseAuth.createUserWithEmailAndPassword(eMail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                        updateUserInterface();
                        openMenuGame();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Sign up failed!", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }

    private Boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches(); // serve per validare l'email <username>@<mail-server>.<mail-servertype or server-location>
    }


    private void openMenuGame() {
        final Intent iChangeActivity = new Intent(this, GdxActivity.class);
        startActivity(iChangeActivity);
    }

    public void updateUserInterface() {
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        final String userUid = currentUser.getUid();
        myRef.child(userUid).child(Constant.DATA).child("Complete Level").setValue(0);
        myRef.child(userUid).child(Constant.DATA).child(Constant.TOTAL_POINTS).setValue(0);
        myRef.child(userUid).child(Constant.DATA).child(Constant.NAME).setValue("Guest");

    }

}