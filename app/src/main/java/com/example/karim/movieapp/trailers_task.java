package com.example.karim.movieapp;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by karim on 3/26/2016.
 */
public class trailers_task extends AsyncTask<String,String,Void> {
    Context context;
    ListView listView;
    LinkedList<String> linkedList;
    JSONArray result=null;
    ScrollView scrollView;
    TextView title;
    public trailers_task(Context context,ListView listView,LinkedList<String> linkedList,ScrollView scrollView,TextView textView){
        this.context=context;
        this.listView=listView;
        this.linkedList=linkedList;
        this.scrollView=scrollView;
        title=textView;
    }
    @Override
    protected Void doInBackground(String... params) {
        String prog="";
        try{

        URL url=new URL("https://api.themoviedb.org/3/movie/" + params[0] + "/videos?api_key=3ac8f9f5b4e3893b101b07640455233f");
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
        String wo="";
        while ((line = reader.readLine()) != null) {
            wo += line;
        }
        JSONObject reader1=new JSONObject(wo);

        result= reader1.getJSONArray("results");

            publishProgress("done");
    }catch (Exception e){
        publishProgress("error "+e.getMessage());

    }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            String[] sites = new String[result.length()];

            if (result.length() > 0) {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject movies = result.getJSONObject(i);
                    String re = movies.getString("key");
                    String site = movies.getString("site")+" --> "+movies.getString("name");
                    sites[i] = site;
                    linkedList.addLast(re);
                }
                trailer_list trailer_list = new trailer_list(context, sites);
                listView.setAdapter(trailer_list);
                //publishProgress(re);
                title.setText("Trailers");
            } else {
                title.setText("No Trailers Available");
                listView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }


    scrollView.scrollTo(0,0);
    }

    @Override
    protected void onProgressUpdate(String... values) {


    }
}
