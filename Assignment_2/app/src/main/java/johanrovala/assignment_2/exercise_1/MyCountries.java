package johanrovala.assignment_2.exercise_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import johanrovala.assignment_2.R;

public class MyCountries extends Activity implements CalendarProviderClient{

    private SimpleCursorAdapter adapter;
    private int eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_countries);
        getMyCountriesCalendarId();

        ListView listView = (ListView) findViewById(R.id.listview_test);
        //final String[] test = {EVENTS_LIST_PROJECTION[PROJ_EVENTS_LIST_TITLE_INDEX], EVENTS_LIST_PROJECTION[PROJ_EVENTS_LIST_DTSTART_INDEX]};

        adapter = new MyCursorAdapter(this, R.layout.country_listview, null, EVENTS_LIST_PROJECTION, new int[]{R.id.year_id}, 0);
        listView.setAdapter(adapter);
        getLoaderManager().initLoader(LOADER_MANAGER_ID, null, this);
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

   /* public static void addToUserCountries(String country){
        userCountries.add(country);
        //addNewEvent(userCountries.get(0), userCountries.get(1));
    }*/

    @Override
    public long getMyCountriesCalendarId() {
        long id = -1;
        Cursor cursor = getContentResolver().query(CALENDARS_LIST_URI, CALENDARS_LIST_PROJECTION, CALENDARS_LIST_SELECTION,
                                                    CALENDARS_LIST_SELECTION_ARGS, null);

        if(!cursor.moveToFirst()){
            Uri uri = asSyncAdapter(CALENDARS_LIST_URI, ACCOUNT_TITLE, CalendarContract.ACCOUNT_TYPE_LOCAL);
            ContentValues cv = new ContentValues();
            cv.put(CalendarContract.Calendars.ACCOUNT_NAME, ACCOUNT_TITLE);
            cv.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
            cv.put(CalendarContract.Calendars.NAME, CALENDAR_TITLE);
            cv.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_TITLE);
           // cv.put(CalendarContract.Calendars.CALENDAR_COLOR, yourColor);
            cv.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
            cv.put(CalendarContract.Calendars.OWNER_ACCOUNT, ACCOUNT_TITLE);
            cv.put(CalendarContract.Calendars.VISIBLE, 1);
            cv.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
            Uri result = getContentResolver().insert(uri, cv);
            id = ContentUris.parseId(result);
            System.out.println("if: " + id);
        }else {
            id =  cursor.getLong(PROJ_CALENDARS_LIST_ID_INDEX);
            System.out.println("else: " + id);
        }
        return id;
    }

    @Override
    public void addNewEvent(int year, String country) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, CalendarUtils.getEventStart(year));
        values.put(CalendarContract.Events.DTEND, CalendarUtils.getEventEnd(year));
        values.put(CalendarContract.Events.CALENDAR_ID, getMyCountriesCalendarId());
        values.put(CalendarContract.Events.TITLE, country);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);
        long event_ID = Long.parseLong(uri.getLastPathSegment());
        getLoaderManager().restartLoader(LOADER_MANAGER_ID, null, this);
    }

    @Override
    public void updateEvent(int eventId, int year, String country) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, CalendarUtils.getEventStart(year));
        values.put(CalendarContract.Events.DTEND, CalendarUtils.getEventEnd(year));
        values.put(CalendarContract.Events.CALENDAR_ID, getMyCountriesCalendarId());
        values.put(CalendarContract.Events.TITLE, country);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        Uri uri = ContentUris.withAppendedId(EVENTS_LIST_URI, eventId);
        contentResolver.update(uri, values, null, null);
        getLoaderManager().restartLoader(LOADER_MANAGER_ID, null, this);
    }

    @Override
    public void deleteEvent(int eventId) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        Uri uri = ContentUris.withAppendedId(EVENTS_LIST_URI, eventId);
        contentResolver.delete(uri, null, null);
        getLoaderManager().restartLoader(LOADER_MANAGER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_MANAGER_ID:
                return new CursorLoader(this, EVENTS_LIST_URI, EVENTS_LIST_PROJECTION, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case LOADER_MANAGER_ID:
                adapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    static Uri asSyncAdapter(Uri uri, String account, String accountType) {
        return uri.buildUpon()
                .appendQueryParameter(android.provider.CalendarContract.CALLER_IS_SYNCADAPTER,"true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, account)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, accountType).build();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result){
        String country = "";
        int year = 0;
        System.out.println("onActivityResult");
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 0:
                    System.out.println("case 0");
                    country = result.getStringExtra("COUNTRY");
                    year = result.getIntExtra("YEAR", 0);
                    addNewEvent(year, country);
                    break;
                case 1:
                    System.out.println("case 1");
                    country = result.getStringExtra("COUNTRY");
                    year = result.getIntExtra("YEAR", 0);
                    System.out.println("ID in cas 1: " + eventID);
                    updateEvent(eventID, year, country);
            }
        }
    }

    class MyCursorAdapter extends SimpleCursorAdapter {

        private Context mContext;
        private Context appContext;
        private int layout;
        private Cursor cr;
        private final LayoutInflater inflater;

        public MyCursorAdapter(Context context,int layout, Cursor c,String[] from,int[] to, int flags) {
            super(context,layout,c,from,to, flags);
            this.layout=layout;
            this.mContext = context;
            this.inflater=LayoutInflater.from(context);
            this.cr=c;
        }

        @Override
        public View newView (Context context, Cursor cursor, ViewGroup parent) {
            return inflater.inflate(layout, null);
        }



        @Override
        public void bindView(final View view, final Context context, final Cursor cursor) {
            super.bindView(view, context, cursor);
            TextView country = (TextView) view.findViewById(R.id.country_id);
            TextView year = (TextView) view.findViewById(R.id.year_id);
            final int yearVal = CalendarUtils.getEventYear(cursor.getLong(CalendarProviderClient.PROJ_EVENTS_LIST_DTSTART_INDEX));
            final String countryVal = cursor.getString(CalendarProviderClient.PROJ_EVENTS_LIST_TITLE_INDEX);
            final int id = cursor.getInt(CalendarProviderClient.PROJ_EVENTS_LIST_ID_INDEX);

            year.setText(String.valueOf(yearVal));
            country.setText(countryVal);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //UpdateDeleteCountryDialog dialog = new UpdateDeleteCountryDialog();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("ÖHHH");
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, UpdateDeleteCountry.class);
                            System.out.println("ID in viewclick: " + id);
                          //  intent.putExtra("ID", id);
                            eventID = id;
                            startActivityForResult(intent, 1);


                        }
                    });

                    builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            eventID = id;
                            deleteEvent(eventID);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        }
    }
}

