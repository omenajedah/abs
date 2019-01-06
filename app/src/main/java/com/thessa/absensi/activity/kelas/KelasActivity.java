package com.thessa.absensi.activity.kelas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.thessa.absensi.BR;
import com.thessa.absensi.R;
import com.thessa.absensi.base.BaseActivity;
import com.thessa.absensi.databinding.ActivityHomeBinding;
import com.thessa.absensi.databinding.ActivityKelasBinding;
import com.thessa.absensi.pojo.Kelas;

public class KelasActivity extends BaseActivity<ActivityKelasBinding, KelasViewModel>
    implements KelasViewModel.KelasListener {

    private KelasViewModel viewModel;
    private List<Kelas> kelasList = new ArrayList<>();
    private KelasAdapter adapter;

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, KelasActivity.class));
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_kelas;
    }

    @Override
    public KelasViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new KelasViewModel(this, this);

        return viewModel;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new KelasAdapter(kelasList);
        adapter.setBaseRecyclerOnSuccessListener(() -> viewModel.refresh());

        getBinding().listData.setAdapter(adapter);
        getBinding().listData.setHasFixedSize(true);
        getBinding().listData.setItemAnimator(new DefaultItemAnimator());

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        getBinding().listData.setLayoutManager(layoutManager);
//        getBinding().listData.addItemDecoration(new CustomDivider(this, V));

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
    public void onRefresh(List<Kelas> kelas) {
        this.kelasList.clear();
        this.kelasList.addAll(kelas);
        adapter.updateDataSet();
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this,"terjadi error", Toast.LENGTH_SHORT).show();
    }
}