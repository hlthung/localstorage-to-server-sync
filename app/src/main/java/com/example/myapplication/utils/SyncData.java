package com.example.myapplication.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SyncData extends AsyncTask<Void, Void, String> {

    private Context context;

    public SyncData(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        String response = "";
        try {
            // Get data from SQLite database
            LocalDatabaseHelper myDB = new LocalDatabaseHelper(context);
            List<User> dataList = myDB.getData();

            // Create a Gson object
            Gson gson = new Gson();
            String json = gson.toJson(dataList);

            // Send data to server
            URL url = new URL(context.getString(R.string.server_url) + "auth/sync-sample");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

            // Get response from server
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuffer responseBuffer = new StringBuffer();

                while ((line = in.readLine()) != null) {
                    responseBuffer.append(line);
                }
                in.close();

                response = responseBuffer.toString();

                // Clear data from SQLite database
                myDB.clearData();
            } else {
                response = "Error syncing data to server";
            }

        } catch (Exception e) {
            response = "Error syncing data to server: " + e.getMessage();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        // Handle response from server
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
    }
}