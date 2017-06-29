/*
 * Created by Roshan Mahes on 12-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

/**
 * Shows some nice splash screen (on startup).
 */

public class LauncherActivity extends BaseActivity {

    int splashTimeOut = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        showSplashScreen();
    }

    private void showSplashScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, splashTimeOut);
    }
}
