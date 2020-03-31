package com.example.loginandregister.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_info")
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private String userID;
    @ColumnInfo(name = "user_name")
    private String userName;
    @ColumnInfo(name = "user_pass")
    private String userPassword;

    @Ignore
    public User(String userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }
    @Ignore
    public User(String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }
    @Ignore
    public User() {
    }

    public User(int id, String userID, String userName, String userPassword) {
        this.id = id;
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }


    protected User(Parcel in) {
        id = in.readInt();
        userID = in.readString();
        userName = in.readString();
        userPassword = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userID);
        dest.writeString(userName);
        dest.writeString(userPassword);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
