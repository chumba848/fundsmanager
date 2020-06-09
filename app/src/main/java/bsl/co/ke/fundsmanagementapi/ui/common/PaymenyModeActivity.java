package bsl.co.ke.fundsmanagementapi.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import bsl.co.ke.fundsmanagementapi.R;

public class PaymenyModeActivity extends Activity {

    TextView myaccount;
    private static int SPLASH_TIME_OUT = 3000;

    /**
     * Called when the activity is first created.
     */
    Thread splashTread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_mode);


        myaccount = (TextView) findViewById(R.id.myaccount);
        myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymenyModeActivity.this, DashboardActivity.class));

            }
        });
       /* YoYo.with(Techniques.Bounce)
                .duration(3000)
                .playOn(findViewById(R.id.logo));*/

        //new FetchStats().execute();
      /*  new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {


                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(new Intent(PaymenyModeActivity.this, PictureCodeActivity.class));

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
*/

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
                    startActivity(new Intent(PaymenyModeActivity.this, DashboardActivity.class));

                    // close this activity
                    finish();

                }
            });
        }
    }


}
