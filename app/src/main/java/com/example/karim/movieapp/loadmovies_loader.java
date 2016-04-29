package com.example.karim.movieapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by karim on 4/8/2016.
 */
public class loadmovies_loader extends AsyncTaskLoader<LinkedList<String[]>> {



    GridView l;
    public loadmovies_loader(Context context) {
        super(context);

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public LinkedList<String[]> loadInBackground() {
        String url="";
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getContext());
        LinkedList<String[]> details=new LinkedList<>();
        try {

            HttpClient client = new DefaultHttpClient();

            String title="https://api.themoviedb.org/3/";
            String search="discover/movie?";
            String sort=prefs.getString("sort","popularity");
            String sortby="&sort_by="+sort+".desc";

            String api="&api_key="+BuildConfig.MY_KEY;
            // Toast.makeText(con,prefs.getString("sort","popularity"),Toast.LENGTH_LONG).show();
            HttpPost httppost = new HttpPost(title+search+sortby+api);

            //publishProgress("connecting");
            HttpResponse httpResponse = client.execute(httppost);

            int status = httpResponse.getStatusLine().getStatusCode();
            if(status==200){
                HttpEntity entity = httpResponse.getEntity();
                String word= EntityUtils.toString(entity, "UTF_8");

                JSONObject reader=new JSONObject(word);
                JSONArray result= reader.getJSONArray("results");
                //movie_detail=new String[result.length()][7];
                //Toast.makeText(con,"result "+result.length()+"",Toast.LENGTH_SHORT).show();
                for(int i=0;i< result.length();i++){
                    JSONObject movies=result.getJSONObject(i);
                    String[] wo=new String[]{movies.getString("original_title"),
                            movies.getString("overview"),movies.getString("release_date")
                            ,movies.getString("poster_path"),
                            movies.getString("backdrop_path"),movies.getString("vote_average")
                            ,movies.getString("id")};
                    //  publishProgress("lenght= "+wo.length+"");

                    details.addLast(wo);
                    //  Toast.makeText(con, "hey "+wo.length+"",Toast.LENGTH_SHORT).show();
                }
              //  Toast.makeText(getContext(), "hey "+details.size()+"",Toast.LENGTH_SHORT).show();
                Log.i("size",details.size()+"");


            }
        }catch (Exception e){
           // Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            Log.i("error",e.getMessage());

        }
        return details;
    }

    @Override
    public void deliverResult(LinkedList<String[]> data) {
        //Toast.makeText(getContext(), "hey "+data.size()+"",Toast.LENGTH_SHORT).show();
        super.deliverResult(data);
    }
}
