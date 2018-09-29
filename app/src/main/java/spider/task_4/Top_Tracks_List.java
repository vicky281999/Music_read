package spider.task_4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top_Tracks_List extends AppCompatActivity {
    public static Bitmap bm = null , myBitmap = null;
    public static String[] ratings = new String[500];
    public static String[] tname = new String[500];
    static String[] tpic ;
    public static String[] treleasedate = new String[500];
    public static String[] tmusicgenrename = new String[500];
    public static String[] tmusicgenrenameex = new String[500];
    public static String[] tmusicgenrevanity = new String[500];
    public static String[] talbum = new String[500];
    public static String[] tartist = new String[500];
    EditText searchtrack;
    public static String search,musicgenrename, musicgenrenameex,musicgenrevanity;
    private ListView toptracklist;
    private TopTracksAdapter Adapter;
    private List<Tracks> mTracks = new ArrayList<Tracks>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top__tracks__list);

        toptracklist=(ListView)findViewById(R.id.toptracklist);

        ImageView switch1=(ImageView)findViewById(R.id.switch1);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Top_Tracks_List.this,Top_Artist_List.class);
                startActivity(intent);
                finish();
            }
        });
        ImageView tracksearch=findViewById(R.id.search1);
        tracksearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText searchtrack = (EditText) findViewById(R.id.searchtrack);
                search = searchtrack.getText().toString();
                if(search!=""){
                Intent intent=new Intent(Top_Tracks_List.this,Search_Tracks.class);
                startActivity(intent);
                finish();}
                else
                    Toast.makeText(getApplicationContext(),"Enter the Track Name!!",Toast.LENGTH_LONG).show();
            }
        });
        // getBitmapFromURL("https://www.sankalpcs.com/company/images/mobile_apps.png");
        downloadTracks();   

    }

    void downloadTracks(){
        Call<JsonObject> call = ApiService.getInstance().getMusic();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JsonObject Music = response.body();
                    JsonObject message = null;
                    message = Music.getAsJsonObject("message");
                    JsonObject body = message.getAsJsonObject("body");
                    JsonArray tracklist = body.getAsJsonArray("track_list");
                    tpic = new String[tracklist.size()];

                    Log.d("track log:",""+tracklist.toString());
                    for (int i = 0; i < tracklist.size(); i++) {
                        JsonObject trackvalue = tracklist.get(i).getAsJsonObject();
                        JsonObject track = (JsonObject) trackvalue.get("track");
                        String name = String.valueOf(track.get("track_name"));
                        String rating = String.valueOf(track.get("track_rating"));
                        String pic = String.valueOf(track.get("album_coverart_100x100"));
                        pic = pic.replace("\"","");
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
                    setAdapter();

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
     void setAdapter(){
        toptracklist = findViewById(R.id.toptracklist);
        Adapter = new TopTracksAdapter(getApplicationContext(),mTracks);
        toptracklist.setAdapter(Adapter);

        toptracklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(Top_Tracks_List.this,TrackDetails.class);
                intent.putExtra("selectedposition",i);
                startActivity(intent);
                finish();
            }
        });

    }

     Bitmap getBitmapFromURL(final String src,final int i) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //URL url = new URL(src);
                    URL url = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                    Log.e("Bitmap", "returned");
                    /*if(myBitmap!=null){
                        downloadTracks();
                    }*/
                    Common.bmarray[i] = myBitmap ;
                    //return myBitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Exception", e.getMessage());
                    return;
                }
            }
        }).start();
        return myBitmap;
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
                    myBitmap = BitmapFactory.decodeStream(input);
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
        finish();
    }
}
