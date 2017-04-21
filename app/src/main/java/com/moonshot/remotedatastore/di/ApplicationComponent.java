package com.moonshot.remotedatastore.di;

import android.content.Context;

import com.moonshot.remotedatastore.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by armando_contreras on 4/21/17.
 */

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    //Exposed to sub-graphs.
    Context context();

    SchedulerProvider scheduler();

}