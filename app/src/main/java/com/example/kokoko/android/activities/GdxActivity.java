package com.example.kokoko.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.example.kokoko.Constant;
import com.example.kokoko.R;
import com.example.kokoko.libgdx.GameClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/** Classe activity di Gdx */
public class GdxActivity extends AndroidApplication {

    //static variables
    private static final String TAG = "GdxActivity";
    private static Intent iChangeActivity;

    //public variables
    public GameClass gdxController;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private int puntiAttuali;
    private int[] punti;
    private String[] nomi;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdx);

        //declare the database reference obj
        mAuth = FirebaseAuth.getInstance(); // for users mail, pass
        mFirebaseDatabase = FirebaseDatabase.getInstance(); // for using the real time database of firebase
        myRef = mFirebaseDatabase.getReference();

        //declare a current user to know who is the user of the actual app
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        final String userUid = currentUser.getUid();

        final ValueEventListener scoreListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                punti = new int[(int) snapshot.getChildrenCount()];
                nomi = new String[(int) snapshot.getChildrenCount()];
                name = snapshot.child(userUid).child(Constant.DATA).child(Constant.NAME).getValue(String.class);
                int count = 0;
                for (DataSnapshot i : snapshot.getChildren()) {
                    punti[count] = i.child(Constant.DATA).child(Constant.TOTAL_POINTS).getValue(Integer.class);
                    nomi[count] = i.child(Constant.DATA).child(Constant.NAME).getValue(String.class);
                    count++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled ", error.toException());
            }
        };

        myRef.addValueEventListener(scoreListener);

        //controller per la GDX
        gdxController = new GameClass(getAssets(), this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        final View gdxView = initializeForView(gdxController, new AndroidApplicationConfiguration());
        final FrameLayout container = findViewById(R.id.container_gdx);
        container.addView(gdxView);

    }

    public void openLogIn() {
        iChangeActivity = new Intent(GdxActivity.this, MainActivity.class);
        startActivity(iChangeActivity);
    }

    public void updateUserInterface(int lvlComplete, int points, String nickName) {
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        final String userUid = currentUser.getUid();
        myRef.child(userUid).child(Constant.DATA).child("Complete Level").setValue(lvlComplete);
        myRef.child(userUid).child(Constant.DATA).child(Constant.TOTAL_POINTS).setValue(points);
        myRef.child(userUid).child(Constant.DATA).child(Constant.NAME).setValue(nickName);
    }


    public String[] getNomi() {
        return nomi;
    }

    public int[] getPunti() {
        return punti;
    }

    public String getName() {
        return name;
    }
}
