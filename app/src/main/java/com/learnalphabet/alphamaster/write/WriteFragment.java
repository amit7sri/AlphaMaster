package com.learnalphabet.alphamaster.write;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.learnalphabet.alphamaster.GlobalState;
import com.learnalphabet.alphamaster.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by amitn on 11-10-2015.
 */
public class WriteFragment extends Fragment implements GestureOverlayView.OnGesturePerformedListener, View.OnClickListener {
    TextToSpeech ttobj;
    Context mContext;
    ImageButton leftslide;
    ImageButton rightslide;
    private GestureLibrary gLibrary;
    private com.learnalphabet.alphamaster.write.CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;

    AlertDialog dialogLetter;

    public WriteFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getBaseContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.write_layout, container, false);

        leftslide = (ImageButton) rootView.findViewById(R.id.leftslide);
        leftslide.setOnClickListener(this);
        rightslide = (ImageButton) rootView.findViewById(R.id.rightslide);
        rightslide.setOnClickListener(this);

        gLibrary = GestureLibraries.fromRawResource(mContext, R.raw.gestures_imp_3_capital);
        if (!gLibrary.load()) {
            getActivity().finish();
        }

        ttobj = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    ttobj.setLanguage(Locale.UK);
                    ttobj.setSpeechRate((float) 0.5);
                }
            }
        });

        ttobj.speak(GlobalState.toSpeakletter[0], TextToSpeech.QUEUE_FLUSH, null);
        mCustomPagerAdapter = new com.learnalphabet.alphamaster.write.CustomPagerAdapter(mContext);

        mViewPager = (ViewPager) rootView.findViewById(R.id.writeletterpager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        GestureOverlayView gOverlay =
                (GestureOverlayView) rootView.findViewById(R.id.gOverlay);
        gOverlay.addOnGesturePerformedListener(this);


//        leftslide.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mViewPager.setCurrentItem(mViewPager.getCurrentItem() -1, true);
//            }
//        });
//
//        rightslide.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mViewPager.setCurrentItem(mViewPager.getCurrentItem() +1, true);
//            }
//        });

        Button gotowrite = (Button) rootView.findViewById(R.id.goto_page_write);
        gotowrite.setOnClickListener(this);

        Button repeat = (Button) rootView.findViewById(R.id.repeat_write);
        repeat.setOnClickListener(this);

        Button tips = (Button) rootView.findViewById(R.id.help_write);
        tips.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                GlobalState.currentpage = position;
                ttobj.speak(GlobalState.toSpeakletter[position], TextToSpeech.QUEUE_FLUSH, null);

                //Log.d("WriteActivity", "position is " + position);

            }
        });

        return rootView;
    }

    private void showAlertDialog() {
        // Prepare grid view
        GridView gridView = new GridView(getActivity());

        List<Character> mList = new ArrayList<Character>();
        char ch;
        for( ch = 'A' ; ch <= 'Z' ; ch++ ){
            mList.add(ch);
        }

        gridView.setAdapter(new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, mList));
        gridView.setNumColumns(6);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // do something here
                //mCurlView.setCurrentIndex(position+1);
                //Log.d(getClass().toString() , "  position is :  " +  position);

                mViewPager.setCurrentItem(position, true);
                dialogLetter.dismiss();

            }
        });

        // Set grid view to alertDialog
        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialogLetter = new AlertDialog.Builder(getActivity()).create();
        dialogLetter.setIcon(R.drawable.goto_icon );
        dialogLetter.setCancelable(true);
        dialogLetter.setView(gridView);
        dialogLetter.setTitle("Goto");

        dialogLetter.show();
    }

    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gLibrary.recognize(gesture);

        //Log.d(getClass().toString() + "  predictions is :  ", predictions.toString());

        boolean flag = true;
        for(int i=0 ; i<predictions.size();i++){
            //Log.d(getClass().toString() , "  name =   " +  predictions.get(i).name + " predictions is :  "+ predictions.get(i).score);
            //Log.d(getClass().toString(), " common   " + GlobalState.toSpeakletter[GlobalState.currentpage]);
            if(GlobalState.toSpeakletter[GlobalState.currentpage].equals(predictions.get(i).name) && predictions.get(i).score >2.0)
            {
                String action = predictions.get(i).name;
                //Log.d(getClass().toString() + "  action is :  ",action);
                ttobj.speak("Welldone", TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(mContext, action, Toast.LENGTH_SHORT).show();
                flag = false;
            }
            else if(flag != false)
                ttobj.speak("Try Again ", TextToSpeech.QUEUE_FLUSH, null);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.repeat_write:
                ttobj.speak(GlobalState.toSpeakletter[mViewPager.getCurrentItem()], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.goto_page_write:
                // Create new fragment and transaction
                showAlertDialog();
                //Log.d("Amit", "onClick write");
                break;

            case R.id.help_write:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                // set title
                alertDialogBuilder.setTitle(R.string.help);
                alertDialogBuilder.setIcon(R.drawable.help_icon).setMessage(R.string.help_main_write)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something else
                            }
                        }).create();

                alertDialogBuilder.show();
                //Log.d("Amit", "onClick tips");
                break;

            case R.id.rightslide:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() +1, true);
                break;
            case R.id.leftslide:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() -1, true);
                break;

            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        if (ttobj != null)
        {
            ttobj.stop();
            ttobj.shutdown();
        }
        super.onDestroyView();
    }
}
