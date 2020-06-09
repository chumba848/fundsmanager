package bsl.co.ke.fundsmanagementapi.ui.Steppers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import bsl.co.ke.fundsmanagementapi.R;
import bsl.co.ke.fundsmanagementapi.ui.activities.AboutActivity;
import bsl.co.ke.fundsmanagementapi.ui.activities.GetStartedActivity;

public class StartUpStepper extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "";

    public StartUpStepper(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        if (position == 0) {
            final AboutActivity step = new AboutActivity();
            Bundle b = new Bundle();
            b.putInt(CURRENT_STEP_POSITION_KEY, position);
            step.setArguments(b);
            return step;
        } else if (position == 1) {
            final GetStartedActivity step = new GetStartedActivity();
            Bundle b = new Bundle();
            b.putInt(CURRENT_STEP_POSITION_KEY, position);
            step.setArguments(b);
            return step;
        }
        final AboutActivity step = new AboutActivity();
        Bundle b = new Bundle();
        b.putInt(CURRENT_STEP_POSITION_KEY, position);
        step.setArguments(b);
        return step;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        if (position == 0) {
            return new StepViewModel.Builder(context)
                   // .setTitle(R.string.)
                    // .setEndButtonLabel(R.string.proceed)
                    .create();
        } else if (position == 1) {
            return new StepViewModel.Builder(context)
                    //.setTitle(R.string.facility_inspection)
                    // .setEndButtonLabel(R.string.proceed)
                    // .setBackButtonLabel(R.string.back)
                    .create();
        } else if (position == 2) {
            return new StepViewModel.Builder(context)
                    //.setTitle(R.string.facility_inspection)
                    // .setEndButtonLabel(R.string.proceed)
                    // .setBackButtonLabel(R.string.back)
                    .create();
        }
        return new StepViewModel.Builder(context)
               // .setTitle(R.string.facility_inspection)
                // .setEndButtonLabel(R.string.proceed)
                .create();
    }

}
