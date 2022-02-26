package com.example.meca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText tvEmail, tvPassword;
    TextView tvLogin;

    FirebaseAuth mAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        anhxa();
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
////                String txtUsername = tvEmail.getText().toString();
////                String txtName = tvPassword.getText().toString();
//                String txtEmail = tvEmail.getText().toString();
//                String txtPassword = tvPassword.getText().toString();
//
//                if (/*TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtName) || */TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
//                    Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
//                } else if (txtPassword.length() < 6){
//                    Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
//                } else {
//                    registerUser(/*txtUsername , txtName , */txtEmail , txtPassword);
//                }
            }
        });

    }
    private void anhxa(){
        tvLogin = (TextView) findViewById(R.id.textLogin);
        tvEmail = (EditText) findViewById(R.id.editTextEmailRegister);
        tvPassword = (EditText) findViewById(R.id.editTextPasswordRegister);
        btnRegister = (Button) findViewById(R.id.buttonRegister);
    }
    private void registerUser() {
        pd.setMessage("Please Wail!");
        pd.show();
//        mAuth.createUserWithEmailAndPassword(txtEmail , txtPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isComplete()){
////                    userID = mAuth.getCurrentUser().getUid();
//                    pd.dismiss();
//                    Toast.makeText(RegisterActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                    finish();
//                }else{
//                    Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        String email = tvEmail.getText().toString();
        String pass  = tvPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
                            Toast.makeText(RegisterActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
//                            Log.d("adsad", user.getEmail());
                            pd.dismiss();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });


    }
}