package com.thessa.absensi.activity.absensi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.thessa.absensi.BR;
import com.thessa.absensi.R;
import com.thessa.absensi.base.BaseActivity;
import com.thessa.absensi.databinding.ActivityAbsensiBinding;
import com.thessa.absensi.other.CustomDivider;
import com.thessa.absensi.pojo.AbsensiSiswa;

public class AbsensiActivity extends BaseActivity<ActivityAbsensiBinding, AbsensiViewModel>
    implements AbsensiViewModel.AbsensiListener {

    private AbsensiViewModel viewModel;
    private List<AbsensiSiswa> absensiSiswaList = new ArrayList<>();
    private AbsensiAdapter adapter;

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, AbsensiActivity.class));
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_absensi;
    }

    @Override
    public AbsensiViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new AbsensiViewModel(this, this);

        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new AbsensiAdapter(absensiSiswaList);
        adapter.setBaseRecyclerOnSuccessListener(() -> viewModel.refresh());

        getBinding().listData.setAdapter(adapter);
        getBinding().listData.setHasFixedSize(true);
        getBinding().listData.setItemAnimator(new DefaultItemAnimator());
        getBinding().listData.addItemDecoration(new CustomDivider(this, CustomDivider.VERTICAL_LIST));

        viewModel.refresh();
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

    @Override
    public void onRefresh(List<AbsensiSiswa> absen) {
        this.absensiSiswaList.clear();
        this.absensiSiswaList.addAll(absen);
        adapter.updateDataSet();
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this,"terjadi error", Toast.LENGTH_SHORT).show();
    }
}