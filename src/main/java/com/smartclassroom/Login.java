package com.smartclassroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    Button buttonLogin;
    EditText editEmail;
    EditText editPassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    String email="",password="",name="",type="";
    DatabaseReference databaseReference;
    List<Member> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin=(Button)findViewById(R.id.buttonLogin);
        editEmail=(EditText)findViewById(R.id.editEmail);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        editPassword=(EditText)findViewById(R.id.editPassword);
        databaseReference= FirebaseDatabase.getInstance().getReference("Member");
        memberList=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getUid()!=null)
        {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });


    }
    private void openMain()
    {
        email=editEmail.getText().toString();
        password=editPassword.getText().toString();
        if (email.isEmpty()) {
            editEmail.setError("Enter an email address");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editEmail.setError("Enter an valid email address");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editPassword.setError("Enter a password");
            editPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                }
                else
                {
                    editEmail.setError("Email address and password didn't matched");
                    editEmail.requestFocus();
                    return;
                }

            }
        });

    }


}
