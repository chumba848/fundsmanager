package bsl.co.ke.fundsmanagementapi.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;

import bsl.co.ke.fundsmanagementapi.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    BottomAppBar bottomAppBar;

    ToggleButton toggleButton;

    BottomSheetBehavior<View> bottomSheetBehavior;

    CoordinatorLayout coordinatorLayout;

    FloatingActionButton fab;
    NavigationView navigationView;

    Context context;

    static String uri;


    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    private String name, email, photo, mobile;
    private DrawerLayout drawerLayout;

    //  ProfileDialog profileDialog;

    Intent serviceIntent;

    private FloatingNavigationView mFloatingNavigationView;


    @BindView(R.id.ssselectfunction)
    Spinner ssselectfunction;
    @BindView(R.id.ssRecipient)
    Spinner ssRecipient;
    @BindView(R.id.ssPaymentMode)
    Spinner ssPaymentMode;

    String[] SelectFunction = {"-select function-", "My Account Top Up", "Send Funds", "Withdaw Funds", "Pay Trader", "Pay Loan"};
    String[] SelectRecipient = {"-select recipient-", "My Acount", "Trader Number", "Trader", "My Loan Account"};
    String[] SelectPaymentMode = {"-select payment mode-", "My Account", "Mpesa", "Airtel Money", "Eazy Pay", "Credit/Debit Card"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btm_appbar_fab);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinFuniction = (Spinner) findViewById(R.id.ssselectfunction);
        Spinner spinRecipient = (Spinner) findViewById(R.id.ssRecipient);
        Spinner spinPaymentmode = (Spinner) findViewById(R.id.ssPaymentMode);

        //select functionality

        spinFuniction.setOnItemSelectedListener(this);
        //Setting the ArrayAdapter data on the Spinner
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, SelectFunction);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinFuniction.setAdapter(arrayAdapter);

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


        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingNavigationView.open();
            }
        });
        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //  Snackbar.make((View) mFloatingNavigationView.getParent(), item.getTitle() + " Selected!", Snackbar.LENGTH_SHORT).show();
                mFloatingNavigationView.close();
                return true;
            }
        });

     /*   snackProgressBarManager = new SnackProgressBarManager(coordinatorLayout)
                .setProgressBarColor(R.color.md_green_800)
                .setOverlayLayoutAlpha(0.9f);
*/

        init();

    }

    boolean validateSpinner(Spinner spinner, String error) {

        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            if (selectedTextView.getText().equals("-select function-")) {
                selectedTextView.setError(error);
                Toast.makeText(getApplicationContext(), "Function Required", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void init() {
        if (getSupportActionBar() != null) {
            //  getSupportActionBar().setTitle("KEPHIS");
            //getSupportActionBar().setHomeButtonEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        context = DashboardActivity.this;
        navigationView = findViewById(R.id.navigation_view);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        View bottomDrawer = coordinatorLayout.findViewById(R.id.bottom_drawer);
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        toggleButton = findViewById(R.id.btn_toggle);
        //  toggleButton.setChecked(fab.getVisibility() == View.VISIBLE);
       /* toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) fab.show();
                else fab.hide();

            }

        });*/

     /*   navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override


            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.btnlogout) {

                    return true;
                }
                //  TextUtils.showToast(context, "Search Clicked", Toast.LENGTH_SHORT);

                else if (item.getItemId() == R.id.btm_aboutus) {
                    return true;
                }
                // 66 AppUtils.showToast(context, "Accelerator Clicked", Toast.LENGTH_SHORT);
                return true;


            }

        });*/

        bottomSheetBehavior = BottomSheetBehavior.from(bottomDrawer);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomAppBar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        bottomAppBar.replaceMenu(R.menu.menu_demo);
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

            }

        });
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override


            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btnlogout:

                        break;

                    case R.id.btm_aboutus:
                        // AppUtils.showToast(context, "3D Clicked!!", Toast.LENGTH_SHORT);
                        break;


                }
                return false;

            }

        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                Toast.makeText(context, "User Account Info Here!!!", Toast.LENGTH_SHORT).show();
            }

        });

    }


    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            return;

        }
        super.onBackPressed();

    }


    public void showFabCenter(View view) {
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);

    }


    public void showFabEnd(View view) {
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selection = (String) parent.getItemAtPosition(position);
        if (selection.equals("-select function-")) {
            Toast.makeText(this, "Function Required", Toast.LENGTH_SHORT);
        } else if (selection.equals("My Account Top Up")) {
           /* Intent intent = new Intent(this, PictureCodeActivity.class);
            startActivity(intent);*/
        } else if (selection.equals("Withdaw Funds")) {
           /* Intent intent = new Intent(this, PictureCodeActivity.class);
            startActivity(intent);*/
        } else if (selection.equals("Pay Trader")) {
           /* Intent intent = new Intent(this, PictureCodeActivity.class);
            startActivity(intent);*/
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //get data

/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/


}
