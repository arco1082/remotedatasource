package com.moonshot.remotedatastore.di;

import android.content.Context;

import com.moonshot.remotedatastore.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by armando_contreras on 4/21/17.
 */

@Module
public class ApplicationModule {

    private final Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides @Singleton
    SchedulerProvider providesScheduler() {
        return new SchedulerProvider();
    }

}