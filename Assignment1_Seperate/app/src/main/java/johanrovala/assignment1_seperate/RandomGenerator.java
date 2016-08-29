package johanrovala.assignment1_seperate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class RandomGenerator extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        /*
         * Button is initialized from the xml file and a onclick-function is set to call
         * updateTextView() and update our number.
         */

        Button randomButton = (Button) findViewById(R.id.random_button);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextView();
            }
        });
    }


    /*
     * Updates the number of our TextView get randomized, values range from 1-100.
     */

    private void updateTextView(){
        Random rand = new Random();

        int randomNumber = rand.nextInt(100 - 1 + 1) + 1;
        TextView randomText = (TextView) findViewById(R.id.random_text);
        randomText.setText(String.valueOf(randomNumber));
    }
}
