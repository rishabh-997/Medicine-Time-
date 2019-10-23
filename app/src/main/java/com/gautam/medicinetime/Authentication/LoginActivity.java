package com.gautam.medicinetime.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gautam.medicinetime.R;
import com.gautam.medicinetime.medicine.MedicineActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email,password;
    TextView forgotpassword,newuser;

    Button login;
    String xemail,xpassword;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.loginemail);
        password=findViewById(R.id.loginpassword);
        login=findViewById(R.id.login);
        forgotpassword=findViewById(R.id.txt_view_forgot_password);
        newuser=findViewById(R.id.txt_view_new_user);
        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("users");

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            startActivity(new Intent(LoginActivity.this,MedicineActivity.class));
            finish();
        }

        login.setOnClickListener(this);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xemail=email.getText().toString().trim();
                if(TextUtils.isEmpty(xemail))
                {
                    Toast.makeText(getApplicationContext(),"Please enter email", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(xemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Link to reset your password has been sent on given email", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "Error Please try again later", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });


        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),SignUpActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {



        xemail=email.getText().toString().trim();
        xpassword=password.getText().toString().trim();



        if(TextUtils.isEmpty(xemail))
        {
            email.setError("Enter Email");
            return;
        }
        else if(TextUtils.isEmpty(xpassword))
        {
            password.setError("Enter Password");
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(xemail,xpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                           Intent i=new Intent(getApplicationContext(), MedicineActivity.class);
                           finish();
                           startActivity(i);
                        }else
                        {
                            Toast.makeText(getApplicationContext(),"User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
