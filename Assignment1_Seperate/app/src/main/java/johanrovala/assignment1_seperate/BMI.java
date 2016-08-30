package johanrovala.assignment1_seperate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/*
 * This class is the main activity for the bmi calculator. It allows for the US and EU metric system and will
 * eventually allow for a popup window if the user has a BMI greater than 30, click ok in this window will result
 * in the device calling it's browser with a url to the weightwatchers website.
 */

public class BMI extends Activity {

    private boolean properMetricSystem = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        // Instantiated all important components from our view

        final EditText heightText = (EditText) findViewById(R.id.height_field);
        final EditText weightText = (EditText) findViewById(R.id.weight_field);

        final TextView bmiText = (TextView) findViewById(R.id.bmi_textview);

        Button calculateButton = (Button) findViewById(R.id.calculate_button);
        Button resetButton = (Button) findViewById(R.id.reset_button);

        final CheckBox metricCheckBox = (CheckBox) findViewById(R.id.metric_checkbox);
        final TextView usMetricTextView = (TextView) findViewById(R.id.bmi_us_metric);
        final TextView euMetricTextView = (TextView) findViewById(R.id.bmi_eu_metric);

        metricCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            /*
             * Is there a way to not have to set textcolor, text etc from the
             * controller type class (this class). Rather having the xml file listen for changes
             * in some method called in the controller?
             */

            // If our checkbox is activiated we will use the EU metric

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    properMetricSystem = true;
                    usMetricTextView.setTextColor(getResources().getColor(R.color.grey));
                    euMetricTextView.setTextColor(getResources().getColor(R.color.black));
                    heightText.setHint(R.string.bmi_eu_meters);
                    weightText.setHint(R.string.bmi_eu_kg);

            // If our checkbox is not activated we will use the US metric system.

                }else{
                    properMetricSystem = false;
                    usMetricTextView.setTextColor(getResources().getColor(R.color.black));
                    euMetricTextView.setTextColor(getResources().getColor(R.color.grey));
                    heightText.setHint(R.string.bmi_us_inches);
                    weightText.setHint(R.string.bmi_us_pounds);
                }

            }
        });


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String h = heightText.getText().toString();
                String w = weightText.getText().toString();

                // Error handling to make sure that nothing is calculated unless it's a number.

                if(properMetricSystem){
                    try{
                        bmiText.setText(calculateBmiEUMetric(Double.valueOf(h), Double.valueOf(w)));
                    }catch (NumberFormatException e){
                        Toast.makeText(BMI.this, R.string.bmi_error_textinput, Toast.LENGTH_LONG).show();
                    }
                }else if(!properMetricSystem){
                    try{
                        bmiText.setText(calculateBmiUSMetric(Double.valueOf(h), Double.valueOf(w)));
                    }catch (NumberFormatException e){
                        Toast.makeText(BMI.this, R.string.bmi_error_textinput, Toast.LENGTH_LONG).show();
                    }
                }

                if( Double.valueOf(calculateBmiEUMetric(Double.valueOf(h), Double.valueOf(w))) > 30 || Double.valueOf(calculateBmiEUMetric(Double.valueOf(h), Double.valueOf(w))) > 30){
                    ObesityHelpDialog obesityHelpDialog = new ObesityHelpDialog();
                    // Not really sure what String tag (2nd parameter does).
                    obesityHelpDialog.show(getFragmentManager(), "");
                }
            }
        });

        // Reset all values if reset button is clicked

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmiText.setText(R.string.bmi_startup_number);
                heightText.setText("");
                weightText.setText("");
            }
        });

     }


    // Methods for calculating EU and US metrics.

    private String calculateBmiEUMetric(Double height, Double weight){

        DecimalFormat numberFormat = new DecimalFormat("#.00");

        double denominator = height * height;
        return numberFormat.format((weight / denominator));

    }

    private String calculateBmiUSMetric(Double height, Double weight){

        DecimalFormat numberFormat = new DecimalFormat("#.00");

        double denominator = height * height;
        return numberFormat.format(703 * weight / denominator);

    }
}
