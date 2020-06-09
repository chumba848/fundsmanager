package bsl.co.ke.fundsmanagementapi.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import bsl.co.ke.fundsmanagementapi.FMAPI;
import bsl.co.ke.fundsmanagementapi.R;
import bsl.co.ke.fundsmanagementapi.ui.common.ActivityFundsDasboard;
import bsl.co.ke.fundsmanagementapi.ui.database.DataBaseAdapter;
import bsl.co.ke.fundsmanagementapi.ui.database.DatabaseHelper;
import bsl.co.ke.fundsmanagementapi.ui.database.InputValidation;
import bsl.co.ke.fundsmanagementapi.ui.views.activities.RegisterActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private NestedScrollView nestedScrollView;


    private AppCompatButton appCompatButtonLogin;

    EditText textInputEditTextIdno;
    EditText textInputEditTextpassword;
    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    String password;
    @BindView(R.id.textInputEditTextTraderNumber)
    EditText textInputEditTextTraderNumber;
    @BindView(R.id.textInputEditTextPassword)
    EditText textInputEditTextPassword;

    int PERMISSION_ALL = 1;
    public ProgressDialog mprogress;
    private static int SPLASH_TIME_OUT = 3000;


    //static LoginRequest login;

    static String userName;

    FMAPI app;
    DataBaseAdapter db;
    private Intent serviceIntent;
    Snackbar snackbar;
    private ScrollView scrollView;


    String tradernumber;

    private static final int DATA_SETUP_SERVICE_ID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //  app = (FMAPI) getApplication();
        db = new DataBaseAdapter(getApplicationContext());
        inputValidation = new InputValidation(this);


        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);


   /*    textInputEditTextIdno = (EditText) findViewById(R.id.textInputEditTextIdNo);
       textInputEditTextpassword = (EditText) findViewById(R.id.textInputEditTextPassword);*/

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);


        appCompatButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyFromSQLite();

              /*  userName = textInputEditTextTraderNumber.getText().toString().trim();
                password = textInputEditTextPassword.getText().toString().trim();
                if (validateUsername(textInputEditTextTraderNumber.getText().toString()) && validatePassword(textInputEditTextPassword.getText().toString())) { //Username and Password Validation
                 //   verifyFromSQLite();

                    new FetchLogin().execute();
                }*/
            }
        });
        textViewLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyFromSQLite();

                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
    }

    private void verifyFromSQLite() {
        tradernumber = Objects.requireNonNull(textInputEditTextTraderNumber.getText()).toString().trim();
        password = Objects.requireNonNull(textInputEditTextPassword.getText()).toString().trim();


        if (TextUtils.isEmpty(tradernumber)) {
            textInputEditTextTraderNumber.setError("Field Required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            textInputEditTextPassword.setError("Field Required");
            return;
        }


        if (db.checkUser(textInputEditTextTraderNumber.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {


           new FetchLogin().execute();
            // accountsIntent.putExtra("EMAIL", textInputEditTextIdNo.getText().toString().trim());
            emptyInputEditText();


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_no_password), Snackbar.LENGTH_LONG).show();
            // Toast.makeText(this, "Invalid trader number or password", Toast.LENGTH_SHORT);
        }
    }

    private void emptyInputEditText() {
        textInputEditTextTraderNumber.setText(null);
        textInputEditTextPassword.setText(null);
    }

    private boolean validatePassword(String pass) {
        if (pass.length() < 3 || pass.length() > 20) {
            textInputEditTextPassword.setError("Password Must consist of 3 to 20 characters");
            return false;
        }
        return true;
    }

    private boolean validateUsername(String email) {

        /* if (email.length() < 4 *//*|| email.length() > 30*//*) {
            textInputEditTextTraderNumber.setError("Email Must consist of at least characters");
            return false;
        } else if (!email.matches("^[A-za-z0-9.@]+")) {
            textInputEditTextTraderNumber.setError("Only . and @ characters allowed");
            return false;
        } else if (!email.contains("@") || !email.contains(".")) {
            textInputEditTextTraderNumber.setError("Email must contain @ and .");
            return false;
        }*/
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }

    }


    private class FetchLogin extends AsyncTask<Void, Void, Void> {

        private ProgressDialog mprogress = new ProgressDialog(LoginActivity.this);
        boolean islogin = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mprogress.show();
            mprogress.setTitle("Authenticating...");
            mprogress.setMessage("Authenticating...");
            mprogress.setCancelable(true);
            mprogress.setIndeterminate(false);

        }

        @Override
        protected Void doInBackground(Void... params) {

            //  verifyFromSQLite();
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(Void result) {

            if (mprogress.isShowing()) {
                mprogress.dismiss();
            }
            runOnUiThread(new Runnable() {
                public void run() {


                    // Start your app main activity


                    Intent intent = new Intent(getApplicationContext(), ActivityFundsDasboard.class);
                    startActivity(intent);

                    finish();


                    // close this activity
//                    finish();

                }
            });
        }
    }

    public void showSnackBar(String string, ScrollView scrollView) {
        snackbar = Snackbar.make(scrollView, string, Snackbar.LENGTH_INDEFINITE).
                setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
        snackbar.show();
    }


    // Handle toast messages
    public void toast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
                .show();
    }


}
