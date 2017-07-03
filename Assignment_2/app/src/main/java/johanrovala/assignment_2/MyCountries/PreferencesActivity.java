package johanrovala.assignment_2.MyCountries;

import android.preference.PreferenceActivity;

import java.util.List;

import johanrovala.assignment_2.R;

/**
 * Created by johanrovala on 05/06/17.
 */
public class PreferencesActivity extends PreferenceActivity{

    @Override
    public void onBuildHeaders(List<Header> target){
        loadHeadersFromResource(R.xml.preferences_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName){
        return (Preferences.class.getName().equals(fragmentName));
    }


}
