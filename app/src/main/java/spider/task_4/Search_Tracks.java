package spider.task_4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Search_Tracks extends AppCompatActivity {

    public static String[] ratings = new String[500];
    public static String[] tname = new String[500];
    public static String[] tpic = new String[500];
    public static String[] treleasedate = new String[500];
    public static String[] tmusicgenrename = new String[500];
    public static String[] tmusicgenrenameex = new String[500];
    public static String[] tmusicgenrevanity = new String[500];
    public static String[] talbum = new String[500];
    public static String[] tartist = new String[500];
    public static String musicgenrename, musicgenrenameex,musicgenrevanity;

    private List<Tracks> mTracks;
    private TopTracksAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__tracks);

        String url = "http://api.musixmatch.com/ws/1.1/track.search?q_track="+ Top_Tracks_List.search+"&page_size=10&page=1&s_track_rating=desc&apikey=fa7e43f6fb9e2f10cd8f1322dbbe8dbc";

        Call<JsonObject> call = ApiService.getInstance().getSearchTrack(url);
//asynchronous call
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JsonObject Music = response.body();
                    JsonObject message = null;
                    message = Music.getAsJsonObject("message");
                    JsonObject body = message.getAsJsonObject("body");
                    JsonArray tracklist = body.getAsJsonArray("track_list");
                    Log.d("track log:",""+tracklist.toString());
                    mTracks = new ArrayList<>();
                    if(tracklist.size()==0){
                        Toast.makeText(getApplicationContext(),"No tracks found!!",Toast.LENGTH_LONG).show();
                        Intent in = new Intent(Search_Tracks.this,Top_Tracks_List.class);
                        startActivity(in);
                        finish();
                    }
                    for (int i = 0; i < tracklist.size(); i++) {
                        JsonObject trackvalue = tracklist.get(i).getAsJsonObject();
                        JsonObject track = (JsonObject) trackvalue.get("track");
                        String name = String.valueOf(track.get("track_name"));
                        String rating = String.valueOf(track.get("track_rating"));
                        String pic = String.valueOf(track.get("album_coverart_100x100"));
                        String releasedate = String.valueOf(track.get("first_release_date"));
                        String album = String.valueOf(track.get("album_name"));
                        String artist = String.valueOf(track.get("artist_name"));
                        JsonObject primarygenres = (JsonObject) track.get("primary_genres");
                        JsonArray musicgenrelist = primarygenres.getAsJsonArray("music_genre_list");
                        for (int j=0;j < musicgenrelist.size(); j++) {
                            JsonObject genre = musicgenrelist.get(j).getAsJsonObject();
                            JsonObject musicgenre = (JsonObject) genre.get("music_genre");
                            musicgenrename = String.valueOf(musicgenre.get("music_genre_name"));
                            musicgenrenameex = String.valueOf(musicgenre.get("music_genre_name_extended"));
                            musicgenrevanity = String.valueOf(musicgenre.get("music_genre_vanity"));
                        }
                        //getBitmapFromURL(pic,i);
                        tmusicgenrename[i] = musicgenrename;
                        tmusicgenrenameex[i] = musicgenrenameex;
                        tmusicgenrevanity[i] = musicgenrevanity;
                        talbum[i] = album;
                        tartist[i] = artist;
                        treleasedate[i] = releasedate;
                        ratings[i] = rating;
                        tname[i] = name;
                        tpic[i] = pic;
                        mTracks.add(new Tracks(tname[i],tpic[i],ratings[i]));
                    }
                    DownloadImage downloadImage = new DownloadImage();
                    downloadImage.execute();
                    ListView searchtracklist=(ListView)findViewById(R.id.searchtracklist);
                    Adapter = new TopTracksAdapter(getApplicationContext(),mTracks);
                    searchtracklist.setAdapter(Adapter);

                    searchtracklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(Search_Tracks.this,SearchTrackDetails.class);
                            intent.putExtra("selecteditem",i);
                            startActivity(intent);
                        }
                    });


                }catch(Exception ex){
                    Log.d("track log error:",ex.getMessage());
                }


            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("onFailure:",t.getMessage());
            }
        });

    }
    public static class DownloadImage extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                //URL url = new URL(src);
                URL url = null;
                for(int i=0;i<tpic.length;i++) {
                    url = new URL(tpic[i]);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    Log.e("Bitmap", "returned");
                    /*if(myBitmap!=null){
                        downloadTracks();
                    }*/
                    Common.bmarray[i] = myBitmap;
                }
                //return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Exception", e.getMessage());
                return null;
            }
            return null;
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(Search_Tracks.this,Top_Tracks_List.class);
        startActivity(intent);
        finish();
    }

}
