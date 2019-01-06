package com.thessa.absensi.activity.laporandetil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.thessa.absensi.R;
import com.thessa.absensi.base.BaseActivity;
import com.thessa.absensi.databinding.ActivityLaporanDetilBinding;
import com.thessa.absensi.BR;
import com.thessa.absensi.pojo.Siswa;

/**
 * Created by Firman on 1/6/2019.
 */
public class LaporanDetailActivity extends BaseActivity<ActivityLaporanDetilBinding, LaporanDetilViewModel> implements LaporanDetilViewModel.LaporanListener {

    private LaporanDetilViewModel viewModel;

    public static void startThisActivity(Context context, Siswa siswa) {
        Intent intent = new Intent(context, LaporanDetailActivity.class);
        intent.putExtra("siswa", siswa);
        context.startActivity(intent);
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_laporan_detil;
    }

    @Override
    public LaporanDetilViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new LaporanDetilViewModel(this, this);
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel.setSiswa((Siswa) getIntent().getSerializableExtra("siswa"));
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
