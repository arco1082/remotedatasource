package com.moonshot.remotedatastore.data.source;

import android.content.Context;

import com.moonshot.remotedatastore.data.source.local.LocalDataSource;
import com.moonshot.remotedatastore.data.source.remote.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by armando_contreras on 4/21/17.
 */

@Module
public class DataRepositoryModule {

    @Singleton
    @Provides
    @Remote
    DataSource provideRemoteDataSource() {
        return new RemoteDataSource();
    }

    @Singleton
    @Provides
    @Local
    DataSource provideLocalDataSource(Context context) {
        return new LocalDataSource(context);
    }

}

