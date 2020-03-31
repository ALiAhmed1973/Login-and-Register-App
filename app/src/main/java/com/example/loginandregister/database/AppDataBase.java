package com.example.loginandregister.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.loginandregister.data.User;
import com.example.loginandregister.data.UserDao;

@Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private  static final String TAG = AppDataBase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "user_list";
    private static AppDataBase sInstance;

    public static AppDataBase getInstance(Context context)
    {
        if(sInstance==null)
        {
            synchronized (LOCK){
                Log.d(TAG,"Creating New Database");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,
                        AppDataBase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract UserDao userDao();
}
