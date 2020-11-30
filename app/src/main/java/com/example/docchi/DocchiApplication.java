package com.example.docchi;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class DocchiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Images.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("M0vZHfDXtFzMQng8gZEQdDa4OthaJenROvGoUmR5")
                .clientKey("V5gos2kxi7uJr7lU01Yp7mqAKWTxMGrVxR5Nz7Lq")
                .server("https://parseapi.back4app.com")
                .build()
        );

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        Log.d("ParseNotif", "hello" + installation.getInstallationId());
        installation.put("GCMSenderId", "151705258392");
        installation.saveInBackground();
    }
}
