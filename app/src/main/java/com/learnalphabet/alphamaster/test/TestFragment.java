package com.learnalphabet.alphamaster.test;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.learnalphabet.alphamaster.R;

/**
 * Created by amitn on 11-10-2015.
 */
public class TestFragment extends Fragment {
    TextToSpeech ttobj;
    Context mContext;
    TextView level1score;
    TextView level2score;

    public TestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getBaseContext();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.testfragment, container, false);


        Button level1 = (Button)rootView.findViewById(R.id.level1);
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Level1Activity.class);
                getActivity().startActivity(intent);

            }
        });

        Button level2 = (Button)rootView.findViewById(R.id.level2);
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Level2Activity.class);
                getActivity().startActivity(intent);

            }
        });

        level1score = (TextView)rootView.findViewById(R.id.level1score);
        level2score = (TextView)rootView.findViewById(R.id.level2score);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("AlphaMasterLevel1", Context.MODE_PRIVATE);

        int l1score = sharedpreferences.getInt("LEVEL1SCORE", 0);
        int l2score = sharedpreferences.getInt("LEVEL2SCORE",0);

        if(l1score==0){
            level1score.setText("None");
        }
        else{
            level1score.setText(String.valueOf(l1score));
        }

        if(l2score==0){
            level2score.setText("None");
        }
        else{
            level2score.setText(String.valueOf(l2score));
        }
    }

    @Override
    public void onDestroyView() {

        // Don't forget to shutdown!
        if (ttobj != null)
        {
            ttobj.stop();
            ttobj.shutdown();
        }
        super.onDestroyView();
    }
}
