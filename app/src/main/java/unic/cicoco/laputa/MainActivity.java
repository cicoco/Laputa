package unic.cicoco.laputa;

import android.app.Activity;
import android.os.Bundle;

import unic.cicoco.laputa.logger.UnicLog;


public class MainActivity extends Activity {


    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UnicLog.i(TAG , "onCreate...");
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


