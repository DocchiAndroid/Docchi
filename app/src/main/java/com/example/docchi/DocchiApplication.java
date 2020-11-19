package com.example.docchi;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class DocchiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("M0vZHfDXtFzMQng8gZEQdDa4OthaJenROvGoUmR5")
                .clientKey("V5gos2kxi7uJr7lU01Yp7mqAKWTxMGrVxR5Nz7Lq")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
