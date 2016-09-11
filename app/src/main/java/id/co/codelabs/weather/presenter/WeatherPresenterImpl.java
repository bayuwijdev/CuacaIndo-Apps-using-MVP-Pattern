package id.co.codelabs.weather.presenter;

import java.util.List;

import id.co.codelabs.weather.model.Cuaca;
import id.co.codelabs.weather.model.service.WeatherApi;
import id.co.codelabs.weather.model.service.WeatherApiImpl;
import id.co.codelabs.weather.utility.EspressoIdlingResource;
import id.co.codelabs.weather.view.MainView;

/**
 * Created by bayu_ on 8/06/2016.
 */
public class WeatherPresenterImpl extends BasePresenter implements WeatherPresenter {
    private final MainView mainView;
    private final WeatherApiImpl mWeatherApi;

    public WeatherPresenterImpl(MainView view, WeatherApiImpl weatherApi) {
        mainView = view;
        mWeatherApi = weatherApi;
    }

    @Override
    public void loadWeatherData() {
        mainView.showProgress();

        EspressoIdlingResource.increment();

        mWeatherApi.getAllWeathers(new WeatherApi.WeatherServiceCallback<List<Cuaca>>() {
            @Override
            public void onSuccess(List<Cuaca> cuacaList) {
                EspressoIdlingResource.decrement();
                mainView.hideProgress();
                mainView.showWeathers(cuacaList);
            }

            @Override
            public void onFailure() {
                EspressoIdlingResource.decrement();
                mainView.showConnectionError();
                mainView.hideProgress();
            }
        });
    }

    @Override
    public void itemClick(Cuaca cuaca) {
        mainView.showWeatherClickedMessage(cuaca);
    }
}
