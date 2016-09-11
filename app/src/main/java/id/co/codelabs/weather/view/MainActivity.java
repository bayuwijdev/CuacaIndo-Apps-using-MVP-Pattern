package id.co.codelabs.weather.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import id.co.codelabs.weather.R;
import id.co.codelabs.weather.adapter.WeatherAdapter;
import id.co.codelabs.weather.model.Cuaca;
import id.co.codelabs.weather.model.service.WeatherApiImpl;
import id.co.codelabs.weather.presenter.WeatherPresenterImpl;

public class MainActivity extends BaseActivity implements MainView, WeatherAdapter.WeatherItemListener  {
    private WeatherPresenterImpl mPresenter;

    private WeatherAdapter weatherAdapter;
    private SwipeRefreshLayout mSrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new WeatherPresenterImpl(this,new WeatherApiImpl());

        initSwipeRefreshLayout();
        initRecyclerView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadWeatherData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mPresenter.loadWeatherData();
                break;
        }
        return true;
    }

    private void initSwipeRefreshLayout() {
        mSrl = (SwipeRefreshLayout) findViewById(R.id.swiperlayout_main);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadWeatherData();
            }
        });
    }

    private void initRecyclerView() {
        weatherAdapter = new WeatherAdapter(this, this);
        RecyclerView rvWeatherList = (RecyclerView) findViewById(R.id.recyclerview_main);
        rvWeatherList.setLayoutManager(new LinearLayoutManager(this));
        rvWeatherList.setAdapter(weatherAdapter);
    }

    @Override
    public void showProgress() {
        if(!mSrl.isRefreshing()) {

            // make sure setRefreshing() is called after the layout done everything else
            mSrl.post(new Runnable() {
                @Override
                public void run() {
                    mSrl.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void hideProgress() {
        if(mSrl.isRefreshing()) {
            mSrl.setRefreshing(false);
        }
    }

    @Override
    public void showWeatherClickedMessage(Cuaca cuaca) {
        Log.e("Kota",cuaca.getCityName()+" di klik");
        Toast.makeText(this, "Kota " + cuaca.getCityName()+ " dengan temperatur "+ cuaca.getTemperature()+"\u00B0C", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWeathers(List<Cuaca> cuacaList) {
        weatherAdapter.replaceData(cuacaList);
    }

    @Override
    public void showConnectionError() {
        Toast.makeText(MainActivity.this, "Failed to connect, please check your connection and try again!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWeatherItemClick(Cuaca item) {
        mPresenter.itemClick(item);
    }

}
