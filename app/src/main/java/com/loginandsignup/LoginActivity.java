package com.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult>, View.OnFocusChangeListener {

// dont forget to remove loginactivity as a launcher on manifest.xml


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
    private String _mail;
    private String _password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initEvent();

    }


    public void initEvent() {


        btnLogin.setOnClickListener(this);
        txtLink.setOnClickListener(this);
        edtEmail.setOnFocusChangeListener(this);
        edtPassword.setOnFocusChangeListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v == btnLogin) {
            validate();
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

    public boolean validate() {

        _mail = String.valueOf(edtEmail.getText());
        _password = String.valueOf(edtPassword.getText());
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
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            callProgress();

            //Toast.makeText(getApplicationContext(), "kayıt başarılı", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), " que paso? do it again ", Toast.LENGTH_SHORT).show();
        }
    }

    public CustomProgressDialog callProgress() {
        CustomProgressDialog dialog = new CustomProgressDialog(getApplicationContext());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (!hasFocus) {
            hideKeyboard(v);
        }
    }
}

