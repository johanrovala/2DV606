package johanrovala.assignment_2.MyCountries;

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
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import johanrovala.assignment_2.R;

public class MyCountries extends Activity implements CalendarProviderClient{

    private static final String TAG = "MyCountries";

    private SimpleCursorAdapter adapter;
    private int eventID;
    private final String SORTINGPREFERENCE = "sortpref";
    private final String SORTINGORDER = "sortorder";
    private final String BGPREFERENCE = "bgpref";
    private final String BGCOLOR = "background_colors";
    private SharedPreferences prefs;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_countries);
        getMyCountriesCalendarId();

        listView = (ListView) findViewById(R.id.listview_test);
        adapter = new MyCursorAdapter(this, R.layout.country_listview, null, EVENTS_LIST_PROJECTION, new int[]{R.id.year_id}, 0);
        listView.setAdapter(adapter);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        getPreferences();
    }

    private void getPreferences() {

        // Sorting
        Bundle bundle = new Bundle();
        SharedPreferences sortPref = getSharedPreferences(SORTINGPREFERENCE, MODE_PRIVATE);
        String order = sortPref.getString(SORTINGORDER, "");
        if (order != null) {
            Log.v(TAG, "Sorting preferences retrieved");
            bundle.putString(SORTINGORDER, order);
            getLoaderManager().restartLoader(LOADER_MANAGER_ID, bundle, this);
        }
        else {
            Log.v(TAG, "Sorting preferences retrieval failed.");

        }

        // Background colors
        SharedPreferences bgprefs = PreferenceManager.getDefaultSharedPreferences(this);
        String bgColor = bgprefs.getString(BGCOLOR, "");
        int color;
        System.out.println("Fetched color string: " + bgColor);
        switch (bgColor){
            case "Blue":
                color = getResources().getColor(R.color.Blue);
                break;
            case "Red":
                color = getResources().getColor(R.color.Red);
                break;
            case "Orange":
                color = getResources().getColor(R.color.Orange);
                break;
            default:
                color= getResources().getColor(R.color.Default);
                break;
        }
        listView.setBackgroundColor(color);

        // Font styles
        SharedPreferences fontprefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean changeFont = fontprefs.getBoolean("changefont", false);
        SharedPreferences fontsizeprefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean changeSize = fontsizeprefs.getBoolean("changesize", false);

        if (changeFont && changeSize){
            listView = (ListView) findViewById(R.id.listview_test);
            adapter = new MyCursorAdapter(this, R.layout.country_listview_font_fontsize, null, EVENTS_LIST_PROJECTION, new int[]{R.id.year_id}, 0);
            listView.setAdapter(adapter);
        }
        else if(changeFont){
            listView = (ListView) findViewById(R.id.listview_test);
            adapter = new MyCursorAdapter(this, R.layout.country_listview_font, null, EVENTS_LIST_PROJECTION, new int[]{R.id.year_id}, 0);
            listView.setAdapter(adapter);
        }
        else if(changeSize){
            listView = (ListView) findViewById(R.id.listview_test);
            adapter = new MyCursorAdapter(this, R.layout.country_listview_fontsize, null, EVENTS_LIST_PROJECTION, new int[]{R.id.year_id}, 0);
            listView.setAdapter(adapter);
        }
        else {
            listView = (ListView) findViewById(R.id.listview_test);
            adapter = new MyCursorAdapter(this, R.layout.country_listview, null, EVENTS_LIST_PROJECTION, new int[]{R.id.year_id}, 0);
            listView.setAdapter(adapter);
        }
    }

    private void updateSortingPreferences(String sorting) {

        // Sorting
        Bundle bundle = new Bundle();
        SharedPreferences sortpref = getSharedPreferences(SORTINGPREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sortpref.edit();

        bundle.putString(SORTINGORDER, sorting);
        editor.putString(SORTINGORDER, sorting);
        editor.commit();
        getLoaderManager().restartLoader(LOADER_MANAGER_ID, bundle, this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_countries, menu);
        menu.add(0, 1, 0, "Sort by year (desc)");
        menu.add(0, 2, 1, "Sort by year (asc)");
        menu.add(0, 3, 2, "Sort by country (desc)");
        menu.add(0, 4, 3, "Sort by country (asc)");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        Intent intent;
        String sorting;

        switch (id) {
            case R.id.action_settings:
                intent = new Intent(this, PreferencesActivity.class);
                this.startActivityForResult(intent, 0);
                break;

            case R.id.action_add_country:
                intent = new Intent(this, AddMyCountries.class);
                this.startActivityForResult(intent, 0);
                break;

            case 1:
                sorting = CalendarContract.Events.DTSTART + " DESC";
                updateSortingPreferences(sorting);
                break;

            case 2:
                sorting = CalendarContract.Events.DTSTART + " ASC";
                updateSortingPreferences(sorting);
                break;

            case 3:
                sorting = CalendarContract.Events.TITLE + " DESC";
                updateSortingPreferences(sorting);
                break;

            case 4:
                sorting = CalendarContract.Events.TITLE + " ASC";
                updateSortingPreferences(sorting);
                break;

            default:
                sorting = CalendarContract.Events.DTSTART + " DESC";
                updateSortingPreferences(sorting);
        }

        return super.onOptionsItemSelected(item);
    }


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
        String calendar = CalendarContract.Events.CALENDAR_ID + "=" + getMyCountriesCalendarId();

        if (args != null){
            String sortorder = args.getString(SORTINGORDER);
            return new CursorLoader(this, EVENTS_LIST_URI, EVENTS_LIST_PROJECTION, calendar, null, sortorder);
        }
        else {
            return new CursorLoader(this, EVENTS_LIST_URI, EVENTS_LIST_PROJECTION, calendar, null, null);
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
        adapter.swapCursor(null);
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
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 0:
                    country = result.getStringExtra("COUNTRY");
                    year = result.getIntExtra("YEAR", 0);
                    addNewEvent(year, country);
                    break;
                case 1:
                    country = result.getStringExtra("COUNTRY");
                    year = result.getIntExtra("YEAR", 0);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, UpdateDeleteCountry.class);
                            System.out.println("ID in viewclick: " + id);
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

