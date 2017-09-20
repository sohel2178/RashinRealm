package com.example.sohel.rashinrealm;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by sohel on 20-09-17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
