package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginandregister.data.User;
import com.example.loginandregister.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextUserName,editTextUserPass,editTextUserPassCheck;
    Button buttonRegister;
    private LoaderManager.LoaderCallbacks<String> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextUserName = findViewById(R.id.et_register_user_name);
        editTextUserPass = findViewById(R.id.et_register_password);
        editTextUserPassCheck = findViewById(R.id.et_register_password_check);
        buttonRegister = findViewById(R.id.bt_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextUserPass.getText().toString().equals(editTextUserPassCheck.getText().toString())&&
                        !TextUtils.isEmpty(editTextUserPass.getText().toString())) {
                    register = new LoaderManager.LoaderCallbacks<String>() {
                        @NonNull
                        @Override
                        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
                            return new AsyncTaskLoader<String>(RegisterActivity.this) {
                                @Nullable
                                @Override
                                public String loadInBackground() {
                                    URL url = NetworkUtils.buildUrl(NetworkUtils.REGISTER_URL);
                                    String data = NetworkUtils.buildLoginUri(editTextUserName.getText().toString(), editTextUserPass.getText().toString());

                                    String response = null;
                                    String message="";
                                    try {
                                        response = NetworkUtils.SendDataHttpUrl(url, data);
                                        if(response.contains("response_id"))
                                        {

                                             message ="Success";
                                            return message;
                                        }
                                        else if(response.contains("error_id"))
                                        {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                                    builder.setMessage("الإسم موجود سابقاً").
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
                        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
                            if(data!=null)
                            {
                                Toast.makeText(RegisterActivity.this,data,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this,WelcomeActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onLoaderReset(@NonNull Loader<String> loader) {

                        }
                    };
                    getSupportLoaderManager().initLoader(10, null, register).forceLoad();
                }else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("كلمة السر غير صحيحة").
                            setNegativeButton("حاول مره أخرى",null).create().show();
                }
            }
        });
    }
}
