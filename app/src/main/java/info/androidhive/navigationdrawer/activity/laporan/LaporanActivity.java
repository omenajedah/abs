package info.androidhive.navigationdrawer.activity.laporan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.BR;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.kelas.KelasAdapter;
import info.androidhive.navigationdrawer.base.BaseActivity;
import info.androidhive.navigationdrawer.databinding.ActivityKelasBinding;
import info.androidhive.navigationdrawer.databinding.ActivityLaporanBinding;
import info.androidhive.navigationdrawer.pojo.Kelas;

public class LaporanActivity extends BaseActivity<ActivityLaporanBinding, LaporanViewModel>
    implements LaporanViewModel.KelasListener {

    private LaporanViewModel viewModel;

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, LaporanActivity.class));
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_laporan;
    }

    @Override
    public LaporanViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new LaporanViewModel(this, this);

        return viewModel;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}