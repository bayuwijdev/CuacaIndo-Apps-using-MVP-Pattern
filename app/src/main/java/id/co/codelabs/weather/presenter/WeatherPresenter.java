package id.co.codelabs.weather.presenter;

import id.co.codelabs.weather.model.Cuaca;

/**
 * Created by bayu_ on 8/06/2016.
 */
public interface WeatherPresenter {
    void loadWeatherData();
    void itemClick(Cuaca cuaca);
}
