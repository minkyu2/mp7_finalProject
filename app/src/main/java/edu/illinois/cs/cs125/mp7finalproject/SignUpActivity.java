package edu.illinois.cs.cs125.mp7finalproject;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.illinois.cs.cs125.mp7finalproject.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        findViewById(R.id.tvSignin).setOnClickListener(this);
        findViewById(R.id.SignupBtn).setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
    }

    private void registerUser() {
        final String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 7) {
            etPassword.setError("Minimum length should be 7");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()) {

                    String userID = mAuth.getCurrentUser().getUid();

                    DatabaseReference currentUserDB = mdatabase.child(userID);

                    currentUserDB.child("name").setValue(email);

                    Intent intent = new Intent(SignUpActivity.this, NikeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.SignupBtn:
                registerUser();
                break;

            case R.id.tvSignin:
                startActivity(new Intent(this, edu.illinois.cs.cs125.mp7finalproject.MainActivity.class));
                break;
        }
    }
}
