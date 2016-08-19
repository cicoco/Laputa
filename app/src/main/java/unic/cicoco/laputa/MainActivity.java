package unic.cicoco.laputa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import unic.cicoco.laputa.logger.UnicLog;

public class MainActivity extends Activity {


    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UnicLog.i(TAG, "onCreate...");
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_crash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new NullPointerException();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        UnicLog.i(TAG, "onStart...");
    }


    @Override
    protected void onResume() {
        super.onResume();
        UnicLog.i(TAG, "onResume...");

    }


    @Override
    protected void onPause() {
        super.onPause();
        UnicLog.i(TAG, "onPause...");

    }


    @Override
    protected void onStop() {
        super.onStop();
        UnicLog.i(TAG, "onStop...");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UnicLog.i(TAG, "onDestroy...");

    }
}


