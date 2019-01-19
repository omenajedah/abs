package com.thessa.absensi.activity.laporandetil;

import android.content.Context;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.thessa.absensi.base.BaseViewModel;
import com.thessa.absensi.other.ConstantNetwork;
import com.thessa.absensi.pojo.AbsensiSiswa;
import com.thessa.absensi.pojo.AbsensiSiswaSummary;
import com.thessa.absensi.pojo.Siswa;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 1/6/2019.
 */
public class LaporanDetilViewModel extends BaseViewModel {

    private final LaporanListener listener;

    private final AbsensiSiswaSummary siswa = new AbsensiSiswaSummary();

    public LaporanDetilViewModel(Context context, LaporanListener listener) {
        super(context);
        this.listener=listener;
    }

    public void setSiswa(Siswa siswa) {
        this.siswa.copy(siswa);
        downloadSummary();
    }

    public void downloadSummary() {
        getCompositeDisposable().clear();
        getCompositeDisposable().add(
                getAbsensi().subscribe(aBoolean -> {

                }, throwable -> {
                    throwable.printStackTrace();
                })
        );
    }

    private Observable<Boolean> getAbsensi() {

        return Rx2AndroidNetworking.post(ConstantNetwork.CHECKSISWA)
                .addBodyParameter("nisn", siswa.getNisn())
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    siswa.setTotal_hari_bulan(jsonObject.getInt("TOTAL_HARI_BULAN"));
                    siswa.setTotal_hari_absen(jsonObject.getInt("TOTAL_HARI_ABSEN"));
                    siswa.setTotal_absen_hadir(jsonObject.getInt("TOTAL_ABSEN_HADIR"));
                    siswa.setTotal_absen_izin(jsonObject.getInt("TOTAL_ABSEN_IZIN"));
                    siswa.setTotal_absen_sakit(jsonObject.getInt("TOTAL_ABSEN_SAKIT"));
                    siswa.setTotal_absen_alpha(jsonObject.getInt("TOTAL_ABSEN_ALPHA"));
                    siswa.setTotal_persentase(jsonObject.getInt("PERSENTASE_KEHADIRAN"));

                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public AbsensiSiswaSummary getSiswa() {
        return siswa;
    }

    public interface LaporanListener {

    }
}
