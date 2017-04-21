package com.moonshot.remotedatastore;

import android.app.Application;

import com.moonshot.remotedatastore.data.source.DaggerRepositoryComponent;
import com.moonshot.remotedatastore.data.source.RepositoryComponent;
import com.moonshot.remotedatastore.di.ApplicationModule;

/**
 * Created by armando_contreras on 4/21/17.
 */

public class RemoteDataSourceApplication extends Application {
    private RepositoryComponent mRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    protected void initializeInjector() {
        mRepositoryComponent = DaggerRepositoryComponent.builder()
                .applicationModule(new ApplicationModule((RemoteDataSourceApplication) getApplicationContext()))
                .build();
    }

    public RepositoryComponent getRepositoryComponent() {
        return mRepositoryComponent;
    }

}

