package com.learnalphabet.alphamaster.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnalphabet.alphamaster.GlobalState;
import com.learnalphabet.alphamaster.R;

public class Level2Activity extends Activity implements View.OnClickListener{

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
    private  int score=0;

    private SharedPreferences sharedpreferences;
    ImageView imageView;
    ImageButton optionImage1;
    ImageButton optionImage2;
    ImageButton optionImage3;
    ImageButton optionImage4;
    TextView timertext;
    CountDownTimer timer;
    long remTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);


        View extraview = (View) findViewById(R.id.extraview);
        extraview.setVisibility(View.GONE);
        imageView =  (ImageView)findViewById(R.id.quesImage);
        optionImage1 = (ImageButton)findViewById(R.id.optnImage1);
        optionImage2 = (ImageButton)findViewById(R.id.optnImage2);
        optionImage3 = (ImageButton)findViewById(R.id.optnImage3);
        optionImage4 = (ImageButton)findViewById(R.id.optnImage4);

        optionImage1.setOnClickListener(this);
        optionImage2.setOnClickListener(this);
        optionImage3.setOnClickListener(this);
        optionImage4.setOnClickListener(this);

        timertext = (TextView) findViewById(R.id.timertext);

        remTime = 30000;

        Button gotowrite = (Button) findViewById(R.id.finish_test);
        gotowrite.setVisibility(View.GONE);

        Button restart = (Button) findViewById(R.id.restart_test);
        restart.setOnClickListener(this);

        Button tips = (Button) findViewById(R.id.score_test);
        tips.setOnClickListener(this);

        Button pause = (Button) findViewById(R.id.pause_test);
        pause.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(timer!=null){
            timer.cancel();
            timer = null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(prevNum!=-1 && remTime==30000){

            //Log.d("MVERMMA", "prev -1");
            generateRandomNum();

            while(prevNum == opt1){
                generateRandomNum();
            }

            generateOptRandomNum();
        }
        else if(prevNum ==-1){
            //Log.d("MVERMMA", "prev not -1");
            generateRandomNum();
            prevNum = opt1;

            generateOptRandomNum();
        }

        int qImg = ((int) (Math.random()*(4 - 1))) + 1;
        ////Log.d("MVERMA"," resrce qimg="+qImg);
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
        ////Log.d("MVERMA"," resrce setimg="+setImgNum);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
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

        timer = new CountDownTimer(remTime, 1000) {

            public void onTick(long millisUntilFinished) {
                timertext.setText("Seconds remaining: " + millisUntilFinished / 1000);
                remTime = millisUntilFinished;
            }

            public void onFinish() {
                timertext.setText("Time Up!");
                saveLevel1Score();
                showfinishdialog();
            }
        }.start();

        //Math.toRadians()
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("score", score);
        outState.putLong("timer",remTime);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        score = savedInstanceState.getInt("score");
        remTime = savedInstanceState.getLong("timer");
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

    int temp=0;
        switch (v.getId()){

            case R.id.optnImage1:
                if(opt1 == setImgNum){
                    moveToNext();
                    score= score+3;
                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        optionImage1.setBackground(getResources().getDrawable(R.drawable.wrong));
                    } else
                        optionImage1.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));

                    score--;
                }
                break;
            case R.id.optnImage2:
                if(opt2 == setImgNum){
                    moveToNext();
                    score= score+3;
                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        optionImage2.setBackground(getResources().getDrawable(R.drawable.wrong));
                    } else
                        optionImage2.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
                    score--;
                }
                break;
            case R.id.optnImage3:
                if(opt3 == setImgNum){
                    moveToNext();
                    score= score+3;
                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        optionImage3.setBackground(getResources().getDrawable(R.drawable.wrong));
                    } else {
                        optionImage3.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
                    }
                    score--;
                }
                break;
            case R.id.optnImage4:
                if(opt4 == setImgNum){
                    moveToNext();
                    score= score+3;
                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        optionImage4.setBackground(getResources().getDrawable(R.drawable.wrong));
                    } else
                        optionImage4.setBackgroundDrawable(getResources().getDrawable(R.drawable.wrong));
                    score--;
                }
                break;
            case R.id.restart_test:


                score=0;
                moveToNext();
                remTime = 30000;
                timer = new CountDownTimer(remTime, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timertext.setText("Seconds remaining: " + millisUntilFinished / 1000);
                        remTime = millisUntilFinished;
                    }

                    public void onFinish() {
                        timertext.setText("Time Up!");
                        saveLevel1Score();
                        showfinishdialog();
                    }
                }.start();
                break;

            case R.id.score_test:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                temp =0;
                if(score>=0)
                    temp = score;
                // set title
                alertDialogBuilder.setTitle("SCORE");
                alertDialogBuilder.setIcon(R.drawable.help_icon).setMessage("SCORE:"+temp)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something else
                            }
                        }).create();

                alertDialogBuilder.show();
                break;

            case R.id.pause_test:

                final long pauseTime = remTime;
                timer.cancel();
                timer = null;

                temp =0;
                if(score>=0)
                    temp = score;

                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);

                // set title
                alertDialogBuilder1.setCancelable(false);
                alertDialogBuilder1.setTitle("PAUSE");
                alertDialogBuilder1.setIcon(R.drawable.pause_icon).setMessage("SCORE:"+temp+"\nTest is paused. Click resume to resume!")
                        .setPositiveButton("RESUME", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something else
                                remTime = pauseTime;
                                timer = new CountDownTimer(remTime, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                        timertext.setText("Seconds remaining: " + millisUntilFinished / 1000);
                                        remTime = millisUntilFinished;
                                    }

                                    public void onFinish() {
                                        timertext.setText("Time Up!");
                                        saveLevel1Score();
                                        showfinishdialog();
                                    }
                                }.start();
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.setBackground(getResources().getDrawable(GlobalState.letter_image[setImgNum]));

            optionImage1.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt1]));
            optionImage2.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt2]));
            optionImage3.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt3]));
            optionImage4.setBackground(getResources().getDrawable(GlobalState.letter_only_image[opt4]));
        }else{
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
        int l1score = sharedpreferences.getInt("LEVEL2SCORE",0);
        if (l1score <score){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("LEVEL2SCORE", score);
            editor.commit();
        }

    }

    private void showfinishdialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("SCORE");
        alertDialogBuilder.setIcon(R.drawable.test_icon).setMessage("SCORE:"+score).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                }).create();

        alertDialogBuilder.show();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        final long pauseTime = remTime;
        timer.cancel();
        timer = null;

        int temp =0;
        if(score>=0)
            temp = score;

            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
            alertDialogBuilder1.setTitle("FINISH");
            alertDialogBuilder1.setIcon(R.drawable.finish_icon).setMessage("SCORE:" + temp + "\n" + "Do you want to quit? \n"+"Score will not be saved if you click on YES")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else

                        finish();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Do something else
                    //saveLevel1Score();
                    //finish();
                    remTime = pauseTime;
                    timer = new CountDownTimer(remTime, 1000) {

                        public void onTick(long millisUntilFinished) {
                            timertext.setText("Seconds remaining: " + millisUntilFinished / 1000);
                            remTime = millisUntilFinished;
                        }

                        public void onFinish() {
                            timertext.setText("Time Up!");
                            saveLevel1Score();
                            showfinishdialog();
                        }
                    }.start();

            }
        }).create();

        alertDialogBuilder1.show();

        }
}
