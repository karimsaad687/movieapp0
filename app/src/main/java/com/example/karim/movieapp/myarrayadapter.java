package com.example.karim.movieapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

/**
 * Created by karim on 3/22/2016.
 */
public class myarrayadapter extends ArrayAdapter<grid_Images> {
    Context c;
    LinkedList<grid_Images> j;
    public myarrayadapter(Context context, LinkedList<grid_Images> images) {
        super(context, R.layout.gridview,images);
        c=context;
        j=images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.gridview, parent, false);
        }

       String h= j.get(position).getImage_link();
        ImageView iv=(ImageView) convertView.findViewById(R.id.imageView);
        String url="http://image.tmdb.org/t/p/w185";


                Picasso.with(c).load(url + h).resize(200,250).error(R.drawable.nopostefound).into(iv);
        //Bitmap bitmap= BitmapFactory.decodeResource(convertView.getResources(),R.drawable.frame);
        //iv.setImageBitmap(bitmap);
        return convertView;
    }


}
