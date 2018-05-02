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

public class OutdoorActivity extends AppCompatActivity {

    Button likeButton3, likeButton4, googleBtn3, googleBtn4;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseLike;
    private FirebaseUser mCurrentUser;
    private boolean mLike3 = false;
    private boolean mLike4 = false;
    Intent tothePage3, tothePage4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);

        likeButton3 = (Button) findViewById(R.id.likeBtn3);
        likeButton4 = (Button) findViewById(R.id.likeBtn4);
        googleBtn3 = (Button) findViewById(R.id.googleBtn3);
        googleBtn4 = (Button) findViewById(R.id.googleBtn4);
        tothePage3 = new Intent(Intent.ACTION_VIEW);
        tothePage4 = new Intent(Intent.ACTION_VIEW);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseLike = FirebaseDatabase.getInstance().getReference();

        googleBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tothePage3.setData(Uri.parse("https://www.nike.com/t/mercurial-superfly-360-elite-sg-pro-anti-clog-soft-ground-soccer-cleat-X1AxrW"));
                startActivity(tothePage3);
            }
        });

        googleBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tothePage4.setData(Uri.parse("https://www.nike.com/t/magista-obra-ii-elite-dynamic-fit-sg-pro-soft-ground-soccer-cleat-E38kxY"));
                startActivity(tothePage4);
            }
        });

        likeButton3.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                mLike3 = true;

                mDatabaseLike.addValueEventListener(new ValueEventListener() {;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(mLike3) {

                            if (dataSnapshot.child("Elite").hasChild(mCurrentUser.getUid())) {
                                mDatabaseLike.child("Elite").child(mCurrentUser.getUid()).removeValue();
                                likeButton3.setBackgroundColor(Color.GRAY);
                                Toast.makeText(OutdoorActivity.this,"You unliked it!", Toast.LENGTH_LONG).show();
                                mLike3 = false;

                            } else {
                                mDatabaseLike.child("Elite").child(mCurrentUser.getUid()).setValue("increment");
                                likeButton3.setBackgroundColor(Color.YELLOW);
                                Toast.makeText(OutdoorActivity.this,"You liked it!", Toast.LENGTH_LONG).show();
                                mLike3 = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        likeButton4.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                mLike4 = true;

                mDatabaseLike.addValueEventListener(new ValueEventListener() {;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(mLike4) {

                            if (dataSnapshot.child("Dynamic").hasChild(mCurrentUser.getUid())) {
                                mDatabaseLike.child("Dynamic").child(mCurrentUser.getUid()).removeValue();
                                likeButton4.setBackgroundColor(Color.GRAY);
                                Toast.makeText(OutdoorActivity.this,"You unliked it!", Toast.LENGTH_LONG).show();
                                mLike4 = false;

                            } else {
                                mDatabaseLike.child("Dynamic").child(mCurrentUser.getUid()).setValue("increment");
                                likeButton4.setBackgroundColor(Color.YELLOW);
                                Toast.makeText(OutdoorActivity.this,"You liked it!", Toast.LENGTH_LONG).show();
                                mLike4 = false;
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
