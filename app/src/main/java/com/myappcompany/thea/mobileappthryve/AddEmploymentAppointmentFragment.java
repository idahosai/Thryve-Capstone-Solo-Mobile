package com.myappcompany.thea.mobileappthryve;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.nio.ByteBuffer;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.StrictMath.random;
import static java.time.ZonedDateTime.now;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.services.s3.S3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AddEmploymentAppointmentFragment extends Fragment {
    //iggy did the below code
    private static final int REQUEST_GALLERY = 200;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String file_path1_emp=null;
    String file_path2_emp=null;
    String file_path3_emp=null;
    TextView file_name;
    Button upload;
    private int count_files_added_emp = 0;
    //progressBar progressBar;
    String bucketName = "mythryvebucket-2021";
    String folderName = "folder1";
    String fileNameInS3 = "";
    String fileNameInLocalPC = "";
    AwsBasicCredentials awsCreds;
    //iggy did the above

    //public static final String ARG_STUDENT = "argStudentAccount";

    private AddEmpFragmentListener empFragmentListener;
    private StudentAccount currentUser;
    private EmploymentConsultantForm newEmpForm;
    private Appointment newAppt;

    private EditText editTextEmpTitle;
    private EditText editTextEmpDescription;
    private EditText editTextEmpDate;
    private DatePickerDialog datePickerDialogEmpDate;
    private Spinner spinnerEmpTime;
    private EditText editTextEmpStudentNotes;

    CheckBox checkQsso;
    CheckBox checkQfriend;
    CheckBox checkQfaculty;
    CheckBox checkQvisit;
    CheckBox checkQorient;
    CheckBox checkQevent;
    CheckBox checkQkpi2;
    CheckBox checkQoutreach;
    CheckBox checkQposters;
    CheckBox checkQstv;
    CheckBox checkQsocial;
    CheckBox checkQmedia;
    CheckBox checkQwalkby;
    CheckBox checkQwebsite;
    CheckBox checkEresume;
    CheckBox checkEcover;
    CheckBox checkEinterview;
    CheckBox checkEjobsearch;
    CheckBox checkEmockinterview;
    CheckBox checkEnetworking;
    CheckBox checkEportfolio;

    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;

    List<TimeText> timeTextList = new ArrayList<>();
    List<String> timeDayList = new ArrayList<>();

    ZonedDateTime startDateTime;
    ZonedDateTime endDateTime;
    ZonedDateTime submittedEndTime;


    public interface AddEmpFragmentListener {
        void onNewEmpApptSent(Appointment newAppt, EmploymentConsultantForm newEmp);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emp_add, container, false);

        //below is added by iggy
        //AmplifyInit.intializeAmplify(Objects.requireNonNull(getActivity()));

        java.security.Security.setProperty("networkaddress.cache.ttl" , "60");
        awsCreds = AwsBasicCredentials.create(
                "AKIASBEZPPNHMQM4BIGL",
                "a5AhmoV1Pl3WfsNpDwZO73opGGD29ah3S+MUmkdF");

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setMaxErrorRetry(0);
        clientConfiguration.setConnectionTimeout(3600000);
        clientConfiguration.setSocketTimeout(3600000);
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("AKIASBEZPPNHMQM4BIGL", "a5AhmoV1Pl3WfsNpDwZO73opGGD29ah3S+MUmkdF");
        AmazonS3Client amazonS3Client = new AmazonS3Client(basicAWSCredentials, clientConfiguration);
        amazonS3Client.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_EAST_2));


        //above is added by iggy
        //empFragmentListener.onReceiveCurrentAccount();

        editTextEmpTitle = (EditText) view.findViewById(R.id.edit_text_emp_title);
        editTextEmpDescription = (EditText) view.findViewById(R.id.edit_text_emp_description);

        spinnerEmpTime = (Spinner) view.findViewById(R.id.spinner_emp_time);
        FloatingActionButton buttonBookEmpAppt = view.findViewById(R.id.button_book_emp_appt);
        buttonBookEmpAppt.setEnabled(false);
        //by iggy
        Button btn_upload_file_emp = view.findViewById(R.id.btn_upload_file_emp);
        btn_upload_file_emp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=23){
                    if (checkPermission()){
                        filePicker();
                    }
                    else{
                        requestPermission();
                    }
                }
                else{
                    filePicker();
                }
            }
        });
        upload = view.findViewById(R.id.btn_select_file_emp);
        file_name = view.findViewById(R.id.txt_file_name_emp);

        upload.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //basically if there is at least one file
                if(file_path1_emp!=null){
                    UploadFile();
                }
                else{
                    Toast.makeText(Objects.requireNonNull(getActivity()), "Please Select File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //above code is by iggy
        //
        editTextEmpStudentNotes = (EditText) view.findViewById(R.id.edit_text_emp_studentnotes);

        editTextEmpDate = (EditText) view.findViewById(R.id.edit_text_emp_date);
        editTextEmpDate.setInputType(InputType.TYPE_NULL);
        editTextEmpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                datePickerDialogEmpDate = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int myYear, int myMonth, int myDay) {
                                selectedYear = myYear;
                                selectedMonth = myMonth + 1;
                                selectedDay = myDay;

                                editTextEmpDate.setText((myMonth + 1) + "/" + myDay + "/" + myYear);

                                timeTextList = initializeTimeTextList(timeTextList);

                                getEmpTimeListFromDay(myYear, myMonth+1, myDay);
                            }
                        }, year, month, day);
                datePickerDialogEmpDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialogEmpDate.show();
            }
        });

        spinnerEmpTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TimeText selectedTime = (TimeText) parent.getSelectedItem();
                String newTimeData = selectedTime.getTimeData();
                int newHour = Integer.parseInt(newTimeData.substring(0,2));
                startDateTime = ZonedDateTime.of(selectedYear, selectedMonth, selectedDay, newHour, 0, 0, 0, ZoneId.of("America/Toronto"));
                endDateTime = ZonedDateTime.of(selectedYear, selectedMonth, selectedDay, newHour, 30, 0, 0, ZoneId.of("America/Toronto"));
                submittedEndTime = now();
                System.out.println("year: " + selectedYear + ", month: " + selectedMonth + ", day: " + selectedDay);
                buttonBookEmpAppt.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkQsso = view.findViewById(R.id.checkbox_q1e_sso);
        checkQfriend = view.findViewById(R.id.checkbox_q1e_friend);
        checkQfaculty = view.findViewById(R.id.checkbox_q1e_faculty);
        checkQvisit = view.findViewById(R.id.checkbox_q1e_visit);
        checkQorient = view.findViewById(R.id.checkbox_q1e_orient);
        checkQevent = view.findViewById(R.id.checkbox_q1e_event);
        checkQkpi2 = view.findViewById(R.id.checkbox_q1e_kpi2);
        checkQoutreach = view.findViewById(R.id.checkbox_q1e_outreach);
        checkQposters = view.findViewById(R.id.checkbox_q1e_posters);
        checkQstv = view.findViewById(R.id.checkbox_q1e_stv);
        checkQsocial = view.findViewById(R.id.checkbox_q1e_social);
        checkQmedia = view.findViewById(R.id.checkbox_q1e_media);
        checkQwalkby = view.findViewById(R.id.checkbox_q1e_walkby);
        checkQwebsite = view.findViewById(R.id.checkbox_q1e_website);
        checkEresume = view.findViewById(R.id.checkbox_ecs_resume);
        checkEcover = view.findViewById(R.id.checkbox_ecs_cover);
        checkEinterview = view.findViewById(R.id.checkbox_ecs_interview);
        checkEjobsearch = view.findViewById(R.id.checkbox_ecs_jobsearch);
        checkEmockinterview = view.findViewById(R.id.checkbox_ecs_mockinterview);
        checkEnetworking = view.findViewById(R.id.checkbox_ecs_networking);
        checkEportfolio = view.findViewById(R.id.checkbox_ecs_portfolio);

        buttonBookEmpAppt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssx");
                String formatStartTime = startDateTime.format(formatter);
                String formatEndTime = endDateTime.format(formatter);
                String formatSubmitTime = submittedEndTime.format(formatter);
                String newTitle = editTextEmpTitle.getText().toString();
                String newDescription = editTextEmpDescription.getText().toString();
                String newStudentNotes = editTextEmpStudentNotes.getText().toString();
                //below has iggy
                File file1=new File(file_path1_emp);
                File file2=new File(file_path2_emp);
                File file3=new File(file_path3_emp);
                //i may have to use file_path1_emp
                //i may have to use file_path2_emp
                //i may have to use file_path3_emp

                //above is iggy

                Appointment newAppointment = new Appointment(newTitle, formatStartTime, formatEndTime, formatSubmitTime, formatStartTime, newDescription, newStudentNotes, "x", file1.getName(), file2.getName(), file3.getName(), "P", false);
                //newAppt = newAppointment;
                System.out.println("HELLOOOOOOO");
                insertNewEmpForm(newAppointment);

                //System.out.println("newEmpForm2 id: " + newEmpForm.getId());

            }
        });

        return view;
    }

    //by iggy


    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(Objects.requireNonNull(getActivity()), "Please Give Permission to Upload File", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;

        }
        else{
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(Objects.requireNonNull(getActivity()), "Permission Successfull", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Objects.requireNonNull(getActivity()), "Permission Failed", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void filePicker(){
        //Toast.makeText(this, "File Picker Called", Toast.LENGTH_SHORT).show();
        Toast.makeText(Objects.requireNonNull(getActivity()), "File Picker Call", Toast.LENGTH_SHORT).show();
        //Let's Pick File
        Intent opengallery=new Intent(Intent.ACTION_PICK);
        opengallery.setType("image/*");
        startActivityForResult(opengallery,REQUEST_GALLERY);
    }

    public String getRealPathFromUri(Uri uri, Activity activity){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor=activity.getContentResolver().query(uri,proj,null,null,null);
        if(cursor==null){
            return uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int id=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        Toast.makeText(Objects.requireNonNull(getActivity()), "In ActivityResult", Toast.LENGTH_SHORT).show();
        //
        if(requestCode==REQUEST_GALLERY && resultCode== Activity.RESULT_OK){
            //the first param of this is the url
            //the true path of the file is found in variable filePath
            String filePath=getRealPathFromUri(data.getData(),Objects.requireNonNull(getActivity()));
            Log.d("File Path : "," "+filePath);
            //now we will upload the file
            //lets import okhttp first

            this.count_files_added_emp = this.count_files_added_emp + 1;

            if(this.count_files_added_emp == 1){
                this.file_path1_emp=filePath;
            }
            if(this.count_files_added_emp == 2){
                this.file_path2_emp=filePath;
            }
            if(this.count_files_added_emp == 3){
                this.file_path3_emp=filePath;
            }

            if (this.count_files_added_emp < 4){
                File file=new File(filePath);
                file_name.setText(file_name.getText() + "\n" +file.getName());
            }


        }
    }

    private void UploadFile() {

        //InputStream exampleInputStream = getContentResolver().openInputStream(uri);
        /*
        File exampleFile = new File(file_path1_emp);
        //il make this a set of strings
        //double randomNumber = (1000..9999).random(0,6);
        int max = 1000;
        int min = 1;
        // create instance of Random class.
        Random randomNum = new Random();
        int showMeRand_emp = min + randomNum.nextInt(max);
        String string_showMeRand_emp = String.valueOf(showMeRand_emp);
        //System. out. println(showMe);
        */

        /*
        Amplify.Storage.uploadFile(
                "UploadedFile" + string_showMeRand_emp,
                exampleFile,
                result -> Toast.makeText(Objects.requireNonNull(getActivity()), "File has Successfully Uploaded :) 1:" + result.getKey(), Toast.LENGTH_SHORT).show(),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );

        if(file_path2_emp != null){
            File exampleFile2 = new File(file_path2_emp);
            int showMeRand_emp2 = min + randomNum.nextInt(max);
            String string_showMeRand_emp2 = String.valueOf(showMeRand_emp2);
            Amplify.Storage.uploadFile(
                    "UploadedFile" + string_showMeRand_emp2,
                    exampleFile2,
                    result -> Toast.makeText(Objects.requireNonNull(getActivity()), "File has Successfully Uploaded :) 2:" + result.getKey(), Toast.LENGTH_SHORT).show(),
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        }
        if(file_path3_emp != null){
            File exampleFile3 = new File(file_path3_emp);
            int showMeRand_emp3 = min + randomNum.nextInt(max);
            String string_showMeRand_emp3 = String.valueOf(showMeRand_emp3);
            Amplify.Storage.uploadFile(
                    "UploadedFile" + string_showMeRand_emp3,
                    exampleFile3,
                    result -> Toast.makeText(Objects.requireNonNull(getActivity()), "File has Successfully Uploaded :) 3:" + result.getKey(), Toast.LENGTH_SHORT).show(),
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        }
        */


        UploadTask uploadTask=new UploadTask();
        uploadTask.execute(new String[]{file_path1_emp, file_path2_emp, file_path3_emp});
    }

    public class UploadTask extends AsyncTask<String,String,String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(View.GONE);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(Objects.requireNonNull(getActivity()), "File uploaded", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(Objects.requireNonNull(getActivity()), "Failed Upload", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            /*
            if(uploadFile(strings[0])){
                return "true";
            }
            else{
                return "failed";
            }
            */

            int max = 1000;
            int min = 1;
            // create instance of Random class.
            Random randomNum = new Random();

            int showMeRand_emp = min + randomNum.nextInt(max);


            int showMeRand_emp2 = min + randomNum.nextInt(max);


            int showMeRand_emp3 = min + randomNum.nextInt(max);

            while(showMeRand_emp == showMeRand_emp2 || showMeRand_emp == showMeRand_emp3 || showMeRand_emp2 == showMeRand_emp3){
                showMeRand_emp = min + randomNum.nextInt(max);
                showMeRand_emp2 = min + randomNum.nextInt(max);
                showMeRand_emp3 = min + randomNum.nextInt(max);
            }
            String string_showMeRand_emp = String.valueOf(showMeRand_emp);
            String string_showMeRand_emp2 = String.valueOf(showMeRand_emp2);
            String string_showMeRand_emp3 = String.valueOf(showMeRand_emp3);

            //last om strings list
            if(strings[2] != null){
                for(String str: strings){
                    if(str != null){
                        File exampleFile = new File(str);
                        try{
                            ClientConfiguration clientConfiguration = new ClientConfiguration();
                            clientConfiguration.setMaxErrorRetry(0);
                            clientConfiguration.setConnectionTimeout(3600000);
                            clientConfiguration.setSocketTimeout(3600000);

                            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("AKIASBEZPPNHMQM4BIGL", "a5AhmoV1Pl3WfsNpDwZO73opGGD29ah3S+MUmkdF");
                            AmazonS3Client amazonS3Client = new AmazonS3Client(basicAWSCredentials, clientConfiguration);
                            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,exampleFile.getName() +':'+string_showMeRand_emp+':'+string_showMeRand_emp2 + ':'+string_showMeRand_emp3 ,exampleFile);
                            amazonS3Client.putObject(objectRequest);

                        }catch (Exception e){
                            Toast.makeText(Objects.requireNonNull(getActivity()), "not working"+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
            else if(strings[1] != null){
                for(String str: strings){
                    if(str != null){
                        File exampleFile = new File(str);
                        try{
                            ClientConfiguration clientConfiguration = new ClientConfiguration();
                            clientConfiguration.setMaxErrorRetry(0);
                            clientConfiguration.setConnectionTimeout(3600000);
                            clientConfiguration.setSocketTimeout(3600000);

                            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("AKIASBEZPPNHMQM4BIGL", "a5AhmoV1Pl3WfsNpDwZO73opGGD29ah3S+MUmkdF");
                            AmazonS3Client amazonS3Client = new AmazonS3Client(basicAWSCredentials, clientConfiguration);
                            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,exampleFile.getName() +':'+string_showMeRand_emp+':'+string_showMeRand_emp2 + ':' ,exampleFile);
                            amazonS3Client.putObject(objectRequest);

                        }catch (Exception e){
                            Toast.makeText(Objects.requireNonNull(getActivity()), "not working"+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
            else if(strings[0] != null){
                for(String str: strings){
                    if(str != null){
                        File exampleFile = new File(str);
                        try{
                            ClientConfiguration clientConfiguration = new ClientConfiguration();
                            clientConfiguration.setMaxErrorRetry(0);
                            clientConfiguration.setConnectionTimeout(3600000);
                            clientConfiguration.setSocketTimeout(3600000);

                            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("AKIASBEZPPNHMQM4BIGL", "a5AhmoV1Pl3WfsNpDwZO73opGGD29ah3S+MUmkdF");
                            AmazonS3Client amazonS3Client = new AmazonS3Client(basicAWSCredentials, clientConfiguration);
                            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,exampleFile.getName() +':'+string_showMeRand_emp+':'+ ':' ,exampleFile);
                            amazonS3Client.putObject(objectRequest);

                        }catch (Exception e){
                            Toast.makeText(Objects.requireNonNull(getActivity()), "not working"+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }

            return "true";//remove this latter
        }




    }
    //above by iggy

    public void saveCurrentUser(StudentAccount a) {
        currentUser = a;
    }

    private List<TimeText> initializeTimeTextList(List<TimeText> timeList) {
        timeList.clear();
        TimeText time1 = new TimeText("08:00 AM", "08:00");
        timeList.add(time1);
        TimeText time2 = new TimeText("09:00 AM", "09:00");
        timeList.add(time2);
        TimeText time3 = new TimeText("10:00 AM", "10:00");
        timeList.add(time3);
        TimeText time4 = new TimeText("11:00 AM", "11:00");
        timeList.add(time4);
        TimeText time5 = new TimeText("01:00 PM", "13:00");
        timeList.add(time5);
        TimeText time6 = new TimeText("02:00 PM", "14:00");
        timeList.add(time6);
        TimeText time7 = new TimeText("03:00 PM", "15:00");
        timeList.add(time7);
        TimeText time8 = new TimeText("04:00 PM", "16:00");
        timeList.add(time8);

        return timeList;
    }

    private void getEmpTimeListFromDay(int y, int m, int d) {
        System.out.println("myDEBUG: " + "year: " + y + ", month: " + m + ", day: " + d);
        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.getApiService();

        Call<AppointmentContainer> call = jsonPlaceHolderApi.listDayAppointments(7, y, m, d);
        call.enqueue(new Callback<AppointmentContainer>() {
            @Override
            public void onResponse(Call<AppointmentContainer> call, Response<AppointmentContainer> response) {
                if(!response.isSuccessful()) {
                    System.out.println("TimeList error!!");
                    return;
                }

                System.out.println("NO ERROR!!");
                AppointmentContainer container = response.body();
                List<Appointment> appointments = container.getMyAppointments();
                System.out.println("appointments.size() = " + appointments.size());

                List<String> timeStringList = new ArrayList<>();

                for(Appointment appointment: appointments) {
                    String thisTime = (appointment.getStartDate()).substring(11, 16);
                    timeStringList.add(thisTime);
                }

                assignTimeDay(timeStringList);
            }

            @Override
            public void onFailure(Call<AppointmentContainer> call, Throwable t) {

            }
        });
    }

    public void assignTimeDay(List<String> myStringList) {
        timeDayList = myStringList;
        System.out.println("myDEBUG: timeTextList.size() = " + timeTextList.size());

        for(String myTimes: timeDayList) {
            for(int i = 0; i < timeTextList.size(); i++) {
                System.out.println("Comparing: " + myTimes + ": " + timeTextList.get(i).getTimeData());
                if(myTimes.equals(timeTextList.get(i).getTimeData())) {
                    timeTextList.remove(i);
                    break;
                }
            }
        }

        System.out.println("myDEBUG: timeDayList.size() = " + timeDayList.size());
        System.out.println("myDEBUG: timeTextList.size() = " + timeTextList.size());

        ArrayAdapter<TimeText> adapter = new ArrayAdapter<TimeText>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, timeTextList);
        spinnerEmpTime.setAdapter(adapter);
    }

    public void insertNewEmpForm(Appointment myNewAppt) {
        EmploymentConsultantForm insertForm = new EmploymentConsultantForm(checkQsso.isChecked(),
                checkQfriend.isChecked(),
                checkQfaculty.isChecked(),
                checkQvisit.isChecked(),
                checkQorient.isChecked(),
                checkQevent.isChecked(),
                checkQkpi2.isChecked(),
                checkQoutreach.isChecked(),
                checkQposters.isChecked(),
                checkQstv.isChecked(),
                checkQsocial.isChecked(),
                checkQmedia.isChecked(),
                checkQwalkby.isChecked(),
                checkQwebsite.isChecked(),
                checkEresume.isChecked(),
                checkEcover.isChecked(),
                checkEinterview.isChecked(),
                checkEjobsearch.isChecked(),
                checkEmockinterview.isChecked(),
                checkEnetworking.isChecked(),
                checkEportfolio.isChecked());

        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.getApiService();
        System.out.println("I am here!!!");
        Call<EmploymentConsultantFormContainer> call = jsonPlaceHolderApi.createEmploymentConsultantForm(insertForm);

        call.enqueue(new Callback<EmploymentConsultantFormContainer>() {
            @Override
            public void onResponse(Call<EmploymentConsultantFormContainer> call, Response<EmploymentConsultantFormContainer> response) {
                if(!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("Message: " + response.message());
                    System.out.println("Error Body: " + response.errorBody());
                    System.out.println("Headers: " + response.headers());
                    System.out.println("Raw: " + response.raw());
                }

                System.out.println("PLEASE WORK! PLEASE!");
                EmploymentConsultantFormContainer container = response.body();
                List<EmploymentConsultantForm> employmentConsultantForms = container.getMyEmploymentConsultantForms();

                /*for(EmploymentConsultantForm employmentConsultantForm: employmentConsultantForms) {
                    System.out.println("myTest");
                    newEmpForm = employmentConsultantForm;
                }*/

                newEmpForm = employmentConsultantForms.get(0);
                System.out.println("I am here. :)))");
                System.out.println("myNewAppt: " + myNewAppt.getId() + " : " + myNewAppt.getStartDate());
                System.out.println("newEmpForm: " + newEmpForm.getId());
                empFragmentListener.onNewEmpApptSent(myNewAppt, newEmpForm);
            }

            @Override
            public void onFailure(Call<EmploymentConsultantFormContainer> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddEmpFragmentListener) {
            empFragmentListener = (AddEmpFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement AddEmpFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        empFragmentListener = null;
    }
}
