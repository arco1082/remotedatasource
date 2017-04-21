package com.moonshot.remotedatastore.data.source;

import com.moonshot.remotedatastore.di.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by armando_contreras on 4/21/17.
 */

@Singleton
@Component(modules = {DataRepositoryModule.class, ApplicationModule.class})
public interface RepositoryComponent {

    DataRepository getDataRepository();
}

