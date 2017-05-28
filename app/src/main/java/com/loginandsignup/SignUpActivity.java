package com.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {

    @InjectView(R.id.activity_sign_up_edtname)
    EditText edtName;
    @InjectView(R.id.activity_sign_up_edtemail)
    EditText edtEmail;
    @InjectView(R.id.activity_sign_up_edtpassword)
    EditText edtPassword;
    @InjectView(R.id.activity_sign_up_btnsignup)
    Button btnSignUp;
    @InjectView(R.id.activity_sign_up_txtbacklogin)
    TextView txtBackLogin;
    private boolean isValid;

    private String _name;
    private String _mail;
    private String _password;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);
        getSupportActionBar().hide();
        initEvent();
    }

    public void initEvent() {
        btnSignUp.setOnClickListener(this);
        txtBackLogin.setOnClickListener(this);


    }

    public boolean validate() {

        _name = edtName.getText().toString();
        _mail = edtEmail.getText().toString();
        _password = edtPassword.getText().toString();

        if (_name.isEmpty() || _name.length() < 3) {
            edtName.setError("At least 3 Jo.");
        } else {
            edtName.setError(null);
            isValid = true;
        }

        if (_mail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(_mail).matches()) {
            edtEmail.setError("It is not an email address. No?");
        } else {
            edtEmail.setError(null);
            isValid = true;
        }
        if (_password.isEmpty() || _password.length() < 4 || _password.length() > 10) {
            edtPassword.setError("do it strong men!");
        } else {
            edtPassword.setError(null);
            isValid = true;
        }
        return isValid;
    }

    @Override
    public void onClick(View v) {

        if (v == btnSignUp) {
            validate();

            if (isValid == true) {
                auth.createUserWithEmailAndPassword(_mail, _password).addOnCompleteListener(this);

            }
        } else {
            Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(myIntent);
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful())
            Toast.makeText(getApplicationContext(), "Bienvenido! ", Toast.LENGTH_SHORT);
    }
}
