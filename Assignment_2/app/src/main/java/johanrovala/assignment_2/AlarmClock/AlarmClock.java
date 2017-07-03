package johanrovala.assignment_2.AlarmClock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

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

        //SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        //String timeString = alarmHourString + ":" + alarmMinuteString + ":00";
        //format.format(timeString);

    }

    private void startAlarmSetUp() {
        Intent intent = new Intent(getBaseContext(), AlarmClockSetup.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result){
        String hours = "";
        String minutes = "";
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 0:
                    hours = result.getStringExtra(HOUR);
                    minutes = result.getStringExtra(MINUTES);
                    setAlarmFromSetupResult(hours, minutes);
                    break;
                default:
                    System.out.println("Error when fetching result from alarm setup");
                    break;
            }
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
