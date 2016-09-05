package dv606.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by johanrovala on 02/09/16.
 */
public class MyAdapter extends ArrayAdapter<WeatherForecast>{

    public MyAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
    }

    public MyAdapter(Context context, int resource, List<WeatherForecast> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int pos, View rowView, ViewGroup parent){
        View row = rowView;

        if (row == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.weather_view, null);
        }

        WeatherForecast wf = getItem(pos);

        if(wf != null){
            TextView dateAndTime = (TextView) row.findViewById(R.id.date_and_time_period);
            TextView weatherType = (TextView) row.findViewById(R.id.weather_type);
            TextView windSpeed = (TextView) row.findViewById(R.id.wind_speed);
            TextView tempAndRain = (TextView) row.findViewById(R.id.temp_and_rain);

            dateAndTime.setText("From: " + wf.getStartHHMM() + " To: " + wf.getEndHHMM());
            weatherType.setText("Weather: " + wf.getWeatherName());
            windSpeed.setText("Wind: " + wf.getWindDirection() + " Speed: " + wf.getWindSpeed());
            tempAndRain.setText("Temperature: " + wf.getTemperature() + " Rain: " + wf.getRain());


        }
        return row;
    }
}
