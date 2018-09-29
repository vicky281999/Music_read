package spider.task_4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TrackDetails extends AppCompatActivity {
    TextView Name,Ratings,Album,Artist,Releasedate,Musicgenrename,Musicgenrenameex,Musicgenrevanity;
    ImageView Pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);
        int i = getIntent().getExtras().getInt("selectedposition");

        Name =findViewById(R.id.name);
        Ratings = findViewById(R.id.ratings);
        Album = findViewById(R.id.album);
        Artist = findViewById(R.id.artist);
        Releasedate = findViewById(R.id.releasedate);
        Musicgenrename = findViewById(R.id.musicgenrename);
        Musicgenrenameex = findViewById(R.id.musicgenrenameextended);
        Musicgenrevanity = findViewById(R.id.musicgenrevanity);
        Pic = findViewById(R.id.pic);

        Name.setText(Top_Tracks_List.tname[i]);
        Ratings.setText(Top_Tracks_List.ratings[i]);
        Album.setText(Top_Tracks_List.talbum[i]);
        Artist.setText(Top_Tracks_List.tartist[i]);
        Releasedate.setText(Top_Tracks_List.treleasedate[i]);
        Musicgenrename.setText(Top_Tracks_List.tmusicgenrename[i]);
        Musicgenrenameex.setText(Top_Tracks_List.tmusicgenrenameex[i]);
        Musicgenrevanity.setText(Top_Tracks_List.tmusicgenrevanity[i]);
        Pic.setImageBitmap(Common.bmarray[i]);




    }
    public void onBackPressed(){
        Intent intent = new Intent(TrackDetails.this,Top_Tracks_List.class);
        startActivity(intent);
        finish();
    }
}
