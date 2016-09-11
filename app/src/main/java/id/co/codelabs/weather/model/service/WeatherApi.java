package id.co.codelabs.weather.model.service;

import java.util.List;

import id.co.codelabs.weather.model.Cuaca;

/**
 * Created by bayu_wpp on 7/30/2016.
 */
public interface WeatherApi {
    interface WeatherServiceCallback<T> {
        void onSuccess(T weathers);
        void onFailure();
    }

    void getAllWeathers(WeatherServiceCallback<List<Cuaca>> callback);
}
