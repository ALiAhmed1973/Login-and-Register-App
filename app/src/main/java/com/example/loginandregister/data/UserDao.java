package com.example.loginandregister.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user_info ORDER BY id")
    LiveData<List<User>> LoadAllUsers();

    @Insert
    void InsertUser(User user);

    @Insert
    void InsertUsersList(List<User> users);

    @Delete
    void DeleteUser(User user);

    @Query("DELETE FROM user_info")
    void DeleteAllUsers();

    @Query("SELECT * FROM user_info WHERE user_name = :name")
    LiveData<User> LoadUserByName(String name);

}
