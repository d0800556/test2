package com.example.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button registered;
    private Button login;
    private EditText login_email;
    private EditText login_password;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);
        registered = (Button) findViewById(R.id.registered);
        login = (Button) findViewById(R.id.login);
        loadingbar= new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, RegisteredActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                LoginUserAccount(email, password);
            }
        });
    }

    private void LoginUserAccount(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(MainActivity.this, "請輸入信箱",
                    Toast.LENGTH_LONG).show();
        }

        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "請輸入密碼",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            loadingbar.setTitle("登入中");
            loadingbar.setMessage("登入進行中請稍後");
            loadingbar.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent mainIntent = new Intent(MainActivity.this,MenuActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "請確認帳號密碼是否正確",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
