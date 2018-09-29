package spider.task_4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.jar.Attributes;

public class AuthurDetails extends AppCompatActivity {
    TextView Name,Ratings,Musicgenrename,Musicgenrenameextended,Musicgenrevanity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authur_details);

        String name = getIntent().getExtras().getString("name");
        String ratings = getIntent().getExtras().getString("ratings");
        String musicgenrename = getIntent().getExtras().getString("musicgenrename");
        String musicgenrenameextended = getIntent().getExtras().getString("musicgenrenameextended");
        String musicgenrevanity = getIntent().getExtras().getString("musicgenrevanity");

        Name = findViewById(R.id.name);
        Ratings = findViewById(R.id.ratings);
        Musicgenrename = findViewById(R.id.musicgenrename);
        Musicgenrenameextended = findViewById(R.id.musicgenrenameextended);
        Musicgenrevanity = findViewById(R.id.musicgenrevanity);

        Name.setText(name);
        Ratings.setText(ratings);
        Musicgenrename.setText(musicgenrename);
        Musicgenrenameextended.setText(musicgenrenameextended);
        Musicgenrevanity.setText(musicgenrevanity);

    }
    public void onBackPressed(){
        finish();
    }
}
