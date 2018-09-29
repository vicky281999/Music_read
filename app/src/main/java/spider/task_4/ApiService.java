package spider.task_4;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static spider.task_4.Api.BASE_URL;

public class ApiService {

    private static Api api = null;

    private ApiService() {

    }

    public static Api getInstance() {

        OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        api = retrofit.create(Api.class);

        return api;
    }
}

