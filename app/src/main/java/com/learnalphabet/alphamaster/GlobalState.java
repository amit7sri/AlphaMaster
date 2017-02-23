package com.learnalphabet.alphamaster;

import android.app.Application;

/**
 * Created by amitn on 11-10-2015.
 */
public class GlobalState extends Application {

//    TextToSpeech ttobj;
//    Context mContext;

//    public Context getAppContext(){
//        return getApplicationContext();
//    }
//
//    void setTtobj(){
//        if(ttobj != null)
//        ttobj = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status != TextToSpeech.ERROR) {
//                    ttobj.setLanguage(Locale.UK);
//                }
//            }
//        });
//    }
//
//    TextToSpeech getTtobj(){
//        return ttobj;
//    }



    final public static int[] letter_image = { R.drawable.a,
            R.drawable.b,R.drawable.c,
            R.drawable.d,R.drawable.e,
            R.drawable.f,R.drawable.g,
            R.drawable.h,R.drawable.i,
            R.drawable.j,R.drawable.k,
            R.drawable.l,R.drawable.m,
            R.drawable.n,R.drawable.o,
            R.drawable.p,R.drawable.q,
            R.drawable.r,R.drawable.s,
            R.drawable.t,R.drawable.u,
            R.drawable.v,R.drawable.w,
            R.drawable.x,R.drawable.y,
            R.drawable.z};

//    final public static int[] letter_main = { R.drawable.a_main,
//            R.drawable.b_main,R.drawable.c_main,
//            R.drawable.d_main,R.drawable.e_main,
//            R.drawable.f_main,R.drawable.g_main,
//            R.drawable.h_main,R.drawable.i_main,
//            R.drawable.j_main,R.drawable.k_main,
//            R.drawable.l_main,R.drawable.m_main,
//            R.drawable.n_main,R.drawable.o_main,
//            R.drawable.p_main,R.drawable.q_main,
//            R.drawable.r_main,R.drawable.s_main,
//            R.drawable.t_main,R.drawable.u_main,
//            R.drawable.v_main,R.drawable.w_main,
//            R.drawable.x_main,R.drawable.y_main,
//            R.drawable.z_main};

    final public static int[] letter_only_image = { R.drawable.apple,
            R.drawable.ball,R.drawable.cat,
            R.drawable.doll,R.drawable.egg,
            R.drawable.fan,R.drawable.grapes,
            R.drawable.hand,R.drawable.icecream,
            R.drawable.joker,R.drawable.key,
            R.drawable.lion,R.drawable.mouse,
            R.drawable.nest,R.drawable.orange,
            R.drawable.pen,R.drawable.queen,
            R.drawable.rabbit,R.drawable.ship,
            R.drawable.train,R.drawable.unicorn,
            R.drawable.van,R.drawable.window,
            R.drawable.xylophone,R.drawable.yak,
            R.drawable.zip};

    public static final String[] toSpeakletterword = {"", "A for Apple", "B for Ball", "C for Cat",
            "D for Doll", "E for Egg", "F for Fan", "G for Grapes", "H for Hand",
            "I for ice cream", "J for joker", "K for key", "L for lion",
            "M for Mouse", "N for Nest", "O for Orange", "P for Pen", "Q for Queen",
            "R for Rabbit", "S for ship", "T for Train", "U for Unicorn",
            "V for van", "W for window", "X for xylophone", "Y for yak", "Z for zip"};

    final  public static String[] toSpeakletter = { "A", "B", "C", "D", "E", "F", "G", "H","I", "J", "K", "L","M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","" };

    public static final String[] imagename = {"ANT", "BIRD","COW", "DOG","ELEPHANT","FISH","GOAT","HEN" ,"INK"
    ,"JUG", "KITE","LAMP", "MANGO", "NOSE", "OWL", "PIZZA", "QUIL","ROCKET", "STAR", "TREE", "UMBRELLA", "VIOLIN" , "WATCH",
    "XMAS TREE" , "YATCH", "ZEBRA"};

    public static final String[] toSpeakimage = { "Apple", "Ball", "Cat",
            "Doll", "Egg", "Fan", "Grapes", "Hand",
            "ice cream", "joker", "key", "lion",
            "Mouse", "Nest", "Orange", "Pen", "Queen",
            "Rabbit", "ship", "Train", "Unicorn",
            "van", "window", "xylophone", "yak", "zip"};

    public static int currentpage;

//    public static boolean currenthomefrag;

}
