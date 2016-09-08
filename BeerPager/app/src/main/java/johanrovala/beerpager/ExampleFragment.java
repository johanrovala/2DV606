package johanrovala.beerpager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by johanrovala on 08/09/16.
 */
public class ExampleFragment extends Fragment {
    final static String HEADER_TEXT = "header_text", BODY_IMAGE = "body_image", DESCRIPTION = "description";
    private ViewGroup rootView;

    public static ExampleFragment create(String header, int imageID, String description){
        ExampleFragment newFragment = new ExampleFragment();
        Bundle args = new Bundle();
        args.putString(HEADER_TEXT, header);
        args.putInt(BODY_IMAGE, imageID);
        args.putString(DESCRIPTION, description);
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = (ViewGroup) inflater.inflate(R.layout.example_fragment, container, false);
        return rootView;
    }

    @Override public void onStart(){
        super.onStart();
        Bundle args = getArguments();
        String headerText = args.getString(HEADER_TEXT);
        int imageID = args.getInt(BODY_IMAGE);
        String description = args.getString(DESCRIPTION);


        ((TextView) rootView.findViewById(R.id.header_text)).setText(headerText);
        ((TextView) rootView.findViewById(R.id.second_text)).setText(description);
    }

}
