package spider.task_4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchArtistDetails extends AppCompatActivity {

    TextView Name,Ratings,Album,Artist,Releasedate,Musicgenrename,Musicgenrenameex,Musicgenrevanity;
    ImageView Pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artist_details);
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

        Name.setText(Search_Artists.tname[i]);
        Ratings.setText(Search_Artists.ratings[i]);
        Album.setText(Search_Artists.talbum[i]);
        Artist.setText(Search_Artists.tartist[i]);
        Releasedate.setText(Search_Artists.treleasedate[i]);
        Musicgenrename.setText(Search_Artists.tmusicgenrename[i]);
        Musicgenrenameex.setText(Search_Artists.tmusicgenrenameex[i]);
        Musicgenrevanity.setText(Search_Artists.tmusicgenrevanity[i]);
        Pic.setImageBitmap(Common.bmarray[i]);

    }
    public void onBackPressed(){
        finish();
    }
}
