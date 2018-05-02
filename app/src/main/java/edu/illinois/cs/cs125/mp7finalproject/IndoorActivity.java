package edu.illinois.cs.cs125.mp7finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import edu.illinois.cs.cs125.mp7finalproject.R;

public class IndoorActivity extends AppCompatActivity {

    Button likeButton, likeButton2, googleBtn1, googleBtn2;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseLike;
    private FirebaseUser mCurrentUser;
    private boolean mLike = false;
    private boolean mLike2 = false;
    Intent toThePage, toThePage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor);

        likeButton = (Button) findViewById(R.id.likeBtn1);
        likeButton2 = (Button) findViewById(R.id.likeBtn2);
        googleBtn1 = (Button) findViewById(R.id.googleBtn1);
        googleBtn2 = (Button) findViewById(R.id.googleBtn2);
        toThePage = new Intent(Intent.ACTION_VIEW);
        toThePage2 = new Intent(Intent.ACTION_VIEW);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseLike = FirebaseDatabase.getInstance().getReference();

        googleBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toThePage.setData(Uri.parse("https://www.nike.com/t/mercurialx-superfly-vi-academy-cr7-ic-indoor-court-soccer-shoe-Zv3wDd"));
                startActivity(toThePage);
            }
        });

        googleBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toThePage2.setData(Uri.parse("https://store.nike.com/us/en_us/pd/mercurialx-vapor-xii-pro-indoor-court-soccer-shoe/pid-11996601/pgid-12143417"));
                startActivity(toThePage2);
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                mLike = true;

                mDatabaseLike.addValueEventListener(new ValueEventListener() {;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(mLike) {

                            if (dataSnapshot.child("CR7 IC").hasChild(mCurrentUser.getUid())) {
                                mDatabaseLike.child("CR7 IC").child(mCurrentUser.getUid()).removeValue();
                                likeButton.setBackgroundColor(Color.GRAY);
                                Toast.makeText(IndoorActivity.this,"You unliked it!", Toast.LENGTH_LONG).show();
                                mLike = false;

                            } else {
                                mDatabaseLike.child("CR7 IC").child(mCurrentUser.getUid()).setValue("increment");
                                likeButton.setBackgroundColor(Color.YELLOW);
                                Toast.makeText(IndoorActivity.this,"You liked it!", Toast.LENGTH_LONG).show();
                                mLike = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        likeButton2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                mLike2 = true;

                mDatabaseLike.addValueEventListener(new ValueEventListener() {;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(mLike2) {

                            if (dataSnapshot.child("Pro IC").hasChild(mCurrentUser.getUid())) {
                                mDatabaseLike.child("Pro IC").child(mCurrentUser.getUid()).removeValue();
                                likeButton2.setBackgroundColor(Color.GRAY);
                                Toast.makeText(IndoorActivity.this,"You unliked it!", Toast.LENGTH_LONG).show();
                                mLike2 = false;

                            } else {
                                mDatabaseLike.child("Pro IC").child(mCurrentUser.getUid()).setValue("increment");
                                likeButton2.setBackgroundColor(Color.YELLOW);
                                Toast.makeText(IndoorActivity.this,"You liked it!", Toast.LENGTH_LONG).show();
                                mLike2 = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
