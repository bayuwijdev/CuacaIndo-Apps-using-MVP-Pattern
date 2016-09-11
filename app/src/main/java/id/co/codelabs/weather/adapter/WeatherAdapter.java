package id.co.codelabs.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.co.codelabs.weather.R;
import id.co.codelabs.weather.model.Cuaca;
import id.co.codelabs.weather.utility.MathUtil;

/**
 * Created by bayu_wpp on 7/27/2016.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private final Context mContext;
    private final WeatherItemListener mListener;

    private List<Cuaca> mList = new ArrayList<>();

    public WeatherAdapter(Context context, WeatherItemListener listener) {
        mContext = context;

        mListener = listener;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_main, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        Cuaca cuaca = mList.get(position);

        // \u00B0 is degree symbol
        String temp = MathUtil.getNoDecimal(cuaca.getTemperature()) + "\u00B0C";
        String windSpeed = "Angin "+ MathUtil.getNoDecimal(cuaca.getWindSpeed()) +" m/s";

        holder.mTvCity.setText(cuaca.getCityName());
        holder.mTvTemp.setText(temp);
        holder.mTvWindSpeed.setText(windSpeed);
        holder.mTvWeatherDesc.setText(cuaca.getWeatherDescription());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void replaceData(List<Cuaca> cuaca) {
        mList = cuaca;
        notifyDataSetChanged();
    }

    protected class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTvTemp;
        private final TextView mTvCity;
        private final TextView mTvWeatherDesc;
        private final TextView mTvWindSpeed;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTvTemp = (TextView) itemView.findViewById(R.id.tv_temp);
            mTvCity = (TextView) itemView.findViewById(R.id.tv_city);
            mTvWeatherDesc = (TextView) itemView.findViewById(R.id.tv_weather_desc);
            mTvWindSpeed = (TextView) itemView.findViewById(R.id.tv_wind_speed);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Cuaca weather = mList.get(position);

            mListener.onWeatherItemClick(weather);
        }
    }

    public interface WeatherItemListener {
        void onWeatherItemClick(Cuaca item);
    }
}