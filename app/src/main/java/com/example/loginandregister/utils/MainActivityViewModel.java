package com.example.loginandregister.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.loginandregister.data.User;
import com.example.loginandregister.database.AppDataBase;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private LiveData<List<User>> users;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        AppDataBase appDatabase = AppDataBase.getInstance(this.getApplication());
        users=appDatabase.userDao().LoadAllUsers();
    }

    public LiveData<List<User>> getUsers()
    {
        return users;
    }
}
