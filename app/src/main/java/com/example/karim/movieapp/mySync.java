package com.example.karim.movieapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
 * Created by karim on 3/22/2016.
 */
public class mySync extends AsyncTask<FragmentManager,String,Void> {
    SharedPreferences prefs;
    FragmentManager fragmentManager;
   myarrayadapter arrayAdapter;
    GridView l;
    Context con;
    boolean cancel=false;
    String[][] movie_detail;
    LinkedList<String[]> details;
    ProgressBar spinner;
    //ProgressBar pb;
    boolean frag;
    public mySync(Context c,myarrayadapter aa,GridView gridView,LinkedList<String[]>d,ProgressBar sp,boolean frag2){
        spinner=sp;
        con=c;
        arrayAdapter=aa;
        frag=frag2;
        l=gridView;
        details=d;
        prefs= PreferenceManager.getDefaultSharedPreferences(con);
       // pb=p;
    }
    @Override
    protected Void doInBackground(FragmentManager... params) {
        String url="";
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(con);
        fragmentManager=params[0];
        try {
            publishProgress("start");
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



                publishProgress("done");
            }
        }catch (Exception e){
            publishProgress("error" + e.getMessage());


        }
        return null;
    }

    @Override
    protected void onPostExecute(Void strings) {
       // super.onPostExecute(strings);

        String w="";
        int y=0;
          //  Toast.makeText(con,details.size()+"",Toast.LENGTH_SHORT).show();
            LinkedList<grid_Images> linkedList=new LinkedList<>();
        String sort=prefs.getString("sort","popularity");
        String movie_name=prefs.getString(sort, "popularity");
        Log.i("stored",movie_name);
            for(int i=0;i<details.size();i++){
                if(movie_name.equals(details.get(i)[0]))
                    y=i;
                linkedList.addLast(new grid_Images(details.get(i)[3]));
            }
            arrayAdapter=new myarrayadapter(con,linkedList);

            l.setAdapter(arrayAdapter);

      // Log.i("position", prefs.getInt("position", 0) + "");

       // if(con.getApplicationContext().getResources().getConfiguration().orientation==2) {
        //Log.i("error", con.getResources().getResourceName(R.id.screen2));

        if(frag){

            Bundle bundle = new Bundle();
            bundle.putString("poster", details.get(y)[3]);
            bundle.putString("overview", details.get(y)[1]);
            bundle.putString("name", details.get(y)[0]);
            bundle.putString("release", details.get(y)[2]);
            bundle.putString("id", details.get(y)[6]);
            bundle.putString("votes", details.get(y)[5]);

            FragmentTransaction ft=fragmentManager.beginTransaction();
            Fragment f=fragmentManager.findFragmentByTag("right");
            if(f!=null){
                ft.remove(f);
            }
            fragmentright fragmentright= new fragmentright();
            fragmentright.setArguments(bundle);
            ft.add(R.id.screen2, fragmentright, "right");
            //Log.i("karim", con+"");
            if(cancel==false)
            ft.commit();

        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if(values[0].equals("start")){
            spinner.setVisibility(View.VISIBLE);
        }else if(values[0].equals("done"))
            spinner.setVisibility(View.GONE);
        else if(values[0].contains("error")) {
            Toast.makeText(con, values[0], Toast.LENGTH_SHORT).show();
        }
      /*  if(values[0].equals("start"))
        pb.setVisibility(View.VISIBLE);
        else
            pb.setVisibility(View.GONE);
            */
    }
}
