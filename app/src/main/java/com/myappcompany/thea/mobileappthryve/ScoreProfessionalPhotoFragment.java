package com.myappcompany.thea.mobileappthryve;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.JSONValue;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.http.Url;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.androidnetworking.utils.Utils.getMimeType;
import static java.lang.System.currentTimeMillis;

import android.os.Handler;
import android.os.Looper;


import android.app.DownloadManager;

import static java.time.ZonedDateTime.now;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.services.s3.S3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;


public class ScoreProfessionalPhotoFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE_SPP = 1;
    private static final int REQUEST_GALLERY_SPP = 200;
    Button btn_upload_spp;
    Button btn_linkedin_spp;
    Button btn_getphotoscore_spp;
    TextView txt_filename_spp;
    TextView txt_trustworthiness_spp;
    TextView txt_likeability_spp;
    String file_path1_spp;


    private static String API_KEY = "78v9nv2ws7fylh";
    private static String API_SECRET = "cc5xLO5w2uP4vvCZ";
    private static String CALL_BACK = "https://thryve.com/auth/linkedin/getimage";
    //to get authorization code
    //https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=781bq2sit0q9i8&scope=r_liteprofile&state=123456&redirect_uri=https://thryve.com/auth/linkedin/getimage

    //to get access token
    //https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&client_id=781bq2sit0q9i8&client_secret=VvPjk8sDrZO7tmBz&code=AQRkvcg9Mg3bRCqwNl4i3GE928KDcxAmlYtJH1Uphd2UNCIx1fm2DhwkH3-P_LjpCoMjmb3AuaTBnKGqgcN82zH9fuozOQ2bFDr0ldV56yMHLUjSPbQvmVro_2I0NhVMdal1s9haCqonQn7zcU9J1xYk5Vs07M4hSq5kay68IQ2bYqMGJy1T19D2mXsu7tMAdI0UA9YbzhzHBOKDr1M&redirect_uri=https://thryve.com/auth/linkedin/getimage
    int countin = 0;


    String linkedinAuthURLFull;
    Dialog linkedIndialog;
    String linkedinCode;

    String id = "";
    String firstName = "";
    String lastName = "";
    String email = "";
    String profilePicURL = "";
    String accessToken = "";

    File linkedinFile;
    ImageView img_photo_spp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //remove - return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_score_professional_photo, container, false);


        btn_upload_spp = view.findViewById(R.id.btn_upload_spp);
        btn_linkedin_spp = view.findViewById(R.id.btn_linkedin_spp);
        btn_getphotoscore_spp = view.findViewById(R.id.btn_getphotoscore_spp);

        txt_filename_spp = view.findViewById(R.id.txt_filename_spp);
        txt_trustworthiness_spp = view.findViewById(R.id.txt_trustworthiness_spp);
        txt_likeability_spp = view.findViewById(R.id.txt_likeability_spp);
        img_photo_spp = view.findViewById(R.id.img_photo_spp);




        btn_upload_spp.setOnClickListener(new View.OnClickListener(){

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

        String state = "linkedin" + TimeUnit.MILLISECONDS.toSeconds(currentTimeMillis());

        linkedinAuthURLFull = LinkedInConstants.AUTHURL + "?response_type=code&client_id=" + LinkedInConstants.CLIENT_ID + "&scope=" + LinkedInConstants.SCOPE + "&state=" + state + "&redirect_uri=" + LinkedInConstants.REDIRECT_URI;


        btn_linkedin_spp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=23){
                    if (checkPermission()){
                        linkedinfilePicker(linkedinAuthURLFull);
                    }
                    else{
                        //requestPermission();
                    }
                }
                else{
                    filePicker();
                }
            }
        });
        return view;
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(Objects.requireNonNull(getActivity()), "Please Give Permission to Upload File", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE_SPP);
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


    public class LinkedInTask extends AsyncTask<String,String,String> {

        //@RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(View.GONE);



            if (s.equalsIgnoreCase("0")){
                System.out.println("countin is 000000000000000000000000000000000000000000000000000000000000000000000000");
                Toast.makeText(Objects.requireNonNull(getActivity()), "countin is 000000000000000000000000000000000000000000000000000000000000000000000000", Toast.LENGTH_SHORT).show();
            }
            else if(s.equalsIgnoreCase("1")){
                System.out.println("countin is 1111111111111111111111111111111111111111111111111111111111111111111111");
                Toast.makeText(Objects.requireNonNull(getActivity()), "countin is 111111111111111111111111111111111111111111111111111111111", Toast.LENGTH_SHORT).show();
            }
            else if (s.equalsIgnoreCase("2")){
                System.out.println("countin is 22222222222222222222222222222222222222222222222222222222222222222222");
                Toast.makeText(Objects.requireNonNull(getActivity()), "countin is 22222222222222222222222222222222222222222222222222222222222", Toast.LENGTH_SHORT).show();
            }
            else if (s.equalsIgnoreCase("3")){
                System.out.println("countin is 33333333333333333333333333333333333333333333333333333333333333333333333");
                Toast.makeText(Objects.requireNonNull(getActivity()), "countin is 33333333333333333333333333333333333333333333333333333333333333", Toast.LENGTH_SHORT).show();
            }
            else if (s.equalsIgnoreCase("4")){
                System.out.println("countin is 4444444444444444444444444444444444444444444444444444444444444444444444444444444");
                Toast.makeText(Objects.requireNonNull(getActivity()), "countin is 444444444444444444444444444444444444444444444444444444444444444", Toast.LENGTH_SHORT).show();

            }
            else{

                txt_filename_spp.setText(s);
                System.out.println(s);
                Toast.makeText(Objects.requireNonNull(getActivity()), s, Toast.LENGTH_SHORT).show();

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
            Token accessToken = null;
            //Using the Scribe library we enter the information needed to begin the chain of Oauth2 calls.
            OAuthService service = new ServiceBuilder()
                    .provider(LinkedInApi.class)
                    .apiKey(API_KEY)
                    .apiSecret(API_SECRET)
                    .callback(CALL_BACK)
                    .build();

            */

        //    try{
                /*
                File file = new File("service.dat");

                if(file.exists()){
                    //if the file exists we assume it has the AuthHandler in it - which in turn contains the Access Token
                    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                    AuthHandler authHandler = (AuthHandler) inputStream.readObject();
                    accessToken = authHandler.getAccessToken();
                } else {
                    System.out.println("There is no stored Access token we need to make one");

                    AuthHandler authHandler = new AuthHandler(service);
                    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("service.dat"));
                    outputStream.writeObject( authHandler);
                    outputStream.close();
                    accessToken = authHandler.getAccessToken();
                 */

            //    }

            //}catch (Exception e){
                //System.out.println("Threw an exception when serializing: " + e.getClass() + " :: " + e.getMessage());
            //}



        /*
        System.out.println();
        System.out.println("********A basic user profile call********");
        //The ~ means yourself - so this should return the basic default information for your profile in XML format
        //https://developer.linkedin.com/documents/profile-api
        String url = "http://api.linkedin.com/v1/people/~";
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();
        */

        /*
        System.out.println("********Get the profile in JSON********");
        //This basic call profile in JSON format
        //You can read more about JSON here http://json.org
        url = "http://api.linkedin.com/v1/people/~";
        request = new OAuthRequest(Verb.GET, url);
        request.addHeader("x-li-format", "json");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();

        System.out.println("********Get the profile in JSON using query parameter********");
        //This basic call profile in JSON format. Please note the call above is the preferred method.
        //You can read more about JSON here http://json.org
        url = "http://api.linkedin.com/v1/people/~";
        request = new OAuthRequest(Verb.GET, url);
        request.addQuerystringParameter("format", "json");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();



        System.out.println("********Get only 10 connections - using parameters********");
        //This basic call gets only 10 connections  - each one will be in a person tag with some profile information
        //https://developer.linkedin.com/documents/connections-api
        //more basic about query strings in a URL here http://en.wikipedia.org/wiki/Query_string
        url = "http://api.linkedin.com/v1/people/~/connections";
        request = new OAuthRequest(Verb.GET, url);
        request.addQuerystringParameter("count", "10");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********GET network updates that are CONN and SHAR********");
        //This basic call get connection updates from your connections
        //https://developer.linkedin.com/documents/get-network-updates-and-statistics-api
        //specifics on updates  https://developer.linkedin.com/documents/network-update-types

        url = "http://api.linkedin.com/v1/people/~/network/updates";
        request = new OAuthRequest(Verb.GET, url);
        request.addQuerystringParameter("type","SHAR");
        request.addQuerystringParameter("type","CONN");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();




        System.out.println();
        System.out.println("********A basic user profile call and response dissected********");
        //This sample is mostly to help you debug and understand some of the scaffolding around the request-response cycle
        //https://developer.linkedin.com/documents/debugging-api-calls
        url = "https://api.linkedin.com/v1/people/~";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        //get all the headers
        System.out.println("Request headers: " + request.getHeaders().toString());
        System.out.println("Response headers: " + response.getHeaders().toString());
        //url requested
        System.out.println("Original location is: " + request.getHeaders().get("content-location"));
        //Date of response
        System.out.println("The datetime of the response is: " + response.getHeader("Date"));
        //the format of the response
        System.out.println("Format is: " + response.getHeader("x-li-format"));
        //Content-type of the response
        System.out.println("Content type is: " + response.getHeader("Content-Type") + "\n\n");

        //get the HTTP response code - such as 200 or 404
        int responseNumber = response.getCode();

        if(responseNumber >= 199 && responseNumber < 300){
            System.out.println("HOORAY IT WORKED!!");
            System.out.println(response.getBody());
        } else if (responseNumber >= 500 && responseNumber < 600){
            //you could actually raise an exception here in your own code
            System.out.println("Ruh Roh application error of type 500: " + responseNumber);
            System.out.println(response.getBody());
        } else if (responseNumber == 403){
            System.out.println("A 403 was returned which usually means you have reached a throttle limit");
        } else if (responseNumber == 401){
            System.out.println("A 401 was returned which is a Oauth signature error");
            System.out.println(response.getBody());
        } else if (responseNumber == 405){
            System.out.println("A 405 response was received. Usually this means you used the wrong HTTP method (GET when you should POST, etc).");
        }else {
            System.out.println("We got a different response that we should add to the list: " + responseNumber + " and report it in the forums");
            System.out.println(response.getBody());
        }
        System.out.println();System.out.println();


        System.out.println("********A basic error logging function********");
        // Now demonstrate how to make a logging function which provides us the info we need to
        // properly help debug issues. Please use the logged block from here when requesting
        // help in the forums.
        url = "https://api.linkedin.com/v1/people/FOOBARBAZ";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();

        responseNumber = response.getCode();

        if(responseNumber < 200 || responseNumber >= 300){
            //logDiagnostics(request, response);
        } else {
            System.out.println("You were supposed to submit a bad request");
        }

        System.out.println("******Finished******");

        */




            /*
            int max = 1000;
            int min = 1;
            Random randomNum = new Random();
            String string_showMeRand_emp = String.valueOf(showMeRand_emp);
            String string_showMeRand_emp2 = String.valueOf(showMeRand_emp2);
            String string_showMeRand_emp3 = String.valueOf(showMeRand_emp3);
            */

            //last om strings list
            if(strings[2] != null){
                countin = 3;
                /*
                for(String str: strings){
                    if(str != null){
                    }
                }
                */

                Token accessToken = null;
                //Using the Scribe library we enter the information needed to begin the chain of Oauth2 calls.
                OAuthService service = new ServiceBuilder()
                        .provider(LinkedInApi.class)
                        .apiKey(API_KEY)
                        .apiSecret(API_SECRET)
                        .callback(CALL_BACK)
                        .build();





                /*
                Token requestToken = new Token();
                String codes_twitter=Linked.getCurrentInstance().getExternalContext().getRequestParameterMap().get("oauth_verifier");
                //System.out.println("code_twitter: "+codes_twitter);
                Verifier verifier_twitter = new Verifier(codes_twitter);
                Token accessToken_twitter = service.getAccessToken(requestToken, verifier_twitter);
                token_twitter=accessToken_twitter.getToken();
                System.out.println("token_twitter : "+token_twitter);
                loginbean.token=token_twitter;
                */


                try{
                    File file = new File("service.dat");
                    if(file.exists()){
                        //if the file exists we assume it has the AuthHandler in it - which in turn contains the Access Token
                        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                        AuthHandler authHandler = (AuthHandler) inputStream.readObject();
                        accessToken = authHandler.getAccessToken();
                        return "3";
                    } else {
                        System.out.println("There is no stored Access token we need to make one");
                        //In the constructor the AuthHandler goes through the chain of calls to create an Access Token
                        AuthHandler authHandler = new AuthHandler(service);

                        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("service.dat"));
                        outputStream.writeObject( authHandler);
                        outputStream.close();
                        accessToken = authHandler.getAccessToken();
                        return "3";
                    }

                }catch (Exception e){
                    System.out.println("Threw an exception when serializing: " + e.getClass() + " :: " + e.getMessage());
                    //it shuts down the emulator when i try to acces the ui element from this different thread
                    //txt_filename_spp.setText("Threw an exception when serializing: " + "\n" + e.getClass() + " :: " + e.getMessage());
                    return "Threw an exception when serializing: " + e.getClass() + " :: " + e.getMessage();
                }
            }
            else if(strings[1] != null){
                countin = 2;
            }
            //ITS POSSIBLE THAT THIS IS THE ONLY ONE THAT RUNS SO I MUST CHECK FOR IT
            else if(strings[0] != null){
                countin = 1;





            }

            if (countin == 0){
                countin = 4;

            }


            return String.valueOf(countin);
            //return "true";
        }
    }
    //above by iggy

    private void linkedinfilePicker(String url){
        Toast.makeText(Objects.requireNonNull(getActivity()), "LinkedinFile,count="+String.valueOf(countin), Toast.LENGTH_SHORT).show();

        /*
        Temporarily blocking these two off so i can see if new code works
        LinkedInTask linkedInTask=new LinkedInTask();
        //if i put 3 strings in this it runs 3 times
        //linkedInTask.execute("");
        linkedInTask.execute(new String[]{"","",""});
        */

        //2021-11-21
        linkedIndialog = new Dialog(Objects.requireNonNull(getActivity()));
        WebView webView = new WebView(Objects.requireNonNull(getActivity()));
        //webView.isVerticalScrollBarEnabled();
        // webView.isHorizontalScrollBarEnabled();
        webView.setWebViewClient(new LinkedInWebViewClient());
        webView.getSettings().getJavaScriptEnabled();
        webView.loadUrl(url);
        linkedIndialog.setContentView(webView);
        linkedIndialog.show();
        //2021-11-21

    }

    //2
    //i remove this class from being static
    //private static class
    private class LinkedInRequestForAccessToken extends AsyncTask<Void, Void, String> {

        private String postParams;
        private WeakReference<FragmentActivity> activityReference;

        LinkedInRequestForAccessToken(FragmentActivity context, String authCode) {
            activityReference = new WeakReference<>(context);
            String grantType = "authorization_code";
            postParams = "grant_type=" + grantType + "&code=" + authCode + "&redirect_uri=" + LinkedInConstants.REDIRECT_URI + "&client_id=" + LinkedInConstants.CLIENT_ID + "&client_secret=" + LinkedInConstants.CLIENT_SECRET;
        }


        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(LinkedInConstants.TOKENURL);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setRequestProperty(
                        "Content-Type",
                        "application/x-www-form-urlencoded"
                );
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpsURLConnection.getOutputStream());
                outputStreamWriter.write(postParams);
                outputStreamWriter.flush();


                InputStream inputStream;
                // get stream
                if (httpsURLConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = httpsURLConnection.getInputStream();
                } else {
                    inputStream = httpsURLConnection.getErrorStream();
                }
                // parse stream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder response = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    response.append(temp);
                }
                org.json.JSONObject jsonObject = (JSONObject) new JSONTokener(response.toString()).nextValue();
                String accessToken = jsonObject.getString("access_token"); //The access token
                Log.e("accessToken is: ", accessToken);

                Integer expiresIn = jsonObject.getInt("expires_in"); //When the access token expires
                Log.e("expires in: ", String.valueOf(expiresIn));
                return accessToken;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // get a reference to the activity if it is still there
            FragmentActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            FetchLinkedInUserProfile task = new FetchLinkedInUserProfile(activity, result);
            task.execute();
        }
    }

    //3
    //i remove this class from being static
    //private static class FetchLinkedInUserProfile
    private class FetchLinkedInUserProfile extends AsyncTask<Void, Void, LinkedInProfileModel> {
        String tokenURLFull;
        String token;
        private WeakReference<FragmentActivity> activityReference;

        FetchLinkedInUserProfile(FragmentActivity context, String accToken) {
            activityReference = new WeakReference<>(context);
            tokenURLFull = "https://api.linkedin.com/v2/me?projection=(id,firstName,lastName,profilePicture(displayImage~:playableStreams))&oauth2_access_token=" + accToken;
            token = accToken;

        }

        @Override
        protected LinkedInProfileModel doInBackground(Void... voids) {
            try {
                URL url = new URL(tokenURLFull);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(false);
                InputStream inputStream;
                // get stream
                if (httpsURLConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = httpsURLConnection.getInputStream();
                } else {
                    inputStream = httpsURLConnection.getErrorStream();
                }
                // parse stream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder response = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    response.append(temp);
                }
                Gson gson = new GsonBuilder().create();
                return gson.fromJson(response.toString(), LinkedInProfileModel.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(LinkedInProfileModel model) {
            // get a reference to the activity if it is still there
            FragmentActivity activity = Objects.requireNonNull(getActivity());
            //MainActivity activity = activityReference.get();

            if (activity == null || activity.isFinishing()) return;
            Log.d("LinkedIn Access Token: ", token);
            //activity.accessToken = token;
            accessToken = token;

            // LinkedIn Id
            String linkedinId = model.getId();
            Log.d("LinkedIn Id: ", linkedinId);
            //activity.id = linkedinId;
            id = linkedinId;

            // LinkedIn First Name
            String linkedinFirstName = model.getFirstName().getLocalized().getEnUs();
            Log.d("LinkedIn First Name: ", linkedinFirstName);
            //activity.firstName = linkedinFirstName;
            firstName = linkedinFirstName;
            // LinkedIn Last Name
            String linkedinLastName = model.getLastName().getLocalized().getEnUs();
            Log.d("LinkedIn Last Name: ", linkedinLastName);
            //activity.lastName = linkedinLastName;
            lastName = linkedinLastName;

            // LinkedIn Profile Picture URL
            /*
                 Change row of the 'elements' array to get diffrent size of the profile pic
                 elements[0] = 100x100
                 elements[1] = 200x200
                 elements[2] = 400x400
                 elements[3] = 800x800
            */

            String linkedinProfilePic = model.getProfilePicture().getDisplayImage().getElements().get(2).getIdentifiers().get(0).getIdentifier();
            Log.d("LinkedIn Profile URL: ", linkedinProfilePic);
            //activity.profilePicURL = linkedinProfilePic;
            profilePicURL = linkedinProfilePic;
            // Get user's email address
            FetchLinkedInEmailAddress task = new FetchLinkedInEmailAddress(activity, token);
            task.execute();
        }
    }

    //4
    //i remove this class from being static
    //private static class
    private class FetchLinkedInEmailAddress extends AsyncTask<Void, Void, LinkedInEmailModel> {
        String tokenURLFull;
        String token;
        private WeakReference<FragmentActivity> activityReference;

        //FragmentActivity was MainActivity
        FetchLinkedInEmailAddress(FragmentActivity context, String accToken) {
            //activityReference = new WeakReference<>(context);
            activityReference = new WeakReference<>(context);
            tokenURLFull = "https://api.linkedin.com/v2/emailAddress?q=members&projection=(elements*(handle~))&oauth2_access_token=" + accToken;
            token = accToken;
        }

        @Override
        protected LinkedInEmailModel doInBackground(Void... voids) {
            try {
                URL url = new URL(tokenURLFull);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(false);
                InputStream inputStream;
                // get stream
                if (httpsURLConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = httpsURLConnection.getInputStream();
                } else {
                    inputStream = httpsURLConnection.getErrorStream();
                }
                // parse stream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder response = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    response.append(temp);
                }
                Gson gson = new GsonBuilder().create();
                return gson.fromJson(response.toString(), LinkedInEmailModel.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        //@RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(LinkedInEmailModel model) {
            // get a reference to the activity if it is still there
            FragmentActivity activity = Objects.requireNonNull(getActivity());
            //MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            // LinkedIn Email
            String linkedinEmail = model.getElements().get(0).getHandle().getEmailAddress();
            Log.d("LinkedIn Email: ", linkedinEmail);
            //activity.email = linkedinEmail;
            email = linkedinEmail;

            //activity.
            openDetailsActivity();
        }
    }

    /**
     * function to download the image from url
     */

    public static Bitmap getBitmapFromURL(String aURL) {
        URL lBitmapURL;
        Bitmap lBitmap = null;
        try {
            lBitmapURL = new URL(aURL);
            //InputStream lInStream = lBitmapURL.openStream();
            //lBitmap = BitmapFactory.decodeStream(lInStream);

            HttpURLConnection connection = (HttpURLConnection) lBitmapURL.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            //Bitmap myBitmap = BitmapFactory.decodeStream(input);
            lBitmap = BitmapFactory.decodeStream(input);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return lBitmap;
    }

    public class UploadLinkedinTask extends AsyncTask<String,String,String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("1")){
                Toast.makeText(Objects.requireNonNull(getActivity()), "1. Linkedin File uploaded", Toast.LENGTH_SHORT).show();
                System.out.println("HELLOOOOOOO");
            }
            else if (s.equalsIgnoreCase("2")){
                Toast.makeText(Objects.requireNonNull(getActivity()), "2. Linkedin File Uploaded", Toast.LENGTH_SHORT).show();
                System.out.println("HELLOOOOOOO");
            }
            else if(s.equalsIgnoreCase("3")){
                Toast.makeText(Objects.requireNonNull(getActivity()), "3. File Uploaded", Toast.LENGTH_SHORT).show();
                System.out.println("HELLOOOOOOO");
            }
            else{
                Toast.makeText(Objects.requireNonNull(getActivity()), "Failed Upload", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if(strings[2] != null){
                Toast.makeText(Objects.requireNonNull(getActivity()), "do in background", Toast.LENGTH_SHORT).show();
                try{
                    java.security.Security.setProperty("networkaddress.cache.ttl" , "60");
                    String bucketName = "mythryvebucket-2021";
                    ClientConfiguration clientConfiguration = new ClientConfiguration();
                    clientConfiguration.setMaxErrorRetry(0);
                    clientConfiguration.setConnectionTimeout(3600000);
                    clientConfiguration.setSocketTimeout(3600000);
                    BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("AKIASBEZPPNHMQM4BIGL", "a5AhmoV1Pl3WfsNpDwZO73opGGD29ah3S+MUmkdF");
                    AmazonS3Client amazonS3Client = new AmazonS3Client(basicAWSCredentials, clientConfiguration);
                    PutObjectRequest objectRequest = new PutObjectRequest(bucketName,linkedinFile.getName() +':'+':'+ ':' ,linkedinFile);
                    amazonS3Client.putObject(objectRequest);
                }catch (Exception e){
                    Toast.makeText(Objects.requireNonNull(getActivity()), "not working"+ e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            return String.valueOf('3');
            //return "true";
        }




    }

    public void uploadLinkedinImage(){
        UploadLinkedinTask uploadTask=new UploadLinkedinTask();
        uploadTask.execute(new String[]{"", "", ""});
    }

    private void openDownloadedAttachment(final Context context, Uri attachmentUri, final String attachmentMimeType) {
        if(attachmentUri!=null) {
            // Get Content Uri.
            if (ContentResolver.SCHEME_FILE.equals(attachmentUri.getScheme())) {
                // FileUri - Convert it to contentUri.
                File file = new File(attachmentUri.getPath());
                attachmentUri = FileProvider.getUriForFile(Objects.requireNonNull(getActivity()), "com.myappcompany.thea.mobileappthryve", file);;
            }

            Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
            openAttachmentIntent.setDataAndType(attachmentUri, attachmentMimeType);
            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                context.startActivity(openAttachmentIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "unable to open file", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openDownloadedAttachment(final Context context, final long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String downloadMimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
            if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
                openDownloadedAttachment(context, Uri.parse(downloadLocalUri), downloadMimeType);
            }
        }
        cursor.close();
    }

    BroadcastReceiver attachmentDownloadCompleteReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, 0);

                //openDownloadedAttachment(context, downloadId);
                DownloadManager dm = (DownloadManager) Objects.requireNonNull(getActivity()).getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uriOfDownloadedFile = dm.getUriForDownloadedFile(downloadId);
                linkedinFile = new File(uriOfDownloadedFile.toString());

                txt_filename_spp.setText(txt_filename_spp.getText() + "\n" +"File name: " +linkedinFile.getName() + " & File path :" + linkedinFile.getAbsolutePath());


                //links that helped with sollution:
                //
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(uriOfDownloadedFile));
                    img_photo_spp.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //uploadLinkedinImage();
            }
        }
    };

    //5
    //@RequiresApi(api = Build.VERSION_CODES.O)
    public void openDetailsActivity() {
        //
        try{
            /*
            //1
            URL t = new URL(profilePicURL);
            InputStream in = t.openStream();
            //utilized https://www.geeksforgeeks.org/how-to-load-any-image-from-url-without-using-any-dependency-in-android/
            //& https://stackoverflow.com/questions/12771500/best-way-of-creating-and-using-an-anonymous-runnable-class
            Bitmap image = BitmapFactory.decodeStream(in);
            Handler handler = new Handler(Looper.getMainLooper());

            handler.post(new Runnable() {
                @Override
                public void run() {
                    img_photo_spp.setImageBitmap(image);
                }
            });
            */

            /*
            //2
            DownloadManager manager;
            manager = (DownloadManager) getSystemService(Objects.requireNonNull(this.getContext()).DOWNLOAD_SERVICE));
            Uri uri = Uri.parse("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf");
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            long reference = manager.enqueue(request);
            */

            //Uri uri = Uri.parse(t);
            //Uri t = (Uri)profilePicURL;
            String linkedinppfilename = "filenamelinkedinpic";
            URL p = new URL(profilePicURL);
            URI profilePicURL_URI = p.toURI();
            Uri myUri = Uri.parse(String.valueOf(profilePicURL_URI));

            Objects.requireNonNull(getActivity()).registerReceiver(attachmentDownloadCompleteReceive, new IntentFilter(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            DownloadManager.Request request = new DownloadManager.Request(myUri);
            request.setMimeType(getMimeType(myUri.toString()));
            request.setTitle(linkedinppfilename);
            request.setDescription("Downloading attachment..");
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, linkedinppfilename);
            DownloadManager dm = (DownloadManager) Objects.requireNonNull(getActivity()).getSystemService(Context.DOWNLOAD_SERVICE);
            dm.enqueue(request);



            //first way
            /*
            URL t = new URL(profilePicURL);
            URI h = t.toURI();
            linkedinFile = new File(h.getPath());
            txt_filename_spp.setText(txt_filename_spp.getText() + "\n" +linkedinFile.getName());
            //i think this is not working cus the image is not downloaded
            if(linkedinFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(linkedinFile.getAbsolutePath());
                img_photo_spp.setImageBitmap(myBitmap);
            }
            */
        }
        catch(IOException | URISyntaxException e){
        }
        //uploadLinkedinImage();

        /*
            try {

                //Url ppurl = new android.net.url(UrlprofilePicURL);
                //String filePath=getRealPathFromUri((Uri)ppurl,Objects.requireNonNull(getActivity()));
                //ppurl.toURI()
            }
            catch(MalformedURLException | URISyntaxException g){

            }
            */
            //.toURI()
            //String filePath=getRealPathFromUri((Uri)profilePicURL,Objects.requireNonNull(getActivity()));
            //Log.d("File Path : "," "+filePath);
            //now we will upload the file

            //this.file_path1_spp=filePath;
            //File file=new File(filePath);

        //txt_filename_spp.setText("filename: " + "\n" +profilePicURL);
        /*
        URL lBitmapURL;
        try {
            lBitmapURL = new URL(profilePicURL);
            File linkedinFile = Paths.get(lBitmapURL.toURI()).toFile();
            //Bitmap bitmap = getBitmapFromURL(profilePicURL);
            txt_filename_spp.setText(txt_filename_spp.getText() + "\n" +linkedinFile.getName());

        }
        catch(IOException | URISyntaxException e){
            Toast.makeText(Objects.requireNonNull(getActivity()), "not working"+ e.toString(), Toast.LENGTH_SHORT).show();
        }
        */

        //try {

            //the file may not exist outside the try so i do all the code here
            //File linkedinFile = new File("thelinkedinimage");
            //OutputStream os = new BufferedOutputStream(new FileOutputStream(linkedinFile));
            //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            //os.close();



            /*
            //im blocking this out to see if this aint working cus the no needed imports
            String bucketName = "mythryvebucket-2021";
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            clientConfiguration.setMaxErrorRetry(0);
            clientConfiguration.setConnectionTimeout(3600000);
            clientConfiguration.setSocketTimeout(3600000);

            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("AKIASBEZPPNHMQM4BIGL", "a5AhmoV1Pl3WfsNpDwZO73opGGD29ah3S+MUmkdF");
            AmazonS3Client amazonS3Client = new AmazonS3Client(basicAWSCredentials, clientConfiguration);
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,linkedinFile.getName() +':'+':'+ ':' ,linkedinFile);
            amazonS3Client.putObject(objectRequest);
            */

            //txt_filename_spp.setText(txt_filename_spp.getText() + "\n" +linkedinFile.getName());
        //}
        //catch(IOException e){
        //    Toast.makeText(Objects.requireNonNull(getActivity()), "not working"+ e.toString(), Toast.LENGTH_SHORT).show();
        //}





        //myIntent.putExtra("linkedin_id", id);
        //myIntent.putExtra("linkedin_first_name", firstName);
        //myIntent.putExtra("linkedin_last_name", lastName);
        //myIntent.putExtra("linkedin_email", email);
        //myIntent.putExtra("linkedin_profile_pic_url", profilePicURL);
        //myIntent.putExtra("linkedin_access_token", accessToken);
        //startActivity(myIntent);
    }

    //1
    // A client to know about WebView navigations
    class LinkedInWebViewClient extends WebViewClient {

        // For API 21 and above
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl().toString().startsWith(LinkedInConstants.REDIRECT_URI)) {
                handleUrl(request.getUrl().toString());

                // Close the dialog after getting the authorization code
                if (request.getUrl().toString().contains("?code=")) {
                    linkedIndialog.dismiss();
                }
                return true;
            }
            return false;
        }


        // For API 19 and below
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(LinkedInConstants.REDIRECT_URI)) {
                handleUrl(url);

                // Close the dialog after getting the authorization code
                if (url.contains("?code=")) {
                    linkedIndialog.dismiss();
                }
                return true;
            }
            return false;
        }

        // Check webview url for access token code or error
        private void handleUrl(String url) {
            Uri uri = Uri.parse(url);
            if (url.contains("code")) {
                linkedinCode = uri.getQueryParameter("code");
                LinkedInRequestForAccessToken task = new LinkedInRequestForAccessToken(Objects.requireNonNull(getActivity()), linkedinCode);
                task.execute();
            } else if (url.contains("error")) {
                String error = uri.getQueryParameter("error");
                if (error != null) {
                    Log.e("Error: ", error);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        Toast.makeText(Objects.requireNonNull(getActivity()), "In ActivityResult", Toast.LENGTH_SHORT).show();
        //
        if(requestCode==REQUEST_GALLERY_SPP && resultCode== Activity.RESULT_OK){
            //the first param of this is the url
            //the true path of the file is found in variable filePath
            String filePath=getRealPathFromUri(data.getData(),Objects.requireNonNull(getActivity()));
            Log.d("File Path : "," "+filePath);
            //now we will upload the file
            //lets import okhttp first
            //File file=new File(filePath);
            //file_name.setText(file_name.getText() + "\n" +file.getName());
            linkedinFile = new File(filePath);
            txt_filename_spp.setText(txt_filename_spp.getText() + "\n" +"File name: " +linkedinFile.getName() + " & File path :" + linkedinFile.getAbsolutePath());
            //links that helped with sollution:
            //
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(data.getData()));
                img_photo_spp.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    private void filePicker(){
        //Toast.makeText(this, "File Picker Called", Toast.LENGTH_SHORT).show();
        Toast.makeText(Objects.requireNonNull(getActivity()), "File Picker Call", Toast.LENGTH_SHORT).show();
        //Let's Pick File
        Intent opengallery=new Intent(Intent.ACTION_PICK);
        opengallery.setType("image/*");
        startActivityForResult(opengallery,REQUEST_GALLERY_SPP);
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
}



