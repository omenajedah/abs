package info.androidhive.navigationdrawer.activity.absensi;

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
import info.androidhive.navigationdrawer.databinding.ActivityAbsensiBinding;
import info.androidhive.navigationdrawer.other.CustomDivider;
import info.androidhive.navigationdrawer.pojo.AbsensiSiswa;
import info.androidhive.navigationdrawer.pojo.Kelas;
import info.androidhive.navigationdrawer.pojo.Siswa;

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

        getBinding().listData.setAdapter(adapter);
        getBinding().listData.setHasFixedSize(true);
        getBinding().listData.setItemAnimator(new DefaultItemAnimator());
        getBinding().listData.addItemDecoration(new CustomDivider(this, CustomDivider.VERTICAL_LIST));

        absensiSiswaList.add(AbsensiSiswa.add("00001", "Antono", 1, AbsensiSiswa.STATUS_BELUM_ABSEN, Kelas.add("0", "Kelas 1")));
        absensiSiswaList.add(AbsensiSiswa.add("00002", "Toni Subarjo", 1, AbsensiSiswa.STATUS_ALPHA, Kelas.add("0", "Kelas 1")));
        absensiSiswaList.add(AbsensiSiswa.add("00003", "Ranti", 0, AbsensiSiswa.STATUS_HADIR, Kelas.add("0", "Kelas 1")));
        absensiSiswaList.add(AbsensiSiswa.add("00004", "Rina ", 0, AbsensiSiswa.STATUS_IZIN, Kelas.add("0", "Kelas 1")));
        absensiSiswaList.add(AbsensiSiswa.add("00005", "Tono ", 1, AbsensiSiswa.STATUS_SAKIT, Kelas.add("0", "Kelas 1")));

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