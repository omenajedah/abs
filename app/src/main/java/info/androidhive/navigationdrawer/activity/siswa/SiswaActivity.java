package info.androidhive.navigationdrawer.activity.siswa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.BR;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.base.BaseActivity;
import info.androidhive.navigationdrawer.databinding.ActivityHomeBinding;
import info.androidhive.navigationdrawer.databinding.ActivitySiswaBinding;
import info.androidhive.navigationdrawer.other.CustomDivider;
import info.androidhive.navigationdrawer.pojo.Kelas;
import info.androidhive.navigationdrawer.pojo.Siswa;

public class SiswaActivity extends BaseActivity<ActivitySiswaBinding, SiswaViewModel>
    implements SiswaViewModel.KelasListener {

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

        getBinding().listData.setAdapter(adapter);
        getBinding().listData.setHasFixedSize(true);
        getBinding().listData.setItemAnimator(new DefaultItemAnimator());
        getBinding().listData.addItemDecoration(new CustomDivider(this, CustomDivider.VERTICAL_LIST));

        siswaList.add(Siswa.add("00001", "Antono", 1, Kelas.add("0", "Kelas 1")));
        siswaList.add(Siswa.add("00002", "Toni Subarjo", 1, Kelas.add("0", "Kelas 1")));
        siswaList.add(Siswa.add("00003", "Ranti", 0, Kelas.add("0", "Kelas 1")));
        siswaList.add(Siswa.add("00004", "Rina ", 0, Kelas.add("0", "Kelas 1")));
        siswaList.add(Siswa.add("00005", "Tono ", 1, Kelas.add("0", "Kelas 1")));

        adapter.updateDataSet();
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