package id.co.codelabs.weather.model.service;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.co.codelabs.weather.model.Cuaca;
import id.co.codelabs.weather.utility.OkHttpRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by bayu_wpp on 7/30/2016.
 */
public class WeatherApiImpl implements WeatherApi {
    protected final static String API_URL = "http://api.openweathermap.org/data/2.5/group?id=1650357,1215502,1625084,1646170,1633070,1643776,1651531,1214520,1624647,1648473,1630789,1629001,1642911,1621177,1627896,1625822,8064082,1645528,1643837,2082600&units=metric&APPID=b35ad63f5f5ced9b4bceaf049edf6dfb";

    @Override
    public void getAllWeathers(WeatherServiceCallback<List<Cuaca>> callback) {
        new LoadWeatherAsyncTask(callback).execute();
    }

    private class LoadWeatherAsyncTask extends AsyncTask<Void, Void, List<Cuaca>> {
        private final WeatherServiceCallback mCallback;
        List<Cuaca> cuaca = new ArrayList<>();
        public LoadWeatherAsyncTask(WeatherServiceCallback<List<Cuaca>> callback) {
            mCallback = callback;
        }

        @Override
        protected List<Cuaca> doInBackground(Void... voids) {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder content = new StringBuilder();

                String line;
                while ((line = in.readLine()) != null) {
                    content.append(line);
                }

                JSONArray list = new JSONObject(content.toString()).getJSONArray("list");

                List<Cuaca> weathers = new ArrayList<>();

                JSONObject object;

                for (int i = 0; i < list.length(); i++) {
                    object = list.getJSONObject(i);

                    float temp = BigDecimal.valueOf(object.getJSONObject("main").getDouble("temp")).floatValue();
                    float windSpeed = BigDecimal.valueOf(object.getJSONObject("wind").getDouble("speed")).floatValue();

                    weathers.add(new Cuaca(temp, windSpeed, object.getString("name"), object.getJSONArray("weather").getJSONObject(0).getString("description")));
                }

                return weathers;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<Cuaca> cuacaList) {

            if (cuacaList != null) {
                mCallback.onSuccess(cuacaList);
            } else {
                mCallback.onFailure();
            }
        }
    }
}
