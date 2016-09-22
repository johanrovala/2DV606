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

public class UpdateDeleteCountry extends Activity {

    private int year;
    private String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated_country);

        final EditText yearText = (EditText) findViewById(R.id.year_edit_text);
        final EditText countryText = (EditText) findViewById(R.id.country_edit_text);
        Button updateButton = (Button) findViewById(R.id.update_button);
        Button backButton = (Button) findViewById(R.id.back_button);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    country = countryText.getText().toString();
                    year = Integer.valueOf(yearText.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(UpdateDeleteCountry.this, R.string.error_input, Toast.LENGTH_SHORT).show();
                }
                Intent result = new Intent();
                result.putExtra("YEAR", year);
                result.putExtra("COUNTRY", country);
                System.out.println("ID: " + result.getExtras().get("ID"));
                setResult(RESULT_OK, result);
                System.out.println("result set");
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_updated_country, menu);
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
