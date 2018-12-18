package info.androidhive.navigationdrawer.activity.home;

import android.content.Context;
import android.view.View;

import info.androidhive.navigationdrawer.activity.absensi.AbsensiActivity;
import info.androidhive.navigationdrawer.activity.kelas.KelasActivity;
import info.androidhive.navigationdrawer.activity.laporan.LaporanActivity;
import info.androidhive.navigationdrawer.activity.siswa.SiswaActivity;
import info.androidhive.navigationdrawer.base.BaseViewModel;
import info.androidhive.navigationdrawer.pojo.Kelas;

/**
 * Created by Firman on 12/19/2018.
 */
public class HomeViewModel extends BaseViewModel {

    private final HomeListener listener;
    public HomeViewModel(Context context, HomeListener listener) {
        super(context);
        this.listener=listener;
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
        listener.onLogout();
    }

    public interface HomeListener {
        void onLogout();
    }
}
