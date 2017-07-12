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

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarmSetUp();
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

        /*SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
        cal.set(Calendar.MINUTE, Integer.parseInt(minutes));
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        Date d = cal.getTime();
        format.format(d);*/

        long durationTime = 1000*3 + System.currentTimeMillis();
        System.out.println("actual time: " + durationTime);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("message", "The alarm has started");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmClock.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, durationTime, pendingIntent);
        Toast.makeText(this, "Alarm has been set", Toast.LENGTH_LONG).show();
    }

    private void startAlarmSetUp() {
        Intent intent = new Intent(getBaseContext(), AlarmClockSetup.class);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result){
        String hours = "";
        String minutes = "";
        System.out.println("RESULT OK in AlarmClock" + RESULT_OK);
        if(resultCode == RESULT_OK){
            hours = result.getStringExtra(HOUR);
            minutes = result.getStringExtra(MINUTES);
            System.out.println("hours: " + hours);
            System.out.println("minutes: " + minutes);
            setAlarmFromSetupResult(hours, minutes);
        }
        else {
            System.out.println("Error when fetching result from alarm setup");
        }
    }





    /*
     * Not Important, might remove later.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_clock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
