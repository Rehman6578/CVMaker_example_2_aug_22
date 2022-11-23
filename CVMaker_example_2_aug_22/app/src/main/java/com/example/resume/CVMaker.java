package com.example.resume;

import android.app.Application;
import androidx.multidex.MultiDex;

import com.example.resume.dagger2.AppComponent;
import com.example.resume.dagger2.AppModule;


import io.realm.Realm;
import io.realm.RealmConfiguration;



public class CVMaker extends Application {

   private AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
