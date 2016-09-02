package johanrovala.assignment1_seperate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MyCountries extends Activity {

    private static ArrayList<String> userCountries = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_countries);
        System.out.println(userCountries.toString());


        ListView listView = (ListView) findViewById(R.id.listview_test);

        adapter = new ArrayAdapter<String>(this, R.layout.country_listview, R.id.mycountries_view, userCountries);
        listView.setAdapter(adapter);


        adapter.notifyDataSetChanged();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_countries, menu);
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
        else if(id == R.id.action_add_country){
            Intent intent = new Intent(this, AddMyCountries.class);
            this.startActivityForResult(intent, 0);
        }

        return super.onOptionsItemSelected(item);
    }

    public static void addToUserCountries(String country){
        userCountries.add(country);
    }

    public static ArrayList getUserCountries(){
        return userCountries;
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result){
        if(resultCode == RESULT_OK){
            String userCountry = result.getStringExtra("result");
            userCountries.add(userCountry);
            adapter.notifyDataSetChanged();
        }
    }*/
}
