package com.thessa.absensi.activity.home;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.thessa.absensi.activity.absensi.AbsensiActivity;
import com.thessa.absensi.activity.kelas.KelasActivity;
import com.thessa.absensi.activity.laporan.LaporanActivity;
import com.thessa.absensi.activity.siswa.SiswaActivity;
import com.thessa.absensi.base.BaseViewModel;

/**
 * Created by Firman on 12/19/2018.
 */
public class HomeViewModel extends BaseViewModel {

    private final HomeListener listener;
    private ObservableField<String> namaUser = new ObservableField<>();
    private ObservableInt tipeUser = new ObservableInt();

    public HomeViewModel(Context context, HomeListener listener) {
        super(context);
        this.listener=listener;
        namaUser.set(getSessionHandler().getString("user_fullname", null));
        tipeUser.set(getSessionHandler().get("user_type", 1));
    }

    public void onKelasClicked(View v) {
        KelasActivity.startThisActivity(getContext());
    }

    public void onSiswaClicked(View v) {
        SiswaActivity.startThisActivity(getContext());
    }

    public void onAbsensiClicked(View v) {
        AbsensiActivity.startThisActivity(getContext());

    }

    public void onLaporanClicked(View v) {
        LaporanActivity.startThisActivity(getContext());
    }

    public void onLogoutClicked(View v) {
        getSessionHandler().clear();
        listener.onLogout();
    }

    public ObservableField<String> getNamaUser() {
        return namaUser;
    }

    public ObservableInt getTipeUser() {
        return tipeUser;
    }

    public interface HomeListener {
        void onLogout();
    }
}
