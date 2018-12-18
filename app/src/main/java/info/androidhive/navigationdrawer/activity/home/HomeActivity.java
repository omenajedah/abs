package info.androidhive.navigationdrawer.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import info.androidhive.navigationdrawer.BR;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.login.LoginActivity;
import info.androidhive.navigationdrawer.base.BaseActivity;
import info.androidhive.navigationdrawer.databinding.ActivityHomeBinding;

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
    }
}