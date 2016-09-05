/**
 * WeatherForecast.java
 * Created: May 9, 2010
 * Jonas Lundberg, LnU
 */
package dv606.weather;


import java.util.Date;

/**
 * Each WeatherForecast represents a forecast (weather, rain, wind, temperature, etc)
 * for a given time period.
 * 
 *  ToDO: Translate weather and wind names to English!
 *  
 * @author jonasl
 *
 */
public class WeatherForecast {
	private Date startTime, endTime;
	private int period_code, weather_code;
	private String weather_name;  // Name in Norwegian!
	private String wind_direction, direction_name; // Name in Norwegian!
	private double rain, wind_speed; 
	private String speed_name;
	private int temperature;
	
	/*
	 * Access methods
	 */
	
	/* Time period */
	public String getStartYYMMDD() {return TimeUtils.getYYMMDD(startTime);}
	public String getStartHHMM() {return TimeUtils.getHHMM(startTime);}
	public String getEndYYMMDD() {return TimeUtils.getYYMMDD(endTime);}
	public String getEndHHMM() {return TimeUtils.getHHMM(endTime);}
	public String getDayOfWeek() {return TimeUtils.getDayOfWeek(startTime);}
	public int getPeriodCode() {return period_code;}
	/* Weather */
	public String getWeatherName() {return weather_name;}
	public int getWeatherCode() {return weather_code;}
	/* Rain (mm/h), Temp (Celsius)*/
	public double getRain() {return rain;}
	public int getTemperature() {return temperature;}
	/* Wind */
	public String getWindDirection() {return wind_direction;}
	public String getWindDirectionName() {return direction_name;}
	public double getWindSpeed() {return wind_speed;}
	public String getWindSpeedName() {return speed_name;}
	public int getImageId(){
		if (weather_code == 1){
			return R.mipmap.ic_sunny;
		}
		if(weather_code == 2 || weather_code == 3){
			return R.mipmap.ic_cloudy_sunny;
		}
		if (weather_code == 4){
			return R.mipmap.ic_cloudy;
		}
		if (weather_code == 5 || weather_code == 6 || weather_code == 41 || weather_code == 40 || weather_code == 41){
			return R.mipmap.ic_sunny_cloudy_rain;
		}
		if (weather_code == 42 || weather_code == 7 || weather_code == 43){
			return R.mipmap.ic_rain_snow;
		}
		if (weather_code == 24 || weather_code == 6 || weather_code == 25){
			return R.mipmap.ic_thunder_rain;
		}
		return R.mipmap.ic_sunny_cloudy_rain;
	}
	
	
	/* 
	 * Methods used by WeatherHandler to build forecast 
	 * */	
	void setPeriod(String from, String to, String period) {
		//System.out.println(from+"\t"+to+"\t"+period);
		startTime = TimeUtils.getDate(from);
		endTime = TimeUtils.getDate(to);
		period_code = Integer.parseInt(period);
	}
	
	void setWeather(String num, String name) {
		//System.out.println(num+"\t"+name);
		weather_code = Integer.parseInt(num);
		weather_name = name;
	}
	
	void setWindDirection(String dir, String name) {
		//System.out.println(dir+"\t"+name);
		wind_direction = dir;
		direction_name = name;
	}
	
	void setWindSpeed(String num, String name) {
		//System.out.println(num+"\t"+name);
		wind_speed = Double.parseDouble(num);
		speed_name = name;
	}
	
	void setRain(String num) {  // Precipitation, nederbörd
		//System.out.println(num);
		rain = Double.parseDouble(num);
	}
	
	void setTemperature(String num) {
		//System.out.println(num);
		temperature = Integer.parseInt(num);
	}


	
	/*
	 * Diagnostics
	 */
	
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("Date: "+ TimeUtils.getYYMMDD(startTime));
		buf.append("\nFrom:" +TimeUtils.getHHMM(startTime)+", To: "+TimeUtils.getHHMM(endTime)
				   +", Period: "+period_code);
		buf.append("\nWeather: "+weather_name+", Code: "+weather_code);
		buf.append("\nWind: "+wind_direction+", Speed: "+wind_speed+"m/s");
		buf.append("\nTemperature: "+ temperature +", Rain: "+rain+"mm/h");
		return buf.toString();
	}
	
	


}
