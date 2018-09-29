package spider.task_4;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface Api {

    String BASE_URL="http://api.musixmatch.com/ws/1.1/";

    @GET("track.search?chart.tracks.get?page=1&page_size=10&country=it&apikey=fa7e43f6fb9e2f10cd8f1322dbbe8dbc")
    Call<JsonObject> getMusic();

    @GET("chart.artists.get?page=1&page_size=10&country=it&apikey=fa7e43f6fb9e2f10cd8f1322dbbe8dbc")
    Call<JsonObject> getArtist();

    @GET
    Call<JsonObject> getSearchArtist(@Url String url);
   /* http://api.musixmatch.com/ws/1.1/track.search?q_artist=justin%20bieber&page_size=3&page=1&s_track_rating=desc&apikey=fa7e43f6fb9e2f10cd8f1322dbbe8dbc*/
    @GET
    Call<JsonObject> getSearchTrack(@Url String url);
    /*http://api.musixmatch.com/ws/1.1/track.search?q_track=Despacito&page_size=3&page=1&s_track_rating=desc&apikey=fa7e43f6fb9e2f10cd8f1322dbbe8dbc*/

}
