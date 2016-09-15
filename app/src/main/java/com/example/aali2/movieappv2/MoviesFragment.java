package com.example.aali2.movieappv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment {

    static GridView gridView;
    static int width;
    static ArrayList<String> posters;
    static boolean sortByPop=true;
    static String API_KEY="0a759684807097cea118c3a34a09e040";

    static ArrayList<String> movie_overview;
    static ArrayList<String> movie_rating;
    static ArrayList<String> movie_poster;
    static ArrayList<String> movie_title;
    static ArrayList<String> movie_id;
    static ArrayList<String> movie_release;
    static ArrayList<String> youtube_link1;
    static ArrayList<String> youtube_link2;
    static ArrayList<ArrayList<String>> comments;
    static ArrayList<Boolean> favorited;
    static PreferenceChangeListener listener;
    static SharedPreferences prefs;
    static boolean sortByFavorites;
    static ArrayList<String> titleF;
    static ArrayList<String> ratingF;
    static ArrayList<String> releaseF;
    static ArrayList<String> overviewF;
    static ArrayList<ArrayList<String>> commentsF;
    static ArrayList<String> postersF;
    static ArrayList<String> youtube1F;
    static ArrayList<String> youtube2F;
    private boolean mTwoPane;

    public MoviesFragment() {
        setHasOptionsMenu(true);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.container2) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;

        } else {
            mTwoPane = false;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_main, container, false);
        WindowManager wm=(WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display=wm.getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);
        if(MainActivity.TABLET){
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                width=size.x/6;

            }else{
                width=size.x/4;
            }

        }else{
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                width=size.x/5;

            }else{
                width=size.x/3;
            }

        }
        if(getActivity()!=null){
            ArrayList<String>array=new ArrayList<String>();
            ImageAdapter adapter=new ImageAdapter(getActivity(),array,width);
            gridView=(GridView)rootView.findViewById(R.id.gridView);
            gridView.setColumnWidth(width);
            gridView.setAdapter(adapter);

        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mTwoPane==false){
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    if (!sortByFavorites) {
                        favorited = bindFavoritesToMovies();
                        Toast.makeText(getActivity(), "" + position,
                                Toast.LENGTH_SHORT).show();
                        intent.putExtra("overview", movie_overview.get(position));
                        intent.putExtra("rating", movie_rating.get(position));
                        intent.putExtra("poster", posters.get(position));
                        intent.putExtra("id", movie_id.get(position));
                        intent.putExtra("title", movie_title.get(position));
                        intent.putExtra("youtube1", youtube_link1.get(position));
                        intent.putExtra("youtube2", youtube_link2.get(position));
                        intent.putExtra("favorited", favorited.get(position));
                        intent.putExtra("comments", comments.get(position));
                        intent.putExtra("date", movie_release.get(position));





                    }else{
                        Toast.makeText(getActivity(), "" + position,
                                Toast.LENGTH_SHORT).show();
                        intent.putExtra("overview", overviewF.get(position));
                        intent.putExtra("rating", ratingF.get(position));
                        intent.putExtra("poster", postersF.get(position));
                        intent.putExtra("title", titleF.get(position));
                        intent.putExtra("youtube1", youtube1F.get(position));
                        intent.putExtra("youtube2", youtube2F.get(position));
                        intent.putExtra("favorited", favorited.get(position));
                        intent.putExtra("comments", commentsF.get(position));
                        intent.putExtra("date", releaseF.get(position));




                    }
                    startActivity(intent);



                }else{
                    Bundle intent = new Bundle();

                    if (!sortByFavorites) {
                        favorited = bindFavoritesToMovies();
                        Toast.makeText(getActivity(), "" + position,
                                Toast.LENGTH_SHORT).show();
                        intent.putString("overview", movie_overview.get(position));
                        intent.putString("rating", movie_rating.get(position));
                        intent.putString("poster", posters.get(position));
                        intent.putString("id", movie_id.get(position));
                        intent.putString("title", movie_title.get(position));
                        intent.putString("youtube1", youtube_link1.get(position));
                        intent.putString("youtube2", youtube_link2.get(position));
                        intent.putBoolean("favorited", favorited.get(position));
                        intent.putStringArrayList("comments", comments.get(position));
                        intent.putString("date", movie_release.get(position));

                    }else{
                        Toast.makeText(getActivity(), "" + position,
                                Toast.LENGTH_SHORT).show();

                        intent.putString("overview", overviewF.get(position));
                        intent.putString("rating", ratingF.get(position));
                        intent.putString("poster", postersF.get(position));
                        intent.putString("title", titleF.get(position));
                        intent.putString("youtube1", youtube1F.get(position));
                        intent.putString("youtube2", youtube2F.get(position));
                        intent.putBoolean("favorited", favorited.get(position));
                        intent.putStringArrayList("comments", commentsF.get(position));
                        intent.putString("date", releaseF.get(position));





                    }
                    DetailActivityFragment fragment=new DetailActivityFragment();
                    fragment.setArguments(intent);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragment).commit();



                }

            }
        });




        return rootView;
    }
    private class PreferenceChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener{

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

            gridView.setAdapter(null);
            onStart();

        }
    }
    public ArrayList<Boolean>bindFavoritesToMovies(){
        ArrayList<Boolean>results=new ArrayList<>();
        for (int i=0;i<movie_title.size();i++){
            results.add(false);
        }
        for (String favoritedTitles:titleF){
            for (int i=0;i<movie_title.size();i++){
                if (favoritedTitles.equals(movie_title.get(i))){
                    results.set(i,true);
                }
            }
        }
        return results;

    }

    @Override
    public void onStart() {
        super.onStart();
        prefs= PreferenceManager.getDefaultSharedPreferences(getActivity());
        listener=new PreferenceChangeListener();
        prefs.registerOnSharedPreferenceChangeListener(listener);
        if(prefs.getString("sortby","popularity").equals("popularity")){

            getActivity().setTitle("Most popular Movies");
            sortByPop=true;
            sortByFavorites=false;
        }else if(prefs.getString("sortby","popularity").equals("rating")){
            getActivity().setTitle("Top Rated Movies");
            sortByPop=false;
            sortByFavorites=false;
        }else if(prefs.getString("sortby","popularity").equals("favorites")){
            getActivity().setTitle("Favorite  Movies");
            sortByPop=false;
            sortByFavorites=true;
        }

        TextView textView=new TextView(getActivity());
        textView.setTextSize(22);
        textView.setPadding(20,20,20,20);
        RelativeLayout layout=(RelativeLayout) getActivity().findViewById(R.id.linearlayout);
        loadFavoriteData();
        if(sortByFavorites){
            if(postersF.size()==0){
                textView.setText("you have no Favorites movies");
                if(layout.getChildCount()==1){
                    layout.addView(textView);
                }
                gridView.setVisibility(GridView.GONE);
            }else {
                gridView.setVisibility(GridView.VISIBLE);
                layout.removeView(textView);
            }
            if(postersF!=null&&getActivity()!=null){
                ImageAdapter adapter=new ImageAdapter(getActivity(),postersF,width);
                gridView.setAdapter(adapter);
            }

        }else {
            gridView.setVisibility(GridView.VISIBLE);
            layout.removeView(textView);

            if (isNetworkAvailable()) {

                new ImageLoadTask().execute();

            } else {
                TextView textView1 = new TextView(getActivity());
                textView1.setTextSize(22);
                textView1.setPadding(20,20,20,20);
                RelativeLayout layout1=(RelativeLayout) getActivity().findViewById(R.id.linearlayout);
                textView1.setText("Check the connection ,please");
                if (layout1.getChildCount() == 1) {
                    layout1.addView(textView1);
                }
                gridView.setVisibility(GridView.GONE);

            }
        }

    }
    public void loadFavoriteData(){

        String URL="content://com.example.aali2.movieappv2/movies";
        Uri movies=Uri.parse(URL);
        Cursor c=getActivity().getContentResolver().query(movies,null,null,null,"title");
        postersF=new ArrayList<String>();
        titleF=new ArrayList<String>();
        ratingF=new ArrayList<String>();
        favorited=new ArrayList<Boolean>();
        releaseF=new ArrayList<String>();
        youtube1F=new ArrayList<String>();
        youtube2F=new ArrayList<String>();
        overviewF=new ArrayList<String>();
        commentsF=new ArrayList<ArrayList<String>>();

        if(c==null)return;
        while (c.moveToNext()){
            postersF.add(c.getString(c.getColumnIndex(MovieProvider.NAME)));
            ratingF.add(c.getString(c.getColumnIndex(MovieProvider.RATING)));
            releaseF.add(c.getString(c.getColumnIndex(MovieProvider.DATE)));
            titleF.add(c.getString(c.getColumnIndex(MovieProvider.TITLE)));
            overviewF.add(c.getString(c.getColumnIndex(MovieProvider.OVERVIEW)));
            youtube1F.add(c.getString(c.getColumnIndex(MovieProvider.YOUTUBE1)));
            youtube2F.add(c.getString(c.getColumnIndex(MovieProvider.YOUTUBE2)));
            commentsF.add(convertStringToArrayList(c.getString(c.getColumnIndex(MovieProvider.REVIEW))));
            favorited.add(true);


        }

    }
    public ArrayList<String>convertStringToArrayList(String s){
        ArrayList<String>results=new ArrayList<>(Arrays.asList(s.split("divider123")));
        return results;

    }
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
    }
    public class ImageLoadTask extends AsyncTask<Void,Void,ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            while (true){
                try {
                    posters=new ArrayList<>(Arrays.asList(getPathsFromAPI(sortByPop)));
                    return posters;
                }catch (Exception e){
                    continue;
                }
            }


        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if(result!=null&& getActivity()!=null) {
                ImageAdapter adapter = new ImageAdapter(getActivity(), result, width);
                gridView.setAdapter(adapter);
            }

        }

        public String[]getPathsFromAPI(boolean sortbypop){

            while (true){

                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                // Will contain the raw JSON response as a string.
                String movieJsonStr = null;

                try {
                    String urlString=null;
                    if(sortbypop){
                        urlString="http://api.themoviedb.org/3/movie/popular?api_key="+API_KEY;
                    }else{
                        urlString="http://api.themoviedb.org/3/movie/top_rated?api_key="+API_KEY;

                    }
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();


                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    movieJsonStr = buffer.toString();
                    try {




                        movie_overview=new ArrayList<String>(Arrays.asList(getStringsFromJson(movieJsonStr,"overview")));
                        movie_title=new ArrayList<String>(Arrays.asList(getStringsFromJson(movieJsonStr,"original_title")));
                        movie_id=new ArrayList<String>(Arrays.asList(getStringsFromJson(movieJsonStr,"id")));
                        movie_rating=new ArrayList<String>(Arrays.asList(getStringsFromJson(movieJsonStr,"vote_average")));
                        movie_poster=new ArrayList<String>(Arrays.asList(getStringsFromJson(movieJsonStr,"poster_path")));
                        movie_release=new ArrayList<String>(Arrays.asList(getStringsFromJson(movieJsonStr,"release_date")));

                        while (true) {
                            youtube_link1 = new ArrayList<String>(Arrays.asList(getYotubeFromIds(movie_id, 0)));
                            youtube_link2 = new ArrayList<String>(Arrays.asList(getYotubeFromIds(movie_id, 1)));
                            int nullCount=0;
                            for (int i=0;i<youtube_link1.size();i++){
                                if(youtube_link1.get(i)==null){
                                    nullCount++;
                                    youtube_link1.set(i,"no video found");
                                }
                            }
                            for (int i=0;i<youtube_link2.size();i++){
                                if(youtube_link2.get(i)==null){
                                    nullCount++;
                                    youtube_link2.set(i,"no video found");
                                }
                            }
                            if(nullCount>2)continue;
                            break;


                        }
                        comments=getReviewsFromIds(movie_id);


                        return getPathsFromJson(movieJsonStr);
                    }catch (JSONException e){
                        return null;
                    }

                }catch (Exception e){
                    continue;
                } finally{
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {

                        }
                    }
                }

            }

        }
        public String[]getYotubeFromIds(ArrayList<String>ids,int posiition){
            String[]results=new String[ids.size()];

            for(int i=0;i<ids.size();i++){
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String movieJsonStr = null;



                try {
                    String urlString = null;

                    urlString = "http://api.themoviedb.org/3/movie/" + ids.get(i) + "/videos?api_key="+API_KEY;

                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();


                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    movieJsonStr = buffer.toString();
                    try {
                        results[i]=getYoutubeFromJSON(movieJsonStr,posiition);

                    }catch (JSONException E){
                        results[i]="no video found";

                    }

                }catch (Exception e){
                    continue;
                } finally{
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {

                        }
                    }
                }
            }
            return results;
        }
        public ArrayList<ArrayList<String>>getReviewsFromIds(ArrayList<String>ids){

            outerloop:
            while (true) {

                ArrayList<ArrayList<String>> results = new ArrayList<>();
                for (int i = 0; i < ids.size(); i++) {
                    HttpURLConnection urlConnection = null;
                    BufferedReader reader = null;
                    String movieJsonStr = null;


                    try {
                        String urlString = null;
                        urlString="http://api.themoviedb.org/3/movie/" + ids.get(i) + "/reviews?api_key="+API_KEY;
                        URL url = new URL(urlString);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.connect();


                        // Read the input stream into a String
                        InputStream inputStream = urlConnection.getInputStream();
                        StringBuffer buffer = new StringBuffer();
                        if (inputStream == null) {
                            // Nothing to do.
                            return null;
                        }
                        reader = new BufferedReader(new InputStreamReader(inputStream));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                            // But it does make debugging a *lot* easier if you print out the completed
                            // buffer for debugging.
                            buffer.append(line + "\n");
                        }

                        if (buffer.length() == 0) {
                            // Stream was empty.  No point in parsing.
                            return null;
                        }
                        movieJsonStr = buffer.toString();
                        try {
                            results.add(getCommentsFromJson(movieJsonStr));

                        }catch (JSONException E){
                            return  null;

                        }

                    }catch (Exception e){
                        continue outerloop;
                    } finally{
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (final IOException e) {

                            }
                        }
                    }
                }
                return results;


            }
        }

        public ArrayList<String>getCommentsFromJson(String jsonStringParam)throws JSONException{
            JSONObject JSONString = new JSONObject(jsonStringParam);
            JSONArray reviewsArray = JSONString.getJSONArray("results");
            ArrayList<String>results=new ArrayList<>();
            if (reviewsArray.length()==0){
                results.add("No Reviews For this Movie");
                return results;
            }
            for (int i=0;i<reviewsArray.length();i++){
                JSONObject result=reviewsArray.getJSONObject(i);
                results.add(result.getString("content"));
            }
            return results;

        }

        public String getYoutubeFromJSON(String jsonStringParam , int position)throws JSONException{
            JSONObject JSONString = new JSONObject(jsonStringParam);
            JSONArray youtubesArray = JSONString.getJSONArray("results");
            JSONObject youtube;
            String result="no videos found";
            if (position==0){
                youtube=youtubesArray.getJSONObject(0);
                result=youtube.getString("key");
            }else if (position==1){
                if (youtubesArray.length()>1){
                    youtube=youtubesArray.getJSONObject(1);

                }else {
                    youtube=youtubesArray.getJSONObject(0);
                }
                result=youtube.getString("key");
            }
            return result;
        }

        public String[]getStringsFromJson(String jsonStringParam , String param)throws JSONException{
            JSONObject JSONString = new JSONObject(jsonStringParam);
            JSONArray moviesArray = JSONString.getJSONArray("results");
            String[]result=new String[moviesArray.length()];

            for(int i = 0; i < moviesArray.length(); i++) {



                JSONObject movie= moviesArray.getJSONObject(i);
                if(param.equals("vote_average")){
                    Double number=movie.getDouble(param);
                    result[i]=Double.toString(number)+"/10";
                }else if(param.equals("id")){
                    Integer ids=movie.getInt(param);
                    result[i]=Integer.toString(ids);

                }else{
                    result[i]=movie.getString(param);
                }




            }


            return result;


        }
        public String[] getPathsFromJson(String JSONStringParam)
                throws JSONException {

            JSONObject JSONString = new JSONObject(JSONStringParam);
            JSONArray moviesArray = JSONString.getJSONArray("results");
            String[]result=new String[moviesArray.length()];

            for(int i = 0; i < moviesArray.length(); i++) {

                JSONObject movie= moviesArray.getJSONObject(i);
                String moviePath=movie.getString("poster_path");
                result[i]=moviePath;


            }


            return result;

        }
    }
}
