package com.learnalphabet.alphamaster;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnalphabet.alphamaster.learn.LearnActivity;
import com.learnalphabet.alphamaster.test.TestFragment;
import com.learnalphabet.alphamaster.write.WriteFragment;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by amitn on 11-10-2015.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    TextToSpeech ttobj;
    Context mContext;
    GridView gridview1;
    AlertDialog dialogLetter;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getBaseContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        gridview1 = (GridView) rootView.findViewById(R.id.gridview1);
        gridview1.setAdapter(new ImageAdapter(mContext));

        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.fly_in_from_center);
        gridview1.setAnimation(anim);
        anim.start();


        ttobj = new TextToSpeech(mContext,
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            ttobj.setLanguage(Locale.UK);
                            ttobj.setSpeechRate((float) 0.5);
                        }
                    }
                });



        gridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ttobj.speak(GlobalState.toSpeakletter[position], TextToSpeech.QUEUE_FLUSH, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.home_letter_popup, null);
                builder.setView(dialoglayout);

                ImageView letterImage = (ImageView) dialoglayout.findViewById(R.id.popup_image);
                //letterImage.setImageAlpha(1);
                letterImage.setImageResource(GlobalState.letter_image[position]);

                TextView letter = (TextView) dialoglayout.findViewById(R.id.letter_popup);
                letter.setText("  " + GlobalState.toSpeakletter[position]);

                TextView word = (TextView) dialoglayout.findViewById(R.id.word);
                word.setText(GlobalState.imagename[position] + "  ");


                dialogLetter = builder.create();
                dialogLetter.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialogLetter.show();

                final Timer timer2 = new Timer();
                timer2.schedule(new TimerTask() {
                    public void run() {
                        dialogLetter.dismiss();
                        timer2.cancel(); //this will cancel the timer of the system
                    }
                }, 1500); // the timer will count 5 seconds....
            }
        });



        Button learn = (Button) rootView.findViewById(R.id.learn_letter);
        learn.setOnClickListener(this);

        Button write = (Button) rootView.findViewById(R.id.write_letter);
        write.setOnClickListener(this);

        Button test = (Button) rootView.findViewById(R.id.test);
        test.setOnClickListener(this);

        Button tips = (Button) rootView.findViewById(R.id.help);
        tips.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment newFragment;
        FragmentTransaction transaction;
        FragmentManager fm = getActivity().getFragmentManager();

        switch (v.getId()) {
            case R.id.learn_letter:
               Intent intent = new Intent(getActivity(), LearnActivity.class);
                getActivity().startActivity(intent);
                //Log.d("Amit", "onClick learn");
                break;
            case R.id.write_letter:
                // Create new fragment and transaction
                newFragment = new WriteFragment();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                //Log.d("Amit", "onClick write");
                break;
            case R.id.test:
                newFragment = new TestFragment();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                //Log.d("Amit", "onClick test");
                break;
            case R.id.help:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                // set title
                alertDialogBuilder.setTitle(R.string.help);
                alertDialogBuilder.setIcon(R.drawable.help_icon).setMessage(R.string.help_main)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something else
                            }
                        }).create();

                alertDialogBuilder.show();
                //Log.d("Amit", "onClick tips");
                break;

            default:
                break;
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
