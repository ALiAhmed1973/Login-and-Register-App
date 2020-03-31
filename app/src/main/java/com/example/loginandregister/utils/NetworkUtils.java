package com.example.loginandregister.utils;

import android.net.Uri;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    public static final String USERS_URL="http://alnafizah.com/Khalid_POS.com/V1/test/users.php";
    public static final String LOG_IN_URL="http://alnafizah.com/Khalid_POS.com/V1/test/login.php";
    public static final String REGISTER_URL="http://alnafizah.com/Khalid_POS.com/V1/test/signup.php";
    private static final String LOG_IN_USER_NAME_KEY="user_name";
    private static final String LOG_IN_PASS_KEY="user_pass";




    public static URL buildUrl(String baseUrl)
    {
        URL url = null;
        try {
            url = new URL(baseUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String buildLoginUri(String userName,String pass)
    {
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(pass))
        {
            return "";
        }
        Uri.Builder builder= new Uri.Builder().
                appendQueryParameter(LOG_IN_USER_NAME_KEY,userName).
                appendQueryParameter(LOG_IN_PASS_KEY,pass);

        String s = builder.build().getEncodedQuery();
        return s;
    }
    public static String GetResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection httpURLConnection = null;
        String jsonResponse = "";

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();

            if(httpURLConnection.getResponseCode()==200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            httpURLConnection.disconnect();
        }
        return jsonResponse;
    }

    public static String SendDataHttpUrl(URL url,String data) throws IOException{
        HttpURLConnection httpURLConnection = null;
        String jsonResponse = "";

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");



            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            outputStream.close();
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode()==200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            httpURLConnection.disconnect();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\A");

        boolean hasInput = scanner.hasNext();
        if (hasInput) {
            return scanner.next();
        } else {
            return null;
        }
    }

}
