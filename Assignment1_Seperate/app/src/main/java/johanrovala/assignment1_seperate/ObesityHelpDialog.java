package johanrovala.assignment1_seperate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by johanrovala on 30/08/16.
 * This class extends DialogFragment in order to create a Dialog where the user
 * can decide wether or not they want to do something about their weight problem.
 */
public class ObesityHelpDialog extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.bmi_over_30_help);
        builder.setPositiveButton(R.string.bmi_over_30_accept_help, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // If user wishes to get help with his or her obesity, a url will open to the weighwatchers website.

                Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.weightwatchers.com/us/"));
                startActivity(openBrowser);
            }
        }).setNegativeButton(R.string.bmi_over_30_reject_help, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
