package johanrovala.assignment_2.exercise_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import johanrovala.assignment_2.R;

public class UpdateDeleteCountryDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_title);
        builder.setPositiveButton(R.string.update_button_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // If user wishes to get help with his or her obesity, a url will open to the weighwatchers website.

             //   Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.weightwatchers.com/us/"));
              //  startActivity(openBrowser);
               // Intent intent = new Intent(getContext(), UpdateDeleteCountry.class);
               // startActivity(intent);
            }
        }).setNegativeButton(R.string.delete_button_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
