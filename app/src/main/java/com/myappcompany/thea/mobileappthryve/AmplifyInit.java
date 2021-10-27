package com.myappcompany.thea.mobileappthryve;


import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
//import com.amplifyframework.core.Amplify.configure;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
//import com.amplifyframework.util.UserAgent.configure;

public class AmplifyInit {


    //i added the static
    public static void intializeAmplify(Context Context){
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(Context);
            Log.d("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }
    }
}
