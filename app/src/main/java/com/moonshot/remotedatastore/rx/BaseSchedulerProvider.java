package com.moonshot.remotedatastore.rx;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by armando_contreras on 4/21/17.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();



}
