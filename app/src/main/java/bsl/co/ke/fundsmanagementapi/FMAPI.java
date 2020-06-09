package bsl.co.ke.fundsmanagementapi;
import android.app.Application;
import android.graphics.Typeface;

import androidx.lifecycle.MutableLiveData;

import com.facebook.stetho.Stetho;

import bsl.co.ke.fundsmanagementapi.ui.model.User;
import bsl.co.ke.fundsmanagementapi.ui.views.settings.Settings;
import bsl.co.ke.fundsmanagementapi.ui.views.widgets.TypeFactory;

public class FMAPI extends Application {
    private static bsl.co.ke.fundsmanagementapi.FMAPI mInstance;
    public Settings settings;
    private TypeFactory mFontFactory;

    public static synchronized bsl.co.ke.fundsmanagementapi.FMAPI getApp() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        settings = new Settings(getApplicationContext());

        Stetho.initializeWithDefaults(this);

        mInstance = this;
    }

    public Typeface getTypeFace(int type) {
        if (mFontFactory == null)
            mFontFactory = new TypeFactory(this);

        switch (type) {
            case Constants.REGULAR:
                return mFontFactory.getRegular();

            case Constants.BOLD:
                return mFontFactory.getBold();

            default:
                return mFontFactory.getRegular();
        }
    }

    public interface Constants {
        int REGULAR = 1,
                BOLD = 2;
    }

    private User user;



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    private MutableLiveData<User> producerInspectionDetails;


    public MutableLiveData<User> getUserDetails() {
        if (producerInspectionDetails == null) {
            producerInspectionDetails = new MutableLiveData<User>();
        }
        return producerInspectionDetails;
    }

}
