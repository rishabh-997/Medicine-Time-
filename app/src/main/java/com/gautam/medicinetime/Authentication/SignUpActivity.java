package com.gautam.medicinetime.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gautam.medicinetime.R;
import com.gautam.medicinetime.medicine.MedicineActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
  EditText name,email,password,confirmpassword,age;
  Button signup;
  FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=findViewById(R.id.edt_txt_sign_up_name);
        email=findViewById(R.id.edt_txt_sign_up_email);
        password=findViewById(R.id.edt_txt_sign_up_password);
        confirmpassword=findViewById(R.id.edt_txt_sign_up_confirm_password);
        age=findViewById(R.id.edt_txt_sign_up_age);
        firebaseAuth= FirebaseAuth.getInstance();

        signup=findViewById(R.id.btn_sign_up);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view ==signup)
        {
            String xname,xemail,xpassword,xconfirmpassword,xage;

            xname=name.getText().toString().trim();
            xemail=email.getText().toString().trim();
            xpassword=password.getText().toString().trim();
            xconfirmpassword=confirmpassword.getText().toString().trim();
            xage=age.getText().toString().trim();

            if (xemail.isEmpty()||xname.isEmpty()||xpassword.isEmpty()||xconfirmpassword.isEmpty()||xage.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Please enter all details", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(!xpassword.equals(xconfirmpassword))
            {
                Toast.makeText(getApplicationContext(),"Passwords do not match..", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                firebaseAuth.createUserWithEmailAndPassword(xemail,xpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful())
                       {
                           Toast.makeText(getApplicationContext(), "Successfully signed in", Toast.LENGTH_SHORT).show();
                           Intent i=new Intent(getApplicationContext(), MedicineActivity.class);
                           finish();
                           startActivity(i);
                       }
                       else{
                           Toast.makeText(SignUpActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        }
    }
}
