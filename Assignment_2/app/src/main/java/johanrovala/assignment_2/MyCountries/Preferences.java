package johanrovala.assignment_2.MyCountries;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import johanrovala.assignment_2.R;

/**
 * Created by johanrovala on 05/06/17.
 */
public class Preferences extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
