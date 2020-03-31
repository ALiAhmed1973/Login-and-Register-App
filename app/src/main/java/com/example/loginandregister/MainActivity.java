package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.loginandregister.data.User;
import com.example.loginandregister.database.AppDataBase;
import com.example.loginandregister.utils.AppExecutors;
import com.example.loginandregister.utils.NetworkUtils;
import com.example.loginandregister.utils.UsersJsonUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<List<User>> {
    Button btLoginPage,btRegisterPage;
    private static final int LOADER_ID = 10;
    AppDataBase dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase = AppDataBase.getInstance(getApplicationContext());


        getSupportLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
        btLoginPage = findViewById(R.id.bt_login_page);
        btLoginPage.setOnClickListener(this);
        btRegisterPage = findViewById(R.id.bt_register_page);
        btRegisterPage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {

            case R.id.bt_login_page:
                intent  = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_register_page:
                intent  = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @NonNull
    @Override
    public Loader<List<User>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<User>>(this) {
            @Nullable
            @Override
            public List<User> loadInBackground() {

                try {
                    URL url = NetworkUtils.buildUrl(NetworkUtils.USERS_URL);
                    String response = NetworkUtils.GetResponseFromHttpUrl(url);

                    List<User> users = UsersJsonUtils.GetUsersFromJson(response);

                    return users;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<User>> loader, final List<User> data) {
        if(data !=null&& !data.isEmpty()) {
            AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    dataBase.userDao().DeleteAllUsers();
                    dataBase.userDao().InsertUsersList(data);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<User>> loader) {

    }
}
