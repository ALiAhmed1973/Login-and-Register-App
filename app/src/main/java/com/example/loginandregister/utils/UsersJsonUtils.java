package com.example.loginandregister.utils;

import android.text.TextUtils;

import com.example.loginandregister.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsersJsonUtils {

    private static final String USER_ID_JSON_KEY_NAME ="user_id";
    private static final String USER_NAME_JSON_KEY_NAME ="user_name";
    private static final String USER_PASS_JSON_KEY_NAME ="user_pass";


    public static List<User> GetUsersFromJson(String json)
    {
        if(TextUtils.isEmpty(json))
        {
            return null;
        }

        List<User> users = new ArrayList<>();
        try {

            JSONArray usersArray = new JSONArray(json);
            for (int i =0;i<usersArray.length();i++)
            {
                JSONObject currentUser = usersArray.getJSONObject(i);
                String userId = currentUser.optString(USER_ID_JSON_KEY_NAME);
                String userName = currentUser.optString(USER_NAME_JSON_KEY_NAME);
                String userPass =currentUser.getString(USER_PASS_JSON_KEY_NAME);

                User user = new User(userId,userName,userPass);
                users.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }
}
