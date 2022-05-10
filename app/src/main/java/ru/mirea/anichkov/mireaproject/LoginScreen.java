package ru.mirea.anichkov.mireaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText passwordEditText, loginEditText;
    private TextView statusTextView, detailTextView;
    private Button btnSignIn, btnCreateAcc;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        passwordEditText = findViewById(R.id.PasswordEditText);
        loginEditText = findViewById(R.id.LoginEditText);

        btnCreateAcc = findViewById(R.id.btnCreateAccount);
        btnSignIn = findViewById(R.id.btnSignIn);

        statusTextView = findViewById(R.id.StatusTextView);
        detailTextView = findViewById(R.id.DetailTextView);

        FirebaseApp.initializeApp(this);
        try {
            mAuth = FirebaseAuth.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MainActivity.loginScreen = this;


    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            statusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            detailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            findViewById(R.id.Buttons).setVisibility(View.GONE);
            findViewById(R.id.EditTexts).setVisibility(View.GONE);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            statusTextView.setText(R.string.signed_out);
            detailTextView.setText(null);
            findViewById(R.id.Buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.EditTexts).setVisibility(View.VISIBLE);
        }
    }
    private boolean validateForm() {
        boolean valid = true;
        String email = loginEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            loginEditText.setError("Required.");
            valid = false;
        } else {
            loginEditText.setError(null);
        }
        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Required.");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }
        return valid;
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        if (!task.isSuccessful()) {
                            statusTextView.setText(R.string.auth_failed);
                        }
                    }
                });
    }

    public void signOut(View view) {
        mAuth.signOut();
        updateUI(null);
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnCreateAccount) {
            createAccount(loginEditText.getText().toString(), passwordEditText.getText().toString());
        } else if (i == R.id.btnSignIn) {
            signIn(loginEditText.getText().toString(), passwordEditText.getText().toString());
//
        }
    }

}