package com.kelasp1.latihanfirebase01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnRegister, btnResetPassword;
    FirebaseAuth mAuth;
    // Definisikan GoogleSignInClient dan RC_SIGN_IN di level class agar bisa diakses method lain
    GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        mAuth = FirebaseAuth.getInstance();
        // 1. Fitur Login
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Login Gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        // 2. Fitur Register
        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi untuk daftar", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Gagal Register: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        // 3. Fitur Reset Password
        btnResetPassword.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(this, "Masukkan email terlebih dahulu untuk reset password", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Instruksi reset password telah dikirim ke email Anda", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Gagal mengirim email reset: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        // 4. Konfigurasi Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("559866345872-inj5e9pa8pauv78nji8dis8mjakib3a8.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.btnGoogleSignIn).setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }
    // Tambahkan method onActivityResult untuk menangkap hasil pilihan akun Google
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Toast.makeText(this, "Google Sign-In gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Method untuk menghubungkan token Google ke Firebase Auth
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Login Google Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Firebase Auth Google Gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    // Fitur Remember Me (Auto Login jika sudah pernah masuk)
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}