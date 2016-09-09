/**
 * VaxjoWeather.java
 * Created: May 9, 2010
 * Jonas Lundberg, LnU
 */

package johanrovala.assignment1_seperate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is a first prototype for a weather app. It is currently 
 * only downloading weather data for Växjö. 
 * 
 * This activity downloads weather data and constructs a WeatherReport,
 * a data structure containing weather data for a number of periods ahead.
 * 
 * The WeatherHandler is a SAX parser for the weather reports 
 * (forecast.xml) produced by www.yr.no. The handler constructs
 * a WeatherReport containing meta data for a given location
 * (e.g. city, country, last updated, next update) and a sequence 
 * of WeatherForecasts.
 * Each WeatherForecast represents a forecast (weather, rain, wind, etc)
 * for a given time period.
 * 
 * The next task is to construct a list based GUI where each row 
 * displays the weather data for a single period.
 * 
 *  
 * @author jlnmsi
 *
 */

public class VaxjoWeather extends AppCompatActivity {
	public static String TAG = "dv606.weather";

	private InputStream input;
	private WeatherReport report = null;
	private MyAdapter adapter;
	private ArrayList<WeatherForecast> list;
	private ListView listView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// Initialize the layout
			setContentView(R.layout.main);
			// Initialize the toolbar
		//	Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
		//	setSupportActionBar(myToolbar);

			listView = (ListView) findViewById(R.id.listview);

			if (isDeviceConnected()) {
				try {
					URL url = new URL("http://www.yr.no/sted/Sverige/Kronoberg/V%E4xj%F6/forecast.xml");
					AsyncTask task = new WeatherRetriever().execute(url);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
    }

	public boolean isDeviceConnected(){
		ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnectedOrConnecting();
	}

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_menu, menu);
        return true;
    }
    
    private void printReportToLog() {
    	if (this.report != null) {
            /*Print some meta data to the UI for the testing purposes*/
            TextView placeholder = (TextView) findViewById(R.id.placeholder);
            placeholder.append(report.getCity() +  ", " + report.getCountry() + "\n" + "Last Updated: "
			+ report.getLastUpdated() + "\n" + "Next Update: " + report.getNextUpdate());

        	/* Print location meta data */
			Log.i(TAG, report.toString());

			list = new ArrayList<>();

        	/* Print forecasts */
    		int count = 0;
    		for (WeatherForecast forecast : report) {
    			count++;
				Log.i(TAG, "Forecast #" + count);
				Log.i(TAG, forecast.toString());
				list.add(forecast);

    			//System.out.p
    			// rintln("Forecast "+count);
    			//System.out.println( forecast.toString() );
    		}
			adapter = new MyAdapter(this, R.layout.weather_view, list);
			listView.setAdapter(adapter);
    	}
    	else {
    		Log.e(TAG, "Weather report has not been loaded.");
    	}
    }
    
    private class WeatherRetriever extends AsyncTask<URL, Void, WeatherReport> {
    	protected WeatherReport doInBackground(URL... urls) {
    		try {
    			return WeatherHandler.getWeatherReport(urls[0]);
    		} catch (Exception e) {
    			throw new RuntimeException(e);
    		}
    	}

    	protected void onProgressUpdate(Void... progress) {

    	}

    	protected void onPostExecute(WeatherReport result) {
			Toast.makeText(getApplicationContext(), "WeatherRetriever task finished", Toast.LENGTH_LONG).show();

    		report = result;
			printReportToLog();
    	}
    }
}