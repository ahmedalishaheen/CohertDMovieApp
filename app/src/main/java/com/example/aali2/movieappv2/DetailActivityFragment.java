package com.example.aali2.movieappv2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public static String youtube1;
    public static String youtube2;
    public static String overview;
    public static String rating;
    public static String release_date;
    public static String review;
    public static String title;
    public static String poster;
    public static boolean favorite;
    public static ArrayList<String> comments;
    public static Button b;
    private ShareActionProvider mShareActionProvider;



    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView=inflater.inflate(R.layout.fragment_detail, container, false);
        review=null;
        Bundle bundleD=getArguments();
        if(bundleD!=null){
            overview=bundleD.getString("overview");
            rating=bundleD.getString("rating");
            poster=bundleD.getString("poster");
            ImageView iv=(ImageView)rootView.findViewById(R.id.poster);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+poster).resize(MoviesFragment.width,(int)(MoviesFragment.width*1.5)).into(iv);
            title=bundleD.getString("title");
            youtube1=bundleD.getString("youtube1");
            youtube2=bundleD.getString("youtube2");
            b=(Button)rootView.findViewById(R.id.favorite);
            favorite =bundleD.getBoolean("favorited",false);
            if(!favorite){
                b.setText("FAVORITE");
                b.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_star_border_24dp, 0, 0, 0);
                b.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);


            }else {
                b.setText("UNFAVORITE");
                b.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_star_24dp, 0, 0, 0);
                b.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

            }
            comments=bundleD.getStringArrayList("comments");
            for (int i=0;i<comments.size();i++){
                LinearLayout linearLayout=(LinearLayout)rootView.findViewById(R.id.linear);
                View divider=new View(getActivity());
                TextView tv=new TextView(getActivity());
                RelativeLayout.LayoutParams p=new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(p);
                int paddingPixel=10;
                float density=getActivity().getResources().getDisplayMetrics().density;
                int paddingDP =(int)(paddingPixel*density);
                tv.setPadding(0,paddingDP,0,paddingDP);
                RelativeLayout.LayoutParams x=new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                x.height=1;
                divider.setLayoutParams(x);
                divider.setBackgroundColor(Color.BLACK);
                tv.setText(comments.get(i));
                linearLayout.addView(divider);
                linearLayout.addView(tv);

                if(review==null){
                    review=comments.get(i);

                }else{
                    review+="divider123"+comments.get(i);

                }

            }
            release_date=bundleD.getString("date");
            TextView tv=(TextView)rootView.findViewById(R.id.date);
            tv.setText(release_date);
            TextView tv2=(TextView)rootView.findViewById(R.id.overview);
            tv2.setText(overview);
            TextView tv3=(TextView)rootView.findViewById(R.id.rating);
            tv3.setText(rating);
            TextView tv4=(TextView)rootView.findViewById(R.id.title);
            tv4.setText(title);

        }





        return rootView;
    }
    public  void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_detail,menu);
        MenuItem item=menu.findItem(R.id.action_share);
        mShareActionProvider=(ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if(mShareActionProvider!=null){
            mShareActionProvider.setShareIntent(createShareIntent());
        }

    }
    public Intent createShareIntent(){
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Check out this trailer for  "+title+": "+"https://www.youtube.com/watch?v="+youtube1);
        return shareIntent;
    }
}
