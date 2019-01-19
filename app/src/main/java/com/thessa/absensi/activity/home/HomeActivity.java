package com.thessa.absensi.activity.home;

import android.content.Context;
import android.content.Intent;

import com.thessa.absensi.BR;
import com.thessa.absensi.R;
import com.thessa.absensi.activity.login.LoginActivity;
import com.thessa.absensi.base.BaseActivity;
import com.thessa.absensi.databinding.ActivityHomeBinding;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel>
    implements HomeViewModel.HomeListener {

    private HomeViewModel viewModel;

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new HomeViewModel(this, this);

        return viewModel;
    }


    @Override
    public void onLogout() {
        finish();
        LoginActivity.startThisActivity(this);
    }
}