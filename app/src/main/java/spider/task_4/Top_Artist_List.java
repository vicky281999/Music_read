package spider.task_4;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top_Artist_List extends AppCompatActivity {
    ArrayList<String> list;
    ListView topartistlist;
    EditText searchartist;
    String[] tname = new String[500];
    String[] tmusicgenrename = new String[500];
    String[] tratings = new String[500];
    String[] tmusicgenrenameextended = new String[500];
    String[] tmusicgenrevanity = new String[500];
    public static String Artist, musicgenrename,musicgenrenameextended,musicgenrevanity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top__artist__list);
        ImageView switch2=(ImageView)findViewById(R.id.switch2);
        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Top_Artist_List.this,Top_Tracks_List.class);
                startActivity(intent);
                finish();
            }
        });
        ImageView artistsearch=findViewById(R.id.search2);
        artistsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchartist=(EditText)findViewById(R.id.searchartist);
                Artist = searchartist.getText().toString();
                if(Artist!=""){
                Intent intent=new Intent(Top_Artist_List.this,Search_Artists.class);
                startActivity(intent);
                finish();}
                else
                    Toast.makeText(getApplicationContext(),"Enter the Artist Name!!",Toast.LENGTH_LONG).show();
            }
        });


        Call<JsonObject> call = ApiService.getInstance().getArtist();
//asynchronous call
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JsonObject Music = response.body();
                    JsonObject message = null;
                    message = Music.getAsJsonObject("message");
                    JsonObject body = message.getAsJsonObject("body");
                    JsonArray artistlist = body.getAsJsonArray("artist_list");
                    list = new ArrayList<>();
                    for (int i = 0; i < artistlist.size(); i++) {
                        JsonObject trackvalue = artistlist.get(i).getAsJsonObject();
                        JsonObject track = (JsonObject) trackvalue.get("artist");
                        String name = String.valueOf(track.get("artist_name"));
                        list.add(name);
                        String ratings = String.valueOf(track.get("artist_rating"));
                        JsonObject primarygenres = (JsonObject) track.get("primary_genres");
                        JsonArray musicgenrelist = primarygenres.getAsJsonArray("music_genre_list");
                        for (int j = 0; j < musicgenrelist.size(); j++) {
                            JsonObject genrevalue = musicgenrelist.get(j).getAsJsonObject();
                            JsonObject musicgenre = (JsonObject) genrevalue.get("music_genre");
                            musicgenrename = String.valueOf(musicgenre.get("music_genre_name"));
                            musicgenrenameextended = String.valueOf(musicgenre.get("music_genre_name_extended"));
                            musicgenrevanity = String.valueOf(musicgenre.get("music_genre_vanity"));
                        }
                        tname[i] = name;
                        tmusicgenrename[i] = musicgenrename;
                        tmusicgenrenameextended[i] = musicgenrenameextended;
                        tmusicgenrevanity[i] = musicgenrevanity;
                        tratings[i] = ratings;
                    }
                    topartistlist = (ListView) findViewById(R.id.topartistlist);
                    ArrayAdapter<String> madapter = new ArrayAdapter<String>(Top_Artist_List.this, android.R.layout.simple_list_item_1, list);
                    topartistlist.setAdapter(madapter);

                    topartistlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(Top_Artist_List.this, AuthurDetails.class);
                            intent.putExtra("name", tname[i]);
                            intent.putExtra("ratings", tratings[i]);
                            intent.putExtra("musicgenrename", tmusicgenrename[i]);
                            intent.putExtra("musicgenrenameextended", tmusicgenrenameextended[i]);
                            intent.putExtra("musicgenrevanity", tmusicgenrevanity[i]);
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
    public void onBackPressed(){
        Intent intent = new Intent(Top_Artist_List.this,Top_Tracks_List.class);
        startActivity(intent);
        finish();
    }
}
