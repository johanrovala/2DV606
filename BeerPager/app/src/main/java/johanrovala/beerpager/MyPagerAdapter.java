package johanrovala.beerpager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

/**
 * Created by johanrovala on 08/09/16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter{

    private Fragment[] fragments = new Fragment[7];

    /*
     * Array of fragments are filled and created on startup. However loading the images and text resources are not done until
     * onStart is called for each individual fragment in ExampleFragment.java
     */

    public MyPagerAdapter(FragmentManager fm){
        super(fm);

        fragments[0] = ExampleFragment.create(R.string.mornin_delight_title, R.string.mornin_delight_rating, R.string.mornin_delight_brewery, R.string.mornin_delight_style, R.string.mornin_delight_ABV, R.mipmap.ic_mornin_delight, R.string.mornin_delight_review);
        fragments[1] = ExampleFragment.create(R.string.good_morning_title, R.string.good_morning_rating, R.string.good_morning_brewery, R.string.good_morning_style, R.string.good_morning_ABV, R.mipmap.ic_good_morning, R.string.good_morning_review);
        fragments[2] = ExampleFragment.create(R.string.pliny_the_younger_title, R.string.pliny_the_younger_rating, R.string.pliny_the_younger_brewery, R.string.pliny_the_younger_style, R.string.pliny_the_younger_ABV, R.mipmap.ic_pliny_the_younger, R.string.pliny_the_younger_review);
        fragments[3] = ExampleFragment.create(R.string.heady_topper_title, R.string.heady_topper_title_rating, R.string.heady_topper_title_brewery, R.string.heady_topper_title_style, R.string.heady_topper_title_ABV, R.mipmap.ic_heady_topper, R.string.heady_topper_title_review);
        fragments[4] = ExampleFragment.create(R.string.kentucky_brunch_brand_stout_title, R.string.kentucky_brunch_brand_stout_rating, R.string.kentucky_brunch_brand_stout_brewery, R.string.kentucky_brunch_brand_stout_style, R.string.kentucky_brunch_brand_stout_ABV, R.mipmap.ic_kentucky_brunch_brand_stout, R.string.kentucky_brunch_brand_stout_review);
        fragments[5] = ExampleFragment.create(R.string.pliny_the_elder_title, R.string.pliny_the_elder_rating, R.string.pliny_the_elder_brewery, R.string.pliny_the_elder_style, R.string.pliny_the_elder_ABV, R.mipmap.ic_russian_river_pliny_the_elder, R.string.pliny_the_elder_review);
        fragments[6] = ExampleFragment.create(R.string.norrlands_guld_export_title, R.string.norrlands_guld_export_rating, R.string.norrlands_guld_export_brewery, R.string.norrlands_guld_export_style, R.string.norrlands_guld_export_ABV, R.mipmap.ic_norrlands, R.string.norrlands_guld_export_review);

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
