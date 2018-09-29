package spider.task_4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchTrackDetails extends AppCompatActivity {
    TextView Name,Ratings,Album,Artist,Releasedate,Musicgenrename,Musicgenrenameex,Musicgenrevanity;
    ImageView Pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_track_details);

        int i = getIntent().getExtras().getInt("selecteditem");

       Name =findViewById(R.id.name);
        Ratings = findViewById(R.id.ratings);
        Album = findViewById(R.id.album);
        Artist = findViewById(R.id.artist);
        Releasedate = findViewById(R.id.releasedate);
        Musicgenrename = findViewById(R.id.musicgenrename);
        Musicgenrenameex = findViewById(R.id.musicgenrenameextended);
        Musicgenrevanity = findViewById(R.id.musicgenrevanity);
        Pic = findViewById(R.id.pic);

        Name.setText(Search_Tracks.tname[i]);
        Ratings.setText(Search_Tracks.ratings[i]);
        Album.setText(Search_Tracks.talbum[i]);
        Artist.setText(Search_Tracks.tartist[i]);
        Releasedate.setText(Search_Tracks.treleasedate[i]);
        Musicgenrename.setText(Search_Tracks.tmusicgenrename[i]);
        Musicgenrenameex.setText(Search_Tracks.tmusicgenrenameex[i]);
        Musicgenrevanity.setText(Search_Tracks.tmusicgenrevanity[i]);
        Pic.setImageBitmap(Common.bmarray[i]);

    }
    public void onBackPressed(){
        finish();
    }
}
