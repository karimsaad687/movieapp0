package com.example.karim.movieapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by karim on 3/25/2016.
 */
public class reviewTask extends AsyncTask<String,String,Void> {

    Context c1;
    TextView t1;
    JSONArray result=null;
    ScrollView scrollView;
    public reviewTask(Context c,TextView textView,ScrollView scrollView11){
        c1=c;
        t1=textView;
        scrollView=scrollView11;
    }
    //https://api.themoviedb.org/3/movie/209112/reviews?api_key=3ac8f9f5b4e3893b101b07640455233f
    @Override
    protected Void doInBackground(String... params) {
        String w="";
        try {

            URL url=new URL("https://api.themoviedb.org/3/movie/" + params[0] + "/reviews?api_key=3ac8f9f5b4e3893b101b07640455233f");
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
            result= reader1.getJSONArray("results");
            if(result.length()>0) {
                JSONObject movies = result.getJSONObject(0);
                String re = movies.getString("content");
                publishProgress(re);
            }else
                publishProgress("No Reviews Yet");
        }catch (Exception e){
            publishProgress("error "+e.getMessage());

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        scrollView.scrollTo(0,0);
    }

    @Override
    protected void onProgressUpdate(String... values) {
       // super.onProgressUpdate(values[0]);
        t1.setText(values[0]);
    }
}
