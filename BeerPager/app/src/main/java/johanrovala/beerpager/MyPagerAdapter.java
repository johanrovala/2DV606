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

        fragments[0] = ExampleFragment.create(R.string.mornin_delight_title, R.string.mornin_delight_rating, R.string.mornin_delight_brewery, R.string.mornin_delight_style, R.string.mornin_delight_ABV, R.mipmap.ic_mornin_delight, R.string.mornin_delight_review);
        fragments[1] = ExampleFragment.create(R.string.mornin_delight_title, R.string.mornin_delight_rating, R.string.mornin_delight_brewery, R.string.mornin_delight_style, R.string.mornin_delight_ABV, R.mipmap.ic_mornin_delight, R.string.mornin_delight_review);
        fragments[2] = ExampleFragment.create(R.string.mornin_delight_title, R.string.mornin_delight_rating, R.string.mornin_delight_brewery, R.string.mornin_delight_style, R.string.mornin_delight_ABV, R.mipmap.ic_mornin_delight, R.string.mornin_delight_review);
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
