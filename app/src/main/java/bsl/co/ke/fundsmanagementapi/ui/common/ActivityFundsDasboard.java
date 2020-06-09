package bsl.co.ke.fundsmanagementapi.ui.common;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import bsl.co.ke.fundsmanagementapi.FMAPI;
import bsl.co.ke.fundsmanagementapi.R;
import bsl.co.ke.fundsmanagementapi.barcodeScanner.BarcodeScanner;
import bsl.co.ke.fundsmanagementapi.ui.activities.LoginActivity;
import bsl.co.ke.fundsmanagementapi.ui.activities.mpesa.MPESAExpressActivity;
import bsl.co.ke.fundsmanagementapi.ui.common.fragment.CenteredTextFragment;
import bsl.co.ke.fundsmanagementapi.ui.common.menu.DrawerAdapter;
import bsl.co.ke.fundsmanagementapi.ui.common.menu.DrawerItem;
import bsl.co.ke.fundsmanagementapi.ui.common.menu.SimpleItem;
import bsl.co.ke.fundsmanagementapi.ui.common.menu.SpaceItem;
import bsl.co.ke.fundsmanagementapi.ui.common.menu.slidingrootnav.SlidingRootNav;
import bsl.co.ke.fundsmanagementapi.ui.common.menu.slidingrootnav.SlidingRootNavBuilder;
import bsl.co.ke.fundsmanagementapi.ui.database.DataBaseAdapter;
import bsl.co.ke.fundsmanagementapi.ui.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yarolegovich on 25.03.2017.
 */

public class ActivityFundsDasboard extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_QR_GENERATOR = 1;
    private static final int POS_QR_SCANNER = 2;
    private static final int POS_ACCOUNT = 3;
    private static final int POS_MESSAGES = 4;
    private static final int POS_LOGOUT = 5;
    private static final int POS_CART = 6;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen1;
    private TextView barcodeText;
    private String barcodeData;



    private SlidingRootNav slidingRootNav;
    @BindView(R.id.ssselectfunction)
    Spinner ssselectfunction;
    @BindView(R.id.sstypeofaccount)
    Spinner sstypeofaccount;
    @BindView(R.id.editTextPhoneNumber)
    EditText editTextPhoneNumber;
    @BindView(R.id.editTextAmount)
    EditText editTextAmount;
    @BindView(R.id.editTexttradername)
    EditText editTexttradername;
    @BindView(R.id.editTextpin)
    EditText editTextpin;
    @BindView(R.id.ssRecipient)
    Spinner ssRecipient;
    @BindView(R.id.ssPaymentMode)
    Spinner ssPaymentMode;
    @BindView(R.id.phonenumbertxt)
    TextView phonenumbertxt;
    @BindView(R.id.amounttxt)
    TextView amounttxt;
    @BindView(R.id.tradertxtview)
    TextView tradertxtview;
    @BindView(R.id.recipinetxt)
    TextView recipinetxt;


    //mpesa

    @BindView(R.id.sendButton)
    Button sendButton;
    FMAPI app;
    DataBaseAdapter db;
    public User user;
    //Declare Daraja :: Global Variable
    Daraja daraja;


    protected List<User> userList;
    User userb = new User();

    String selectionfunction, typeofaccount, phoneNumber, amount, pin, recipient, paymentmode;

    String[] SelectFunction = {"-select function-", "My Account Top Up", "Send Funds", "Withdaw Funds", "Pay Trader", "Pay Loan"};
    String[] SelectRecipient = {"-select recipient-", "My Acount", "Trader Number", "Trader", "My Loan Account"};
    String[] SelectPaymentMode = {"-select payment mode-", "My Account", "Mpesa", "Airtel Money", "Eazy Pay", "Credit/Debit Card"};
    String[] accounttype = {"-select top up account-", "Self Top Up", "Trader Top Up"};

    private static final int accvisibility = 0;
    public Spinner sstypeofacc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //scanner
        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        surfaceView = findViewById(R.id.surface_view);


        Spinner spinFuniction = (Spinner) findViewById(R.id.ssselectfunction);
        Spinner spinRecipient = (Spinner) findViewById(R.id.ssRecipient);
        Spinner spinPaymentmode = (Spinner) findViewById(R.id.ssPaymentMode);
        sstypeofacc = (Spinner) findViewById(R.id.sstypeofaccount);
        // phoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);

        sendButton = (Button) findViewById(R.id.sendButton);

        List<String> accntype = new ArrayList<>();
        accntype.add("Select Acount");
        accntype.add("Self Top Up");
        accntype.add("Trader Account");


        initViews();
        initListeners();
        initObjects();

        sstypeofacc.setVisibility(View.GONE);
        surfaceView.setVisibility(View.GONE);
        editTextPhoneNumber.setVisibility(View.VISIBLE);
        phonenumbertxt.setVisibility(View.VISIBLE);
        editTextAmount.setVisibility(View.VISIBLE);
        amounttxt.setVisibility(View.VISIBLE);
        editTexttradername.setVisibility(View.GONE);
        tradertxtview.setVisibility(View.GONE);
        //select functionality


        db = new DataBaseAdapter(getApplicationContext()).open();



        spinFuniction.setOnItemSelectedListener(this);

        //Setting the ArrayAdapter data on the Spinner
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, SelectFunction);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinFuniction.setAdapter(arrayAdapter);

        //select functionality
        sstypeofacc.setOnItemSelectedListener(this);
        //Setting the ArrayAdapter data on the Spinner
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter arrayAdapterAcconttype = new ArrayAdapter(this, android.R.layout.simple_spinner_item, accounttype);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sstypeofacc.setAdapter(arrayAdapterAcconttype);

        //select recipient
        spinRecipient.setOnItemSelectedListener(this);
        //Setting the ArrayAdapter data on the Spinner
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter arrayAdapterRecipient = new ArrayAdapter(this, android.R.layout.simple_spinner_item, SelectRecipient);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinRecipient.setAdapter(arrayAdapterRecipient);


        //selectpayment mode
        spinPaymentmode.setOnItemSelectedListener(this);
        //Setting the ArrayAdapter data on the Spinner
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter arrayAdapterPaymentMode = new ArrayAdapter(this, android.R.layout.simple_spinner_item, SelectPaymentMode);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinPaymentmode.setAdapter(arrayAdapterPaymentMode);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_QR_GENERATOR),
                createItemFor(POS_QR_SCANNER),
                createItemFor(POS_ACCOUNT),
                createItemFor(POS_MESSAGES),
                createItemFor(POS_LOGOUT),
                new SpaceItem(10),
                createItemFor(POS_CART)));

        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);




    }

    private void initViews() {

        sendButton = (Button) findViewById(R.id.sendButton);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        sendButton.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        db = new DataBaseAdapter(this);

    }

    boolean validateSpinner(Spinner spinner, String error) {

        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            if (selectedTextView.getText().equals("-select function-")) {
                selectedTextView.setError(error);
                Toast.makeText(getApplicationContext(), "Required", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    boolean validateSpinnerpaymentmode(Spinner spinner, String error) {

        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            if (selectedTextView.getText().equals("-select payment mode-")) {
                selectedTextView.setError(error);
                Toast.makeText(getApplicationContext(), "Required", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    boolean validateSpinnerRecipient(Spinner spinner, String error) {

        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            if (selectedTextView.getText().equals("-select recipient-")) {
                selectedTextView.setError(error);
                Toast.makeText(getApplicationContext(), "Required", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    boolean validateSpinneracc(Spinner spinner, String error) {

        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            if (selectedTextView.getText().equals("-select top up account-")) {
                selectedTextView.setError(error);
                Toast.makeText(getApplicationContext(), "Required", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
           new FetchLogout().execute();
        }
        if (position == POS_QR_GENERATOR) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://m.apkure.com/qr-code-generator/com.ykart.tool.qrcodegen/download?from=details"));
            startActivity(intent);
        }
        if (position == POS_QR_SCANNER) {
            Intent intent = new Intent(this, BarcodeScanner.class);
            startActivity(intent);

        }
        slidingRootNav.closeMenu();
        Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
        showFragment(selectedScreen);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendButton:
                postDataToSQLite();

                break;
        }
    }

    private void postDataToSQLite() {
        selectionfunction = Objects.requireNonNull(ssselectfunction.getSelectedItem()).toString().trim();
        typeofaccount = Objects.requireNonNull(sstypeofaccount.getSelectedItem()).toString().trim();
        phoneNumber = Objects.requireNonNull(editTextPhoneNumber.getText()).toString().trim();
        recipient = Objects.requireNonNull(ssRecipient.getSelectedItem()).toString().trim();
        paymentmode = Objects.requireNonNull(ssPaymentMode.getSelectedItem()).toString().trim();
        amount = Objects.requireNonNull(editTextAmount.getText()).toString().trim();
        pin = Objects.requireNonNull(editTextpin.getText()).toString().trim();

        boolean valid = validateSpinner(ssselectfunction, selectionfunction) &
                validateSpinnerRecipient(ssRecipient, recipient) &
                validateSpinnerpaymentmode(ssPaymentMode, paymentmode);

        if (TextUtils.isEmpty(amount)) {
            editTextAmount.setError("Field Required");
            return;
        } else if (TextUtils.isEmpty(pin)) {
            editTextpin.setError("Field Required");
            return;
        }


          /*  user.setSelectionfunction(ssselectfunction.getSelectedItem().toString().trim());
            user.setTypeofaccount(sstypeofaccount.getSelectedItem().toString().trim());
            user.setPhoneNumber(editTextPhoneNumber.getText().toString().trim());
            user.setRecipient(ssRecipient.getSelectedItem().toString().trim());
            user.setPaymentmode(ssPaymentMode.getSelectedItem().toString().trim());
            user.setAmount(editTextAmount.getText().toString().trim());
            user.setPin(editTextpin.getText().toString().trim());

            db.updateUser(user);*/

            editTextPhoneNumber.setText(null);
            editTextAmount.setText(null);
            editTextpin.setText(null);

            new submit().execute();
            // finish();




    }

    private class FetchLogout extends AsyncTask<Void, Void, Void> {

        private ProgressDialog mprogress = new ProgressDialog(ActivityFundsDasboard.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mprogress.show();
            mprogress.setTitle("Authenticating...");
            mprogress.setMessage("Logging Out...");
            mprogress.setCancelable(false);
            mprogress.setIndeterminate(false);

        }

        @Override
        protected Void doInBackground(Void... params) {

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


                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                    finish();


                    // close this activity
//                    finish();

                }
            });
        }
    }

    private class submit extends AsyncTask<Void, Void, Void> {

        private ProgressDialog mprogress = new ProgressDialog(ActivityFundsDasboard.this);
        boolean islogin = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mprogress.show();
            mprogress.setTitle("Authenticating...");
            mprogress.setMessage("Submitting Request...");
            mprogress.setCancelable(false);
            mprogress.setIndeterminate(false);

        }

        @Override
        protected Void doInBackground(Void... params) {

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

                }
            });
        }
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.colorPrimary))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.black))
                .withSelectedTextTint(color(R.color.black));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }


    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selection = (String) parent.getItemAtPosition(position);
        if (selection.equals("-select function-")) {
            sstypeofacc.setVisibility(View.GONE);
            surfaceView.setVisibility(View.GONE);
            editTexttradername.setVisibility(View.GONE);
            tradertxtview.setVisibility(View.GONE);
            recipinetxt.setVisibility(View.VISIBLE);
            ssRecipient.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Function Required", Toast.LENGTH_SHORT);
        }

        else if (selection.equals("My Account Top Up")) {
            sstypeofacc.setVisibility(View.VISIBLE);
            surfaceView.setVisibility(View.VISIBLE);
            recipinetxt.setVisibility(View.GONE);
            ssRecipient.setVisibility(View.GONE);
            editTexttradername.setVisibility(View.VISIBLE);
            tradertxtview.setVisibility(View.VISIBLE);
            initialiseDetectorsources();

            validateSpinneracc(sstypeofaccount, typeofaccount);
        }
        else if (selection.equals("Self Top Up")) {
            editTextPhoneNumber.setText(phoneNumber);

        }


        else if (selection.equals("Withdaw Funds")) {
            sstypeofacc.setVisibility(View.GONE);
            surfaceView.setVisibility(View.VISIBLE);
            recipinetxt.setVisibility(View.GONE);
            ssRecipient.setVisibility(View.GONE);
            editTexttradername.setVisibility(View.VISIBLE);
            tradertxtview.setVisibility(View.VISIBLE);
            editTextPhoneNumber.setVisibility(View.GONE);
            phonenumbertxt.setVisibility(View.GONE);
            initialiseDetectorsources();
        }



        else if (selection.equals("Send Funds")) {
            sstypeofacc.setVisibility(View.GONE);
            surfaceView.setVisibility(View.GONE);
            editTexttradername.setVisibility(View.GONE);
            tradertxtview.setVisibility(View.GONE);
            recipinetxt.setVisibility(View.VISIBLE);
            ssRecipient.setVisibility(View.VISIBLE);
            editTextPhoneNumber.setEnabled(true);


        }  else if (selection.equals("Pay Loan")) {
            sstypeofacc.setVisibility(View.GONE);
            surfaceView.setVisibility(View.GONE);
            editTextPhoneNumber.setEnabled(true);

            editTexttradername.setVisibility(View.GONE);
            tradertxtview.setVisibility(View.GONE);
            recipinetxt.setVisibility(View.VISIBLE);
            ssRecipient.setVisibility(View.VISIBLE);

        }

        else if (selection.equals("Pay Trader")) {
            sstypeofacc.setVisibility(View.GONE);
            surfaceView.setVisibility(View.VISIBLE);
            recipinetxt.setVisibility(View.GONE);
            ssRecipient.setVisibility(View.GONE);
            editTexttradername.setVisibility(View.VISIBLE);
            tradertxtview.setVisibility(View.VISIBLE);
            initialiseDetectorsources();
        }

        else if (selection.equals("Mpesa")) {
            sstypeofacc.setVisibility(View.GONE);

            editTextPhoneNumber.setVisibility(View.GONE);
            phonenumbertxt.setVisibility(View.GONE);
            editTextAmount.setVisibility(View.GONE);
            amounttxt.setVisibility(View.GONE);
            Intent intent = new Intent(getApplicationContext(), MPESAExpressActivity.class);
            startActivity(intent);
        }

        else if (selection.equals("Airtel Money")) {
            surfaceView.setVisibility(View.GONE);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://airtelkenya.com/more-airtel-money"));
            startActivity(intent);
        } else if (selection.equals("Eazy Pay")) {
            surfaceView.setVisibility(View.GONE);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://eazypay.icicibank.com/homePage"));
            startActivity(intent);
        } else if (selection.equals("Credit/Debit Card")) {
            surfaceView.setVisibility(View.GONE);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.dpogroup.com/africa/accept-credit-cards-online/"));
            startActivity(intent);
        } else if (selection.equals("Pay Loan")) {
            surfaceView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //scanner
    private void initialiseDetectorsources() {

        //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ActivityFundsDasboard.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ActivityFundsDasboard.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    editTextPhoneNumber.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                editTextPhoneNumber.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                editTextPhoneNumber.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                editTextPhoneNumber.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                            }
                        }
                    });

                }
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    editTexttradername.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                editTexttradername.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                editTexttradername.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                editTexttradername.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                            }
                        }
                    });

                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        getSupportActionBar().hide();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
           getSupportActionBar().show();
        initialiseDetectorsources();
    }


    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        finishAffinity();
        finish();
        return true;
    }


}
