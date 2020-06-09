package bsl.co.ke.fundsmanagementapi.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import bsl.co.ke.fundsmanagementapi.R;
import bsl.co.ke.fundsmanagementapi.ui.activities.LoginActivity;
import bsl.co.ke.fundsmanagementapi.ui.views.activities.RegisterActivity;


public class SplashScreenActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    /**
     * Called when the activity is first created.
     */
    Thread splashTread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

       /* YoYo.with(Techniques.Bounce)
                .duration(3000)
                .playOn(findViewById(R.id.logo));*/
       //new FetchStats().execute();
         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private class FetchStats extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    // Start your app main activity
                    startActivity(new Intent(SplashScreenActivity.this, ActivityFundsDasboard.class));
                    // close this activity
                    finish();
                }
            });
        }
    }
}
