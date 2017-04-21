package com.moonshot.remotedatastore;

import dagger.Module;
import dagger.Provides;

/**
 * Created by armando_contreras on 4/21/17.
 */

@Module
public class HomePresenterModule {

    private final HomeContract.HomeView mView;

    public HomePresenterModule(HomeContract.HomeView view) {
        mView = view;
    }

    @Provides
    HomeContract.HomeView provideHomeContractView() {
        return mView;
    }

}