package johanrovala.assignment1_seperate;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.Override;

/**
 * Created by johanrovala on 08/09/16.
 */
public class ExampleFragment extends Fragment {

    /*
     * Key values assigned for the Bundle.
     */

    final static String TITLE = "title",RATING = "rating", BREWERY = "brewery", STYLE = "style", ABV = "abv" , IMAGE = "image", REVIEW = "description";
    private ViewGroup rootView;

    /*
     * create adds all given values from the parameters to the Bundle 'args'
     */

    public static ExampleFragment create(int title, int rating, int brewery, int style, int abv, int image, int review){
        ExampleFragment newFragment = new ExampleFragment();
        Bundle args = new Bundle();
        args.putInt(TITLE, title);
        args.putInt(RATING, rating);
        args.putInt(BREWERY, brewery);
        args.putInt(STYLE, style);
        args.putInt(ABV, abv);
        args.putInt(IMAGE, image);
        args.putInt(REVIEW, review);
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = (ViewGroup) inflater.inflate(R.layout.example_fragment, container, false);
        return rootView;
    }

    /*
     * As the fragment becomes visible for the user, the onStart method is called
     * loading all the predetermined resources to the View.
     */

    @Override public void onStart(){
        super.onStart();
        Bundle args = getArguments();
        int title = args.getInt(TITLE);
        int rating = args.getInt(RATING);
        int brewery = args.getInt(BREWERY);
        int style = args.getInt(STYLE);
        int abv = args.getInt(ABV);
        int image = args.getInt(IMAGE);
        int review = args.getInt(REVIEW);


        ((TextView) rootView.findViewById(R.id.title)).setText(title);
        ((TextView) rootView.findViewById(R.id.rating)).setText(rating);
        ((TextView) rootView.findViewById(R.id.brewery)).setText(brewery);
        ((TextView) rootView.findViewById(R.id.style)).setText(style);
        ((TextView) rootView.findViewById(R.id.abv)).setText(abv);
        ((ImageView) rootView.findViewById(R.id.image)).setBackgroundResource(image);
        ((TextView) rootView.findViewById(R.id.review)).setText(review);
    }

}
