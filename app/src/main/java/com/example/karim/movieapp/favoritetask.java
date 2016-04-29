package com.example.karim.movieapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.test.ActivityUnitTestCase;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by karim on 4/5/2016.
 */
public class favoritetask extends AsyncTask<String,String,Void> {
    Context context;
    String[] all=new String[6];
    Activity activity;
    public favoritetask(Activity activity,Context context){
        this.context=context;
        this.activity=activity;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            //publishProgress("start");

            String url1="https://api.themoviedb.org/3/movie/"+params[0]+"?api_key="+BuildConfig.MY_KEY;
            URL url=new URL(url1);
            BufferedReader reader = null;
            HttpURLConnection urlConnection = null;

            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream=urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
                return null;


            reader = new BufferedReader(new InputStreamReader(inputStream));


            String line;
            int h=0;
            String[] list=null;
            String[] day=null;
            String wo="";
            String wo2="";
            while ((line = reader.readLine()) != null) {
                wo += line;
            }
            JSONObject reader1=new JSONObject(wo);
            all[0]=reader1.getString("id");
            all[1]=reader1.getString("overview");
            all[2]=reader1.getString("release_date");
            all[3]=reader1.getString("original_title");
            all[4]=reader1.getString("poster_path");
            all[5]=reader1.getString("vote_average");
               // publishProgress(reader1.getString("id"));
        }catch (Exception e){
            publishProgress("error "+e.getMessage());

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        try {
            
            context.startActivity(new Intent(context, details.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtra("poster", all[4])
                    .putExtra("overview", all[1])
                    .putExtra("name", all[3])
                    .putExtra("release", all[2])
                    .putExtra("id", all[0])
                    .putExtra("votes", all[5]));
        }catch (Exception e1){
            Toast.makeText(context, e1.getMessage(),Toast.LENGTH_SHORT).show();
        }
        //super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Toast.makeText(context,values[0],Toast.LENGTH_SHORT).show();

    }
    //https://api.themoviedb.org/3/movie/209112?api_key=3ac8f9f5b4e3893b101b07640455233f
}
