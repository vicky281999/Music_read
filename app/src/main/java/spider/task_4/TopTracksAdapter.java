package spider.task_4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class TopTracksAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Tracks> mTracks;
    ImageView tpic = null;
    static Bitmap myBitmap;

    public TopTracksAdapter(Context mcontext, List<Tracks> mTracks) {
        super();
        this.mcontext = mcontext;
        this.mTracks = mTracks;
    }

    @Override
    public int getCount() {
        return mTracks.size();
    }

    @Override
    public Object getItem(int i) {
        return mTracks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v==null) {
            LayoutInflater mInflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = mInflater.inflate(R.layout.list, null);
        }
        TextView tname = (TextView)v.findViewById(R.id.track_name);
        tpic = (ImageView)v.findViewById(R.id.track_image);

            tname.setText(mTracks.get(i).getTname());
            String imgSrc = mTracks.get(i).getTpic().replace("\"","");

            tpic.setImageBitmap(Common.bmarray[i]);
            //tpic.setImageBitmap(Top_Tracks_List.getBitmapFromURL(imgSrc));
        //tpic.setImageBitmap(Top_Tracks_List.getBitmapFromURL("https://www.geekinsider.com/wp-content/uploads/2015/03/apps-image.jpg"));
        //v.setTag(mTracks.get(i));
        return v;
    }



}
