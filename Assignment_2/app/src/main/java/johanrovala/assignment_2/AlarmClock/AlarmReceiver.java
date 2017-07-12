package johanrovala.assignment_2.AlarmClock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by johanrovala on 12/07/17.
 */
public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("message");
        System.out.println(msg);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
