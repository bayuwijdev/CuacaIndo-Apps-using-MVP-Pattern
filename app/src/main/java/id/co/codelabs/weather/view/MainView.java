package id.co.codelabs.weather.view;

import java.util.List;

import id.co.codelabs.weather.model.Cuaca;

/**
 * Created by bayu_ on 8/06/2016.
 */
public interface MainView {
    void showProgress();
    void hideProgress();
    void showWeatherClickedMessage(Cuaca cuaca);
    void showWeathers(List<Cuaca> cuacaList);
    void showConnectionError();
}
