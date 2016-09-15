package com.example.aali2.movieappv2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static boolean TABLET =false;
    public boolean isTablet(Context context){
        boolean xlarge=((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)==4);
        boolean large=((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)==Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge||large);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TABLET=isTablet(this);

    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);


    if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MoviesFragment()).commit();
    }


    }
    public void favorite(View view){
        Button b =(Button)findViewById(R.id.favorite);
        if(b.getText().equals("FAVORITE")){
            b.setText("UNFAVORITE");
            b.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_star_24dp, 0, 0, 0);
            b.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
            ContentValues values=new ContentValues();
            values.put(MovieProvider.NAME,DetailActivityFragment.poster);
            values.put(MovieProvider.OVERVIEW,DetailActivityFragment.overview);
            values.put(MovieProvider.DATE,DetailActivityFragment.release_date);
            values.put(MovieProvider.RATING,DetailActivityFragment.rating);
            values.put(MovieProvider.YOUTUBE1,DetailActivityFragment.youtube1);
            values.put(MovieProvider.YOUTUBE2,DetailActivityFragment.youtube2);
            values.put(MovieProvider.TITLE,DetailActivityFragment.title);
            values.put(MovieProvider.REVIEW,DetailActivityFragment.review);
            getContentResolver().insert(MovieProvider.CONTENT_URI,values);


        }else {
            b.setText("FAVORITE");
            b.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_star_border_24dp, 0, 0, 0);
            b.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
            getContentResolver().delete(Uri.parse("content://com.example.aali2.movieappv2/movies"),"title=?",
                    new String[]{DetailActivityFragment.title});


        }

    }
    public void trailer1(View view){
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"+"watch?v="+DetailActivityFragment.youtube1));
        startActivity(intent);

    }

    public void trailer2(View view){
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"+"watch?v="+DetailActivityFragment.youtube2));
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
