package com.moonshot.remotedatastore;

import com.moonshot.remotedatastore.data.source.RepositoryComponent;
import com.moonshot.remotedatastore.di.FragmentScoped;

import dagger.Component;

/**
 * Created by armando_contreras on 4/21/17.
 */

@FragmentScoped
@Component(dependencies = RepositoryComponent.class, modules = HomePresenterModule.class)
public interface HomeComponent {

    void inject(HomeActivity activity);

}
