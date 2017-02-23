package com.learnalphabet.alphamaster.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnalphabet.alphamaster.GlobalState;
import com.learnalphabet.alphamaster.R;

import java.util.Locale;


public class Level1Activity extends Activity implements View.OnClickListener{

    private  int score=0;
    private int prevNum = -1;
    private int setImgNum = -1;
    private int opt1 = -1;
    private int opt2 = -1;
    private int opt3 = -1;
    private int opt4 = -1;

    private int o1 = -1;
    private int o2 = -1;
    private int o3 = -1;
    private int o4 = -1;

    ImageView imageView;
    ImageButton optionImage1;
    ImageButton optionImage2;
    ImageButton optionImage3;
    ImageButton optionImage4;
    TextView timer;

    TextToSpeech ttobj;

    AlertDialog dialogLetter;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        timer = (TextView) findViewById(R.id.timertext);
        timer.setVisibility(View.GONE);
        imageView =  (ImageView)findViewById(R.id.quesImage);
        optionImage1 = (ImageButton)findViewById(R.id.optnImage1);
        optionImage2 = (ImageButton)findViewById(R.id.optnImage2);
        optionImage3 = (ImageButton)findViewById(R.id.optnImage3);
        optionImage4 = (ImageButton)findViewById(R.id.optnImage4);



        optionImage1.setOnClickListener(this);
        optionImage2.setOnClickListener(this);
        optionImage3.setOnClickListener(this);
        optionImage4.setOnClickListener(this);



        Button finishtest = (Button) findViewById(R.id.finish_test);
        finishtest.setOnClickListener(this);

        Button restart = (Button) findViewById(R.id.restart_test);
        restart.setOnClickListener(this);

        Button score = (Button) findViewById(R.id.score_test);
        score.setOnClickListener(this);

        Button pause = (Button) findViewById(R.id.pause_test);
        pause.setVisibility(View.GONE);

        ttobj = new TextToSpeech(this,
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            ttobj.setLanguage(Locale.UK);
                            //ttobj.setSpeechRate((float) 0.5);
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(prevNum!=-1){

            generateRandomNum();

            while(prevNum == opt1){
                generateRandomNum();
            }

            generateOptRandomNum();
        }
        else{
            generateRandomNum();
            prevNum = opt1;

            generateOptRandomNum();
        }

        int qImg = ((int) (Math.random()*(4 - 1))) + 1;
        //Log.d("MVERMA"," resrce qimg="+qImg);
        if(qImg == 1){
            setImgNum = opt1;
        }
        else if(qImg == 2){
            setImgNum = opt2;
        }
        else if(qImg == 3){
            setImgNum = opt3;
        }
        else{
            setImgNum = opt4;
        }
        //Log.d("MVERMA"," resrce setimg="+setImgNum);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // only for gingerbread and newer versions
            imageView.setBackground(getResources().getDrawable(GlobalState.letter_image[setImgNum]));

            optionImage1.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt1]));
            optionImage2.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt2]));
            optionImage3.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt3]));
            optionImage4.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt4]));
        } else {
            imageView.setBackgroundDrawable(getResources().getDrawable(GlobalState.letter_image[setImgNum]));

            optionImage1.setBackgroundDrawable(getResources().getDrawable(GlobalState.letter_only_image[opt1]));
            optionImage2.setBackgroundDrawable(getResources().getDrawable(GlobalState.letter_only_image[opt2]));
            optionImage3.setBackgroundDrawable(getResources().getDrawable(GlobalState.letter_only_image[opt3]));
            optionImage4.setBackgroundDrawable(getResources().getDrawable(GlobalState.letter_only_image[opt4]));
        }


        //Math.toRadians()
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("score", score);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        score = savedInstanceState.getInt("score");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onClick(View v) {

        int temp =0;

        switch (v.getId()){

            case R.id.optnImage1:
                if(opt1 == setImgNum){

                    ttobj.speak(GlobalState.toSpeakimage[opt1], TextToSpeech.QUEUE_FLUSH, null);
                    moveToNext();
                    score= score+3;
   //                 ttobj.speak("Good ", TextToSpeech.QUEUE_FLUSH, null);
                }
                else{
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        optionImage1.setBackground(getResources().getDrawable(R.drawable.wrong));
                    }else
                        optionImage1.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
                    //ttobj.speak("Eehhhh ", TextToSpeech.QUEUE_FLUSH, null);
                    score--;
                }
                break;
            case R.id.optnImage2:
                if(opt2 == setImgNum){

                    ttobj.speak(GlobalState.toSpeakimage[opt2], TextToSpeech.QUEUE_FLUSH, null);
                    moveToNext();
                    score= score+3;
                }
                else{
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        optionImage2.setBackground(getResources().getDrawable(R.drawable.wrong));
                    } else
                        optionImage2.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
                    score--;
                }
                break;
            case R.id.optnImage3:
                if(opt3 == setImgNum){

                    ttobj.speak(GlobalState.toSpeakimage[opt3], TextToSpeech.QUEUE_FLUSH, null);
                    moveToNext();
                    score= score+3;
                }
                else{
                    if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                     optionImage3.setBackground(getResources().getDrawable(R.drawable.wrong));
                    else
                        optionImage3.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
                    score--;
                }
                break;
            case R.id.optnImage4:
                if(opt4 == setImgNum){

                    ttobj.speak(GlobalState.toSpeakimage[opt4], TextToSpeech.QUEUE_FLUSH, null);
                    moveToNext();
                    score= score+3;
                }
                else{
                    if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        optionImage4.setBackground(getResources().getDrawable(R.drawable.wrong));
                    else
                        optionImage4.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
                    score--;
                }
                break;
            case R.id.restart_test:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                // set title
                alertDialogBuilder.setTitle("RESTART");
                alertDialogBuilder.setIcon(R.drawable.restart_icon).setMessage("SCORE: "+score + "\n" + "Do you want to restart?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something else
                                score=0;
                                moveToNext();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                        .create();

                alertDialogBuilder.show();
                //

                break;

            case R.id.score_test:
                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(this);

                temp =0;

                if(score>=0)
                    temp = score;
                // set title
                alertDialogBuilder2.setTitle("SCORE");
                alertDialogBuilder2.setIcon(R.drawable.score_icon).setMessage("SCORE: "+temp)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something else
                            }
                        }).create();

                alertDialogBuilder2.show();
                break;

            case R.id.finish_test:

                temp =0;

                if(score>=0)
                    temp = score;

                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);

                // set title
                alertDialogBuilder1.setTitle("FINISH");
                alertDialogBuilder1.setIcon(R.drawable.finish_icon).setMessage("SCORE:" + temp + "\n" + "Do you want to quit?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something else
                                saveLevel1Score();
                                finish();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        //saveLevel1Score();
                        //finish();

                    }
                }).create();

                alertDialogBuilder1.show();
                break;

            default:
                break;

        }

    }

    private void moveToNext(){

        generateRandomNum();

        while (prevNum == opt1) {
            generateRandomNum();
        }
        prevNum = opt1;
        generateOptRandomNum();

        int qImg = ((int) (Math.random()*(4 - 1))) + 1;

        if(qImg == 1){
            setImgNum = opt1;
        }
        else if(qImg == 2){
            setImgNum = opt2;
        }
        else if(qImg == 3){
            setImgNum = opt3;
        }
        else{
            setImgNum = opt4;
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.setBackground(getResources().getDrawable(GlobalState.letter_image[setImgNum]));

            optionImage1.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt1]));
            optionImage2.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt2]));
            optionImage3.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt3]));
            optionImage4.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt4]));
        } else {
            imageView.setBackgroundDrawable(getResources().getDrawable(GlobalState.letter_image[setImgNum]));

            optionImage1.setBackgroundDrawable(getResources().getDrawable(GlobalState.letter_only_image[opt1]));
            optionImage2.setBackgroundDrawable(getResources().getDrawable(GlobalState.letter_only_image[opt2]));
            optionImage3.setBackgroundDrawable(getResources().getDrawable(GlobalState.letter_only_image[opt3]));
            optionImage4.setBackgroundDrawable(getResources().getDrawable(GlobalState.letter_only_image[opt4]));
        }
    }

    public void generateRandomNum(){

        opt1 =  ((int) (Math.random()*(25 - 0))) + 0;

        opt2 =  ((int) (Math.random()*(25 - 0))) + 0;
        opt3 =  ((int) (Math.random()*(25 - 0))) + 0;
        opt4 =  ((int) (Math.random()*(25 - 0))) + 0;
    }

    public void generateOptRandomNum(){

        opt2 =  ((int) (Math.random()*(25 - 0))) + 0;

        while(opt1==opt2){
            opt2 =  ((int) (Math.random()*(25 - 0))) + 0;
        }

        opt3 =  ((int) (Math.random()*(25 - 0))) + 0;

        while(opt1==opt3 || opt2==opt3){
            opt3 =  ((int) (Math.random()*(25 - 0))) + 0;
        }
        opt4 =  ((int) (Math.random()*(25 - 0))) + 0;

        while(opt1==opt4 || opt2==opt4 || opt3==opt4){
            opt4 =  ((int) (Math.random()*(25 - 0))) + 0;
        }
    }

    private void saveLevel1Score(){

        sharedpreferences = getSharedPreferences("AlphaMasterLevel1", Context.MODE_PRIVATE);
        int l1score = sharedpreferences.getInt("LEVEL1SCORE",0);
        if (l1score <score){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("LEVEL1SCORE", score);
            editor.commit();
        }

    }

    @Override
    protected void onDestroy() {
        if (ttobj != null)
        {
            ttobj.stop();
            ttobj.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        int temp =0;
        if(score>=0)
            temp = score;

        AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
        alertDialogBuilder1.setTitle("FINISH");
        alertDialogBuilder1.setIcon(R.drawable.finish_icon).setMessage("SCORE:" + temp + "\n" + "Do you want to quit?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        saveLevel1Score();
                        finish();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do something else
                //saveLevel1Score();
                //finish();
            }
        }).create();

        alertDialogBuilder1.show();

    }
}
