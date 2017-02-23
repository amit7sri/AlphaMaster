

package com.learnalphabet.alphamaster.learn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.learnalphabet.alphamaster.GlobalState;
import com.learnalphabet.alphamaster.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LearnActivity extends Activity {

    String TAG = getClass().toString();

    private BookView mBookView;
    public TextToSpeech ttobj;

    AlertDialog dialogLetter;



    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.learn_layout);


        int index = 0;
        if (getLastNonConfigurationInstance() != null) {
            index = (Integer) getLastNonConfigurationInstance();
        }

        mBookView = (BookView) findViewById(R.id.curl);
        mBookView.setPageProvider(new PageProvider());
        mBookView.setSizeChangedObserver(new SizeChangedObserver());
        mBookView.setCurrentIndex(index);
        mBookView.setBackgroundColor(0xFF202830);

        ttobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    ttobj.setLanguage(Locale.UK);
                    ttobj.setSpeechRate((float) 0.5);
                }
            }
        });

        Button cover = (Button) findViewById(R.id.goto_page);
        cover.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                showAlertDialog();
//                if (mCurlView.getCurrentIndex() != 0)
//                    mCurlView.setCurrentIndex(0);
            }
        });

//        Button home = (Button) findViewById(R.id.home);
//        home.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(CurlActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });


        Button repeat = (Button) findViewById(R.id.repeat);
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBookView.getCurrentIndex() > 0 && mBookView.getCurrentIndex() <= 26)
                    ttobj.speak(GlobalState.toSpeakletterword[mBookView.getCurrentIndex()], TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        Button help = (Button) findViewById(R.id.help);
        help.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LearnActivity.this);

                alertDialogBuilder.setTitle(R.string.help);
                alertDialogBuilder.setIcon(R.drawable.help_icon).setMessage(R.string.help_learn)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something else

                            }
                        }).create();

                alertDialogBuilder.show();
            }
        });


        // This is something somewhat experimental. Before uncommenting next
        // line, please see method comments in CurlView.
        // mCurlView.setEnableTouchPressure(true);
    }


    private void showAlertDialog() {
        // Prepare grid view
        GridView gridView = new GridView(this);

        List<Character>  mList = new ArrayList<Character>();
        char ch;
        for( ch = 'A' ; ch <= 'Z' ; ch++ ){
            mList.add(ch);
        }

        gridView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, mList));
        gridView.setNumColumns(6);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // do something here
                //Log.d(getClass().getName(),"position is="+position);
                mBookView.setCurrentIndex(position+1);
                dialogLetter.dismiss();

            }
        });

        // Set grid view to alertDialog
        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialogLetter = new AlertDialog.Builder(this).create();
        dialogLetter.setIcon(R.drawable.goto_icon );
        dialogLetter.setCancelable(true);
        dialogLetter.setView(gridView);
        dialogLetter.setTitle("Goto");

        dialogLetter.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBookView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBookView.onResume();
    }


    @Override
    public Object onRetainNonConfigurationInstance() {
        return mBookView.getCurrentIndex();
    }

    /**
     * Bitmap provider.
     */
    private class PageProvider implements BookView.PageProvider {

        // Bitmap resources.
        private int[] mBitmapIds = {R.drawable.az, R.drawable.a,
                R.drawable.apple, R.drawable.b, R.drawable.ball, R.drawable.c,
                R.drawable.cat, R.drawable.d, R.drawable.doll, R.drawable.e,
                R.drawable.egg, R.drawable.f, R.drawable.fan, R.drawable.g,
                R.drawable.grapes, R.drawable.h, R.drawable.hand, R.drawable.i,
                R.drawable.icecream, R.drawable.j, R.drawable.joker, R.drawable.k,
                R.drawable.key, R.drawable.l, R.drawable.lion, R.drawable.m,
                R.drawable.mouse, R.drawable.n, R.drawable.nest, R.drawable.o,
                R.drawable.orange, R.drawable.p, R.drawable.pen, R.drawable.q,
                R.drawable.queen, R.drawable.r, R.drawable.rabbit, R.drawable.s,
                R.drawable.ship, R.drawable.t, R.drawable.train, R.drawable.u,
                R.drawable.unicorn, R.drawable.v, R.drawable.van, R.drawable.w,
                R.drawable.window, R.drawable.x, R.drawable.xylophone, R.drawable.y,
                R.drawable.yak, R.drawable.z, R.drawable.zip, R.drawable.az};
        //private String[] toSpeakletterword = {"","","A for Apple","", "B for Ball","", "C for Cat",""};

        @Override
        public int getPageCount() {
            return 27;
        }

        private Bitmap loadBitmap(int width, int height, int index) {
            Bitmap b = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            b.eraseColor(0xFFFFFFFF);
            Canvas c = new Canvas(b);
            Drawable d = getResources().getDrawable(mBitmapIds[index]);

            int margin = 7;
            int border = 3;
            Rect r = new Rect(margin, margin, width - margin, height - margin);

            int imageWidth = r.width() - (border * 2);
            int imageHeight = imageWidth * d.getIntrinsicHeight()
                    / d.getIntrinsicWidth();
            if (imageHeight > r.height() - (border * 2)) {
                imageHeight = r.height() - (border * 2);
                imageWidth = imageHeight * d.getIntrinsicWidth()
                        / d.getIntrinsicHeight();
            }

            r.left += ((r.width() - imageWidth) / 2) - border;
            r.right = r.left + imageWidth + border + border;
            r.top += ((r.height() - imageHeight) / 2) - border;
            r.bottom = r.top + imageHeight + border + border;

            Paint p = new Paint();
            p.setColor(0xFFC0C0C0);
            c.drawRect(r, p);
            r.left += border;
            r.right -= border;
            r.top += border;
            r.bottom -= border;

            d.setBounds(r);
            d.draw(c);

            return b;
        }

        int last_index = 0;

        @Override
        public void updatePage(BookPage page, int width, int height, int index) {

            //Log.d(getClass().toString(), "updatePage index is   " + mCurlView.getCurrentIndex());
            //Log.d(getClass().toString(), "index is   " +index);
            //new try
            if (index == 0) {
                Bitmap front = loadBitmap(width, height, 0);
                page.setTexture(front, BookPage.SIDE_FRONT, 0);
                Bitmap bmp = loadBitmap(width, height, 1);
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);
                Bitmap back = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);

                page.setTexture(back, BookPage.SIDE_BACK, 1);
                page.setColor(Color.rgb(180, 180, 180), BookPage.SIDE_BACK);
                last_index = index;
            } else {
                Bitmap front = loadBitmap(width, height, 2 * index);
                page.setTexture(front, BookPage.SIDE_FRONT, 2 * index);
                //Log.d("AMIT", "CASE1 index last_index " + index + " " + last_index);
                if (last_index <= index)
                    ttobj.speak(GlobalState.toSpeakletterword[index], TextToSpeech.QUEUE_FLUSH, null);
                else {
                    ttobj.speak(GlobalState.toSpeakletterword[index + 1], TextToSpeech.QUEUE_FLUSH, null);
                }
                Bitmap bmp = loadBitmap(width, height, 2 * index + 1);
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);
                Bitmap back = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
                page.setTexture(back, BookPage.SIDE_BACK, 2 * index + 1);
                //page.setColor(Color.argb(127, 170, 130, 255),
                //	CurlPage.SIDE_FRONT);
                //page.setColor(Color.rgb(255, 190, 150), CurlPage.SIDE_BACK);
                last_index = index;
                //break;
            }
        }
    }

    /**
     * CurlView size changed observer.
     */
    private class SizeChangedObserver implements BookView.SizeChangedObserver {
        @Override
        public void onSizeChanged(int w, int h) {
            if (w > h) {
                mBookView.setViewMode(BookView.SHOW_TWO_PAGES);
                mBookView.setMargins(.1f, .05f, .1f, .05f);
            } else {
                mBookView.setViewMode(BookView.SHOW_ONE_PAGE);
                mBookView.setMargins(.1f, .1f, .1f, .1f);
            }
        }
    }

    @Override
    protected void onDestroy() {
        // Don't forget to shutdown!
        if (ttobj != null)
        {
            ttobj.stop();
            ttobj.shutdown();
        }
        super.onDestroy();
    }

    //    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
//        Intent intent = new Intent(CurlActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
}