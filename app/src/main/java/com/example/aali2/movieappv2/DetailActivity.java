package com.example.aali2.movieappv2;

import android.content.ContentValues;
import android.content.Intent;
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

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState==null){
            Intent i=getIntent();
            Bundle b=new Bundle();
            b.putString("overview", i.getStringExtra("overview"));
            b.putString("rating", i.getStringExtra("rating"));
            b.putString("poster", i.getStringExtra("poster"));
            b.putString("id", i.getStringExtra("id"));
            b.putString("title", i.getStringExtra("title"));
            b.putString("youtube1", i.getStringExtra("youtube1"));
            b.putString("youtube2", i.getStringExtra("youtube2"));
            b.putBoolean("favorited", i.getBooleanExtra("favorited",false));
            b.putStringArrayList("comments", i.getStringArrayListExtra("comments"));
            b.putString("date", i.getStringExtra("date"));


            DetailActivityFragment fragment=new DetailActivityFragment();
            fragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragment).commit();



            // getSupportFragmentManager().beginTransaction().replace(R.id.container2,new DetailActivityFragment()).commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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



}
