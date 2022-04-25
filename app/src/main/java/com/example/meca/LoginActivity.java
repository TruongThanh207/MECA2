package com.example.meca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView tvRegister;
    Button btnLogin;
    TextView tvEmail, tvPassword, tvForgotpass;

    FirebaseAuth mAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvRegister = findViewById(R.id.textSignIn);
        tvEmail = findViewById(R.id.editTextEmailAddress);
        tvPassword = findViewById(R.id.editTextPassword);
        tvForgotpass = findViewById(R.id.textForgotPassword);
        btnLogin = findViewById(R.id.buttonLogin);

        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = tvEmail.getText().toString();
                String txtPassword = tvPassword.getText().toString();
                if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(LoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(txtEmail , txtPassword);
                }
            }
        });
        tvForgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Forgot Password");
                showForgotPasswordDialog();
            }
        });

    }

    private void showForgotPasswordDialog() {
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_forgot_password,null);
        final EditText emailedt = view.findViewById(R.id.gmail_edt);
        final TextView tvback = view.findViewById(R.id.textBackLogin);
        Button btnRequestPassword = view.findViewById(R.id.RequestPassword);

        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setView(view);

        final AlertDialog dialog =builder.create();
        builder.create().show();

        tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                finish();
            }
        });
        btnRequestPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailedt.getText().toString().trim();
                if (TextUtils.isEmpty(emailAddress)){
                    Toast.makeText(LoginActivity.this,"Nhập địa chỉ gmail ",Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
                requestPassword(emailAddress);
            }
        });
    }

    private void requestPassword(String emailAddress) {
        pd.show();
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this,"Email sent",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginUser(String txtEmail, String txtPassword) {
        pd.setMessage("Please Wail!");
        pd.show();
        mAuth.signInWithEmailAndPassword(txtEmail, txtPassword)
        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //Toast.makeText(LoginActivity.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this,"Mật khẩu không đúng",Toast.LENGTH_SHORT).show();
            }
        });
    }
}