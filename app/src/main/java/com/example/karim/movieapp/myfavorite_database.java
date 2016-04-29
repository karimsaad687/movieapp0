package com.example.karim.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by karim on 4/2/2016.
 */
public class myfavorite_database extends SQLiteOpenHelper {
    private static String database_name="favorite.db";
    private static String table_name="myfavorite";
    private static String id="id";
    private static String name="name";
    private static String movie_id="movie_id";
    private static String poster="poster_url";
    SQLiteDatabase db;
    String []cols=new String[]{name,movie_id,poster};
    Context c;
    public myfavorite_database(Context context) {
        super(context,database_name, null, 2);
        db=this.getWritableDatabase();
        c=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // db=this.getWritableDatabase();
        String create_table="CREATE TABLE "+table_name+" ("+id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+name+" TEXT,"+movie_id+" TEXT,"+poster+" TEXT);";
        db.execSQL(create_table);
        Log.i("database","created");
    }
    public int insert_data(String word){
        //SQLiteDatabase db=this.getWritableDatabase();
        String[] w=word.split(",");

        ContentValues content=new ContentValues();
        for(int i=0;i<3;i++){
            content.put(cols[i], w[i]);
        }

        int result=(int) db.insert(table_name, null,content);

        return result;
    }
    public String get_all(int i) {
        String query = "select * from " + table_name +";";

        SQLiteDatabase db1 = this.getReadableDatabase();

        Cursor c = db1.rawQuery(query, null);
        String n="";
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {

                    n+=c.getString(i);

                n+=",";
                c.moveToNext();
            }
        }

        return n;

    }
    public int is_exist(int col,String name) {
        String query = "select * from " + table_name +";";

        SQLiteDatabase db1 = this.getReadableDatabase();

        Cursor c = db1.rawQuery(query, null);
        String n="data: \n";
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {

                n=c.getString(col)+" ";
               // Log.i("col",n+" "+name);
                if(n.contains(name))
                    return 1;
                c.moveToNext();
            }
        }
        return 0;

    }
    public int delete_data(String col,String d){
        SQLiteDatabase db=this.getWritableDatabase();
        int h=0;
        String whereClause = col + "=?";
        String[] whereArgs = new String[] { d };

        h=db.delete(table_name, whereClause, whereArgs);
        return h;

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
