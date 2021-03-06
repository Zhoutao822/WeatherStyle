package me.baron.weatherstyle.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.baron.library.adapter.BaseRecyclerViewAdapter;
import me.baron.weatherstyle.R;
import me.baron.weatherstyle.models.style.Weather;

/**
 * 城市管理页面Adapter
 *
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 *         16/3/16
 */
public class CityManagerAdapter extends BaseRecyclerViewAdapter<CityManagerAdapter.ViewHolder> {

    private final List<Weather> weatherList;

    public CityManagerAdapter(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city_manager, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        holder.city.setText(weather.getCityName());
        holder.weather.setText(weather.getRealTime().getWeather());
        holder.temp.setText(new StringBuilder().append(weather.getForecasts().get(0).getTempMin()).append("~").append(weather.getForecasts().get(0).getTempMax()).append("℃").toString());
        holder.deleteButton.setOnClickListener(v -> {
            Weather removeWeather = weatherList.get(holder.getAdapterPosition());
            weatherList.remove(removeWeather);
            notifyItemRemoved(holder.getAdapterPosition());

            if (onItemClickListener != null && onItemClickListener instanceof OnCityManagerItemClickListener) {
                ((OnCityManagerItemClickListener) onItemClickListener).onDeleteClick(removeWeather.getCityId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return weatherList == null ? 0 : weatherList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_delete)
        ImageButton deleteButton;
        @Bind(R.id.item_tv_city)
        TextView city;
        @Bind(R.id.item_tv_weather)
        TextView weather;
        @Bind(R.id.item_tv_temp)
        TextView temp;

        ViewHolder(View itemView, CityManagerAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> adapter.onItemHolderClick(ViewHolder.this));
        }
    }

    public interface OnCityManagerItemClickListener extends AdapterView.OnItemClickListener {

        void onDeleteClick(String cityId);
    }

}
