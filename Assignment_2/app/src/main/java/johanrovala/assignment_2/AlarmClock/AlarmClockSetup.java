package johanrovala.assignment_2.AlarmClock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import johanrovala.assignment_2.R;

public class AlarmClockSetup extends Activity {

    private EditText hourInput;
    private EditText minuteInput;
    private Button finishButton;
    private static final String HOUR = "HOUR";
    private static final String MINUTES = "MINUTES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock_setup);

        setUpUIComponents();

    }

    private void setUpUIComponents() {
        hourInput = (EditText) findViewById(R.id.alarm_input_hour_edittext);
        minuteInput = (EditText) findViewById(R.id.alarm_input_minute_edittext);
        finishButton = (Button) findViewById(R.id.alarm_input_finish_button);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alarmHourString = String.valueOf(hourInput.getText());
                String alarmMinuteString = String.valueOf(minuteInput.getText());
                System.out.println("In AlarmClockSetup Button" + RESULT_OK);

                Intent intent = new Intent();
                intent.putExtra(HOUR, alarmHourString);
                intent.putExtra(MINUTES, alarmMinuteString);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
