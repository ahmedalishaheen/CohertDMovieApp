package com.example.aali2.movieappv2;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by aali2 on 8/19/2016.
 */
public class Global extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Picasso.Builder builder=new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso build=builder.build();
        Picasso.setSingletonInstance(build);
    }
}
