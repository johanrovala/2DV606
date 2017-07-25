package johanrovala.assignment_2.AlarmClock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import johanrovala.assignment_2.R;

/*
 * AlarmClock
 * Desc: Activity for the main layout of the alarmclock app. Takes responsibility for:
 *       * Updating the current time through updateTime()
 *       *
 */

public class AlarmClock extends Activity {

    private static final String HOUR = "HOUR";
    private static final String MINUTES = "MINUTES";

    private TextView currentTime;
    private TextView alarmTime;
    private Button setAlarmButton;
    private Button cancelAlarmButton;
    private Button updateAlarmButton;
    private Handler timeUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);

        setUpUIComponents();
        updateTime();
    }

    private void setUpUIComponents() {
        currentTime = (TextView) findViewById(R.id.currenttime_textview);
        alarmTime = (TextView) findViewById(R.id.alarmtime_textview);
        setAlarmButton = (Button) findViewById(R.id.set_alarm_button);
        cancelAlarmButton = (Button) findViewById(R.id.cancel_alarm_button);
        updateAlarmButton = (Button) findViewById(R.id.update_alarm_button);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarmSetUp();
            }
        });
        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alarmIsSet()){
                    cancelAlarm();
                }
                else{
                    String message = "No alarm is set.";
                    Toast.makeText(AlarmClock.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alarmIsSet()){
                    cancelAlarm();
                    startAlarmSetUp();                }
                else{
                    String message = "No alarm is set.";
                    Toast.makeText(AlarmClock.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateTime() {
        timeUpdater = new Handler();
        timeUpdater.postDelayed(new Runnable() {

            @Override
            public void run() {
                long time = System.currentTimeMillis();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String timeString = timeFormat.format(time);
                currentTime.setText(timeString);
                // Keep iterating this thread every second in order to update the current time.
                timeUpdater.postDelayed(this, 1000);
            }
        }, 10);
    }

    private void setAlarmFromSetupResult(String hours, String minutes) {

        // Config alarm

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("message", "Alarm has executed");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // Config time

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
        cal.set(Calendar.MINUTE, Integer.parseInt(minutes));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date date = cal.getTime();
        long endTime = date.getTime();

        // Schedule alarm

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, endTime, pendingIntent);

        // Notify user
        System.out.println("Success");
        String message = "The alarm will go off at: " + date.getHours() + ":" + date.getMinutes();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    private void cancelAlarm(){
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        String message = "The alarm was canceled";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        System.out.println("Alarm canceled.");
    }


    private boolean alarmIsSet(){
        Intent intent = new Intent(this, AlarmReceiver.class);
        return (PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
    }

    private void startAlarmSetUp() {
        Intent intent = new Intent(getBaseContext(), AlarmClockSetup.class);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result){
        String hours = "";
        String minutes = "";
        System.out.println(resultCode);
        System.out.println(RESULT_OK);
        if(resultCode == RESULT_OK){
            System.out.println("RESULT_OK");
            hours = result.getStringExtra(HOUR);
            minutes = result.getStringExtra(MINUTES);
            setAlarmFromSetupResult(hours, minutes);
        }
        else{
            System.out.println("Error when fetching result from alarm setup");
        }
    }
}


