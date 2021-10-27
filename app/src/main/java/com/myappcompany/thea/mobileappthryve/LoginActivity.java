package com.myappcompany.thea.mobileappthryve;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout loginBody;

    private TextView loginText;
    private TextView passwordText;

    private TextView alertText;

    private Button loginButton;
    private Button signupButton;

    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        loginBody = (LinearLayout) findViewById(R.id.login_body);
        loginBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        loginText = (TextView) findViewById(R.id.login_id);
        passwordText = (TextView) findViewById(R.id.login_password);

        alertText = (TextView) findViewById((R.id.login_alert));

        loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        signupButton = (Button) findViewById(R.id.button_signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://thryve-database-2021.eba-5qjamc5m.us-east-2.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


    }

    public void enter(StudentAccount sa) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("user", (Serializable) sa);
        //alertText.setText("I GOT TO THIS POINT. YAY!");
        startActivity(intent);
    }

    public void signUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void logIn() {
        String log = loginText.getText().toString();

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        Call<StudentAuthContainer> call = jsonPlaceHolderApi.getStudentAuths("studentauth/get-studentauth");

        call.enqueue(new Callback<StudentAuthContainer>() {
            @Override
            public void onResponse(Call<StudentAuthContainer> call, Response<StudentAuthContainer> response) {
                if (!response.isSuccessful()) {
                    alertText.setText("login code: " + response.code());
                    return;
                }

                StudentAuthContainer container = response.body();

                List<StudentAuth> studentAuths = container.getMyStudentAuths();

                boolean loginCheck = false;

                for (StudentAuth studentAuth : studentAuths) {
                    if (loginText.getText().toString().equals(studentAuth.getSheridanId())) {
                        loginCheck = true;
                        if (passwordText.getText().toString().equals(studentAuth.getPassword())) {
                            //alertText.setText("The auth id is " + studentAuth.getId() + ".");
                            getAccount(studentAuth.getId());
                        } else {
                            loginText.setText("");
                            passwordText.setText("");
                            alertText.setText("Incorrect Password");
                        }
                    }
                }

                if (loginCheck == false) {
                    loginText.setText("");
                    passwordText.setText("");
                    alertText.setText("User doesn't exist");
                }
            }

            @Override
            public void onFailure(Call<StudentAuthContainer> call, Throwable t) {
                alertText.setText(t.getMessage());
            }
        });
    }

    public void getAccount(int account) {
        Call<StudentContainer> call = jsonPlaceHolderApi.getStudentAccounts("student/get-studentaccount");

        call.enqueue(new Callback<StudentContainer>() {
            @Override
            public void onResponse(Call<StudentContainer> call, Response<StudentContainer> response) {
                if (!response.isSuccessful()) {
                    alertText.setText("account code: " + response.code());
                    return;
                }

                StudentContainer containerx = response.body();

                List<StudentAccount> studentAccounts = containerx.getMyStudentAccounts();
                alertText.setText("");
                for (StudentAccount studentAccount : studentAccounts) {
                    alertText.setText("Hello, " + studentAccount.getId() + ":" + studentAccount.getProgramId() + ":" + studentAccount.getAuthId());
                    if (studentAccount.getAuthId() == account) {
                        //alertText.setText("I AM GETTING THE ACCOUNT!");
                        enter(studentAccount);
                        return;
                    } else
                        alertText.append("NO ACCOUNT: " + studentAccount.getAuthId() + " : " + account + "\n");
                }
            }

            @Override
            public void onFailure(Call<StudentContainer> call, Throwable t) {
                alertText.setText("account error: " + t.getMessage());
            }
        });
    }
}
