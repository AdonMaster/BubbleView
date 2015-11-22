package com.github.bubbleview.sample.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.github.bubbleview.sample.R;
import com.github.library.bubbleview.BubbleTextVew;


/**
 * Created by lgp on 2015/4/25.
 */
public class MainActivity extends Activity{

    private BubbleTextVew ui_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ui_t = (BubbleTextVew) findViewById(R.id.textView);

        (new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids)
            {
                SystemClock.sleep(4000);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
            }

        }).execute();
    }

    private int colorCount = 0;

    public void onButtonClickChibungo(View v)
    {
        int[] colors = new int[]{
                R.color.material_deep_teal_200,
                R.color.material_blue_grey_900,
                R.color.background_floating_material_light,
                R.color.abc_input_method_navigation_guard
        };

        int pos = colorCount++ % colors.length;

        ui_t.setBubbleColor(getResources().getColor(colors[pos]));
    }
}
