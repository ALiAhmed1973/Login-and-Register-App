package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginandregister.data.User;
import com.example.loginandregister.utils.AppExecutors;
import com.example.loginandregister.utils.MainActivityViewModel;
import com.example.loginandregister.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
EditText editTextUserName,editTextUserPass;
Button buttonLogin;
private LoaderManager.LoaderCallbacks<User> login;
public static final  String USER_EXTRA_KEY="user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUserName = findViewById(R.id.et_login_user_name);
        editTextUserPass = findViewById(R.id.et_login_password);
        buttonLogin = findViewById(R.id.bt_login);

        setupViewModel();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = new LoaderManager.LoaderCallbacks<User>() {
                    @NonNull
                    @Override
                    public Loader<User> onCreateLoader(int id, @Nullable Bundle args) {
                        return  new AsyncTaskLoader<User>(LoginActivity.this) {
                            @Nullable
                            @Override
                            public User loadInBackground() {
                                URL url = NetworkUtils.buildUrl(NetworkUtils.LOG_IN_URL);
                                String data = NetworkUtils.buildLoginUri(editTextUserName.getText().toString(),editTextUserPass.getText().toString());
                                try {
                                    String response = NetworkUtils.SendDataHttpUrl(url,data);

                                    if(response.contains("user_id"))
                                    {
                                        User user = getDataJson(response);
                                        return user;
                                    }
                                    else if(response.contains("error_id"))
                                    {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                                builder.setMessage("الإسم غير صحيح أو كلمة السر").
                                                        setNegativeButton("حاول مره أخرى",null).create().show();
                                            }
                                        });

                                        return null;

                                    }else
                                    {
                                        return null;
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }
                        };
                    }

                    @Override
                    public void onLoadFinished(@NonNull Loader<User> loader, User data) {
                        if(data != null)
                        {
                            Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                            intent.putExtra(USER_EXTRA_KEY,data);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onLoaderReset(@NonNull Loader<User> loader) {

                    }
                };
                getSupportLoaderManager().initLoader(10,null,login).forceLoad();
            }
        });
    }

    public User getDataJson(String json)
    {
        if(TextUtils.isEmpty(json))
        {
            return null;
        }
        User user = new User();

        try {
            JSONObject jsonObject = new JSONObject(json);
            String userId = jsonObject.optString("user_id");
            String userName = jsonObject.optString("user_name");
           user.setUserID(userId);
           user.setUserName(userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    private void setupViewModel()
    {

        MainActivityViewModel model = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        model.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                if(users!=null&&!users.isEmpty()) {
                    Log.d("fffffffffffffff",users.get(5).getUserName());
                    Log.d("fffffffffffffff", String.valueOf(users.size()));
                }else
                {
                    Log.d("fffffffffffffff","WRONG");
                }

            }
        });
    }
}
