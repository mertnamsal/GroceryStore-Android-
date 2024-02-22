package com.example.grocerystore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RecaptchaActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {

    FirebaseAuth auth;
    CheckBox checkBox;
    GoogleApiClient googleApiClient;
    String SiteKey = "6Lc3NnwpAAAAAGhoj8V13qetBD0rVmst6KAFZxWH";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recaptcha);
        System.out.println("recaptcha basladi");

        checkBox = findViewById(R.id.checkBoxId);
        String mail = getIntent().getStringExtra("mail");
        System.out.println("mail : "+mail);
        String password = getIntent().getStringExtra("password");
        System.out.println("password : "+password);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(RecaptchaActivity.this)
                .build();
        googleApiClient.connect();
        System.out.println("1");
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkBox.isChecked()){
                    SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient,SiteKey)
                            .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                                @Override
                                public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                                    Status status = recaptchaTokenResult.getStatus();
                                    System.out.println("2");
                                    System.out.println(status.toString());
                                    if((status != null) && (status.isSuccess())){
                                        System.out.println("222");
                                        Toast.makeText(RecaptchaActivity.this,"Verification successful",Toast.LENGTH_SHORT).show();
                                        checkBox.setTextColor(Color.GREEN);
                                        auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task != null && task.isSuccessful()){
                                                    Toast.makeText(RecaptchaActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                                }else{
                                                    Toast.makeText(RecaptchaActivity.this,"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }else{
                                        System.out.println("FAILLLLLLLLL");
                                    }
                                }
                            });
                }else{
                    checkBox.setTextColor(Color.RED);
                    Toast.makeText(RecaptchaActivity.this,"Verification not done",Toast.LENGTH_SHORT).show();
                }
            }
        });
        System.out.println("3");


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}