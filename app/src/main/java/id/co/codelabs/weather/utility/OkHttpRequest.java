package id.co.codelabs.weather.utility;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by bayu_ on 7/27/2016.
 */
public class OkHttpRequest {
    public static Call getDataFromServer(String url) {
        Call call = null;

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        call = client.newCall(request);

        return call;
    }
}
