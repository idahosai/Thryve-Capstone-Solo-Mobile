package com.myappcompany.thea.mobileappthryve;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    private TextView signupAlert;
    private EditText registerFname;
    private EditText registerLname;
    private EditText registerSheridanId;
    private EditText registerEmail;
    private EditText registerPassword;
    private EditText registerConfirmPass;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://thryve-database-2021.eba-5qjamc5m.us-east-2.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        signupAlert = (TextView) findViewById(R.id.signup_alert);
        registerFname = (EditText) findViewById(R.id.reg_fname);
        registerLname = (EditText) findViewById(R.id.reg_lname);
        registerSheridanId = (EditText) findViewById(R.id.reg_sheridanid);
        registerEmail = (EditText) findViewById(R.id.reg_email);
        registerPassword = (EditText) findViewById(R.id.et_password);
        registerConfirmPass = (EditText) findViewById(R.id.et_repassword);

        registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateSignUp();
                //createNewStudentAuth();
                //createNewStudentAccount(44);
            }
        });
    }

    private void validateSignUp() {
        String textFname = registerFname.getText().toString().replaceAll("\\s", "");
        String textLname = registerLname.getText().toString().replaceAll("\\s", "");
        String textId = registerSheridanId.getText().toString().replaceAll("\\s", "");
        String textEmail = registerEmail.getText().toString().replaceAll("\\s", "");
        String textPassword = registerPassword.getText().toString().replaceAll("\\s", "");
        String textConfirmPassword = registerConfirmPass.getText().toString().replaceAll("\\s", "");

        if (textFname.equals("")
                || textLname.equals("")
                || textId.equals("")
                || textEmail.equals("")
                || textPassword.equals("")
                || textConfirmPassword.equals("")) {
            signupAlert.setText("Some fields missing");
        } else {
            //signupAlert.setText("I AM JUST TESTING THIS");
            Call<StudentContainer> call = jsonPlaceHolderApi.getStudentAccounts("student/get-studentaccount");

            call.enqueue(new Callback<StudentContainer>() {
                @Override
                public void onResponse(Call<StudentContainer> call, Response<StudentContainer> response) {
                    if (!response.isSuccessful()) {
                        signupAlert.setText(response.isSuccessful() + ": Code: " + response.code());
                        return;
                    }

                    System.out.print("I AM JUST TESTING THIS!!");
                    StudentContainer container = response.body();

                    List<StudentAccount> studentAccounts = container.getMyStudentAccounts();

                    boolean a = false;
                    boolean b = false;
                    for (StudentAccount studentAccount : studentAccounts) {
                        if (textId.equals(studentAccount.getStudentNumber())) {
                            a = true;
                            signupAlert.setText("Student number already in use!");
                            //return;
                        }
                        if (textEmail.equals(studentAccount.getEmailAddress())) {
                            b = true;
                            signupAlert.setText("E-mail address already in use!");
                            //return;
                        }
                    }

                    if (a == false && b == false) {
                        if (textPassword.equals(textConfirmPassword)) {
                            AlertDialog.Builder myConfirmAlert = new AlertDialog.Builder((SignUpActivity.this));
                            myConfirmAlert.setTitle("Confirm Registration");
                            myConfirmAlert.setMessage("Do you want to proceed with this registration?");
                            myConfirmAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    createNewStudentAuth();
                                    //getNewSchoolProgram();
                                }
                            });
                            myConfirmAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            myConfirmAlert.show();
                        } else {
                            signupAlert.setText("Passwords don't match!");
                            return;
                        }
                    }

                }

                @Override
                public void onFailure(Call<StudentContainer> call, Throwable t) {
                    signupAlert.setText("Failure: " + t.getMessage());
                }
            });
        }
    }

    private void createNewStudentAuth() {
        String textId = registerSheridanId.getText().toString().replaceAll("\\s", "");
        String textPassword = registerPassword.getText().toString().replaceAll("\\s", "");

        //final SchoolProgram n = m;

        Call<StudentAuthContainer> call = jsonPlaceHolderApi.createStudentAuth(textId, textPassword);

        call.enqueue(new Callback<StudentAuthContainer>() {
            @Override
            public void onResponse(Call<StudentAuthContainer> call, Response<StudentAuthContainer> response) {
                if (!response.isSuccessful()) {
                    signupAlert.setText("Signup Error 1: " + response.code());
                    return;
                }
                //signupAlert.setText("SUCCESS 1!");

                StudentAuthContainer container = response.body();

                List<StudentAuth> studentAuths = container.getMyStudentAuths();

                for (StudentAuth studentAuth: studentAuths) {
                    createNewStudentAccount(studentAuth.getId());
                }
            }

            @Override
            public void onFailure(Call<StudentAuthContainer> call, Throwable t) {
                signupAlert.setText("Failure: " + t.getMessage());
            }
        });
    }

    private void createNewStudentAccount(int a) {
        String textFname = registerFname.getText().toString().replaceAll("\\s", "");
        String textLname = registerLname.getText().toString().replaceAll("\\s", "");
        String textId = registerSheridanId.getText().toString().replaceAll("\\s", "");
        String textEmail = registerEmail.getText().toString().replaceAll("\\s", "");

        StudentAccount newAccount =  new StudentAccount(textFname,
                textLname,
                textEmail,
                textId,
                1,
                false,
                false,
                "0.0",
                "Diploma",
                false,
                "647111111",
                "full");

        //signupAlert.setText("fname: " + newAccount.getFirstName());

        Call<StudentContainer> call = jsonPlaceHolderApi.createStudentAccount(a, 1, newAccount);
        //Call<StudentContainer> call = jsonPlaceHolderApi.createStudentAccount(
                //a, 1, textFname, textLname, textEmail, textId, "4", false, false, false, "0");

        call.enqueue(new Callback<StudentContainer>() {
            @Override
            public void onResponse(Call<StudentContainer> call, Response<StudentContainer> response) {
                if (!response.isSuccessful()) {
                    signupAlert.setText("Signup Error 2: " + response.message());
                    return;
                }
                //signupAlert.setText("Account Registration Success!");

                AlertDialog.Builder registerConfirmAlert = new AlertDialog.Builder(SignUpActivity.this);
                registerConfirmAlert.setTitle("Registration Complete!");
                registerConfirmAlert.setMessage("Thank you for registering, " + textFname + ".");
                registerConfirmAlert.setCancelable(false);
                registerConfirmAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                registerConfirmAlert.show();
            }

            @Override
            public void onFailure(Call<StudentContainer> call, Throwable t) {
                signupAlert.setText("Account Registration Failed: " + t.getMessage());
            }
        });
    }

    private void getNewSchoolProgram() {
        Call<SchoolProgramContainer> call = jsonPlaceHolderApi.getAssignedSchoolProgram("program/find-schoolprogram/1");

        call.enqueue(new Callback<SchoolProgramContainer>() {
            @Override
            public void onResponse(Call<SchoolProgramContainer> call, Response<SchoolProgramContainer> response) {
                if (!response.isSuccessful()) {
                    signupAlert.setText("Program Error: " + response.code());
                    return;
                }

                SchoolProgramContainer container = response.body();

                List<SchoolProgram> schoolPrograms = container.getMySchoolPrograms();

                for (SchoolProgram schoolProgram: schoolPrograms) {
                    //createNewStudentAuth(schoolProgram);
                }
                //createNewStudentAuth(schoolPrograms.get(0));
            }

            @Override
            public void onFailure(Call<SchoolProgramContainer> call, Throwable t) {
                signupAlert.setText("Incomplete!");
            }
        });
    }
}
