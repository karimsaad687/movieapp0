package com.example.karim.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by karim on 3/26/2016.
 */
public class trailer_list extends ArrayAdapter<String> {
    String[]link;
    public trailer_list(Context context, String[] resource) {
        super(context, R.layout.trailers_listview,resource);
        link=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View custom=inflater.inflate(R.layout.trailers_listview, parent, false);
        String word=getItem(position);
        TextView tv=(TextView) custom.findViewById(R.id.trailers_text);
       ImageView iv=(ImageView) custom.findViewById(R.id.trailer_icon);
        iv.setImageResource(R.drawable.trailers2);

        tv.setText(link[position]);
        return custom;
    }
}
