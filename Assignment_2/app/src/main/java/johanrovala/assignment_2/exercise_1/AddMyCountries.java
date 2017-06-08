package johanrovala.assignment_2.exercise_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import johanrovala.assignment_2.R;

public class AddMyCountries extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_countries);

        final EditText yearText = (EditText) findViewById(R.id.year_id);
        final EditText countryText = (EditText) findViewById(R.id.country_id);
        Button addButton = (Button) findViewById(R.id.add_year_and_country_button);
        Button backButton = (Button) findViewById(R.id.back_to_main_activity_my_countries);

        /*
         * I wanted to be able to add several countries before returning to the old activity.
         * Therefor, I have commented out the code suggested in the lecture notes and went for a
         * static arraylist in the first activity which is updated.
         */

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String country = "";
                int year = 0;
                try {
                    country = countryText.getText().toString();
                    year = Integer.valueOf(yearText.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(AddMyCountries.this, R.string.error_input, Toast.LENGTH_SHORT).show();
                }
                Intent result = new Intent();
                result.putExtra("YEAR", year);
                result.putExtra("COUNTRY", country);
                setResult(RESULT_OK, result);
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyCountries.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_my_countries, menu);
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
