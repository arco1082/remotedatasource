package com.moonshot.remotedatastore;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.moonshot.remotedatastore.data.models.IRemoteModel;

import java.util.List;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeActivity extends AppCompatActivity {
    @Inject
    HomePresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        HomeActivityFragment fragment =
                (HomeActivityFragment) getSupportFragmentManager().findFragmentById(R.id.main_content);

        if (fragment == null) {
            // Create the fragment
            fragment = HomeActivityFragment.createNew();
            checkNotNull(getSupportFragmentManager());
            checkNotNull(fragment);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content, fragment);
            transaction.commit();
        }

        DaggerHomeComponent.builder()
                .repositoryComponent(((RemoteDataSourceApplication) getApplication()).getRepositoryComponent())
                .homePresenterModule(new HomePresenterModule(fragment)).build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
