package johanrovala.assignment_2.AlarmClock;

/**
 * Created by johanrovala on 24/07/17.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

public class AlarmDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone ringtone = RingtoneManager.getRingtone(this, notification);
        ringtone.play();


        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("Cancel alarm").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ringtone.stop();
                Intent intent = new Intent(getApplicationContext(), AlarmClock.class);
                startActivity(intent);
            }
        });

        AlertDialog ad = ab.create();
        ad.show();

    }
}

