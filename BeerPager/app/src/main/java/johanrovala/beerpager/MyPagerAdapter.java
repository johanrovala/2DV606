package johanrovala.beerpager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

/**
 * Created by johanrovala on 08/09/16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter{

    private Fragment[] fragments = new Fragment[3];

    public MyPagerAdapter(FragmentManager fm){
        super(fm);

        fragments[0] = ExampleFragment.create("First", "1");
        fragments[1] = ExampleFragment.create("Second", "2");
        fragments[2] = ExampleFragment.create("Third", "3");

    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
