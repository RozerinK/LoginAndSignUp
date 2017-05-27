package com.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ui.CustomProgressDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {

// dont forget to remove loginactivity as a launcher on manifest.xml

    String email;
    ;
    String password;
    @InjectView(R.id.activity_login_edtemail)
    EditText edtEmail;
    @InjectView(R.id.activity_login_edtpassword)
    EditText edtPassword;
    @InjectView(R.id.activity_login_btnlogin)
    Button btnLogin;
    @InjectView(R.id.activity_login_txtlink)
    TextView txtLink;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        getSupportActionBar().hide();

        initEvent();

    }


    public void initEvent() {


        btnLogin.setOnClickListener(this);
        txtLink.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v == btnLogin) {
            validation();
            if (isValid == true) {
                btnLogin.setEnabled(true);
                auth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                        .addOnCompleteListener(this);
            } else {
                Toast.makeText(getApplicationContext(), "fill both of honey", Toast.LENGTH_LONG);
            }
        } else {
            Intent myIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(myIntent);

        }
    }

    public void login() {


    }

    public boolean validation() {

        email = String.valueOf(edtEmail.getText());
        password = String.valueOf(edtPassword.getText());
        if (email.equals("") || password.equals("")) {
            isValid = false;

        } else {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            CustomProgressDialog dialog = new CustomProgressDialog(getApplicationContext());
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            // return dialog;

            Toast.makeText(getApplicationContext(), "kayıt başarılı", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), " que paso? do it again ", Toast.LENGTH_SHORT).show();
        }
    }
}
