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

public class RegisteredActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText registered_name;
    private EditText registered_password;
    private EditText registered_email;
    private Button registered;
    private Button back;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        mAuth = FirebaseAuth.getInstance();

        registered_name = (EditText) findViewById(R.id.registered_name);
        registered_password = (EditText) findViewById(R.id.registered_password);
        registered_email = (EditText) findViewById(R.id.registered_email);
        registered = (Button) findViewById(R.id.registered);
        back = (Button) findViewById(R.id.back);
        loadingbar= new ProgressDialog(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegisteredActivity.this,MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        });

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = registered_name.getText().toString();
                String email = registered_email.getText().toString();
                String password = registered_password.getText().toString();

                RegisterAccount(name,email,password);
            }
        });
    }

    private void RegisterAccount(String name, String email, String password) {
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(RegisteredActivity.this, "請輸入名稱",
                                                                Toast.LENGTH_LONG).show();
        }

        else if(TextUtils.isEmpty(email))
        {
            Toast.makeText(RegisteredActivity.this, "請輸入信箱",
                    Toast.LENGTH_LONG).show();
        }

        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(RegisteredActivity.this, "請輸入密碼",
                    Toast.LENGTH_LONG).show();
        }

        else
        {
            loadingbar.setTitle("註冊中");
            loadingbar.setMessage("註冊進行中請稍後");
            loadingbar.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent mainIntent = new Intent(RegisteredActivity.this,MenuActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(RegisteredActivity.this, "註冊失敗，請再試一次",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
