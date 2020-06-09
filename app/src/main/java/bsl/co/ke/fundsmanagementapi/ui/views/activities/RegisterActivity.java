package bsl.co.ke.fundsmanagementapi.ui.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import bsl.co.ke.fundsmanagementapi.FMAPI;
import bsl.co.ke.fundsmanagementapi.R;
import bsl.co.ke.fundsmanagementapi.ui.activities.LoginActivity;
import bsl.co.ke.fundsmanagementapi.ui.database.DataBaseAdapter;
import bsl.co.ke.fundsmanagementapi.ui.database.DatabaseHelper;
import bsl.co.ke.fundsmanagementapi.ui.database.InputValidation;
import bsl.co.ke.fundsmanagementapi.ui.model.User;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private NestedScrollView nestedScrollView;
    private EditText textInputEditTextName;
    private EditText textInputEditTextTradernumber;
    private EditText textInputEditTextPin;
    private Button appCompatButtonRegister;
    private TextView appCompatTextViewLoginLink;
    private InputValidation inputValidation;
    private DataBaseAdapter db;
    FMAPI app;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysignup);

        //   app = (FMAPI) getApplication();
        db = new DataBaseAdapter(getApplicationContext());

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputEditTextName = (EditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextTradernumber = (EditText) findViewById(R.id.textInputEditTextTraderNumber);
        textInputEditTextPin = (EditText) findViewById(R.id.textInputEditTextPin);

        appCompatButtonRegister = (Button) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (TextView) findViewById(R.id.appCompatTextViewLoginLink);

    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(this);
        db = new DataBaseAdapter(this);
        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        //  textInputLayoutname =
        String name = Objects.requireNonNull(textInputEditTextName.getText()).toString().trim();
        String phoneno = Objects.requireNonNull(textInputEditTextTradernumber.getText()).toString().trim();
        String pin = Objects.requireNonNull(textInputEditTextPin.getText()).toString().trim();


        if (TextUtils.isEmpty(name)) {
            textInputEditTextName.setError("Field Required");
            return;
        }

        if (TextUtils.isEmpty(phoneno)) {
            textInputEditTextTradernumber.setError("Field Required");
            return;
        }
        if (TextUtils.isEmpty(pin)) {
            textInputEditTextPin.setError("Field Required");
            return;
        } else if (!db.checkUser(textInputEditTextTradernumber.getText().toString().trim())) {
            user.setName(textInputEditTextName.getText().toString().trim());
            user.setIdno(textInputEditTextTradernumber.getText().toString().trim());
            user.setPassword(textInputEditTextPin.getText().toString().trim());

            db.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_ID_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextTradernumber.setText(null);
        textInputEditTextPin.setText(null);

    }
}
