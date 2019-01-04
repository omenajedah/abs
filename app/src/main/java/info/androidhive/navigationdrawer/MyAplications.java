package info.androidhive.navigationdrawer;

import android.app.Application;
import android.text.Editable;
import android.text.TextWatcher;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;

public class MyAplications extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidNetworking.initialize(this);
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
    }
}
