package com.thessa.absensi.activity.siswa;

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
import com.thessa.absensi.databinding.ActivitySiswaBinding;
import com.thessa.absensi.other.CustomDivider;
import com.thessa.absensi.pojo.Siswa;

public class SiswaActivity extends BaseActivity<ActivitySiswaBinding, SiswaViewModel>
    implements SiswaViewModel.SiswaListener {

    private SiswaViewModel viewModel;
    private List<Siswa> siswaList = new ArrayList<>();
    private SiswaAdapter adapter;

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, SiswaActivity.class));
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_siswa;
    }

    @Override
    public SiswaViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new SiswaViewModel(this, this);

        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new SiswaAdapter(siswaList);
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
    public void onRefresh(List<Siswa> siswa) {
        this.siswaList.clear();
        this.siswaList.addAll(siswa);
        adapter.updateDataSet();
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this,"terjadi error", Toast.LENGTH_SHORT).show();
    }
}