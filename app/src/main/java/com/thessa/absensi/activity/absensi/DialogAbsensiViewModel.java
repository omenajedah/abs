package com.thessa.absensi.activity.absensi;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.view.View;
import android.widget.RadioGroup;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.thessa.absensi.R;
import com.thessa.absensi.base.BaseViewModel;
import com.thessa.absensi.other.ConstantNetwork;
import com.thessa.absensi.pojo.AbsensiSiswa;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/16/2018.
 */
public class DialogAbsensiViewModel extends BaseViewModel {

    public static final String AUTO_GENERATE = "Auto Generate";

    private final DialogEditSupplierListener listener;
    private final AbsensiSiswa siswa;
    private final int mode;
    private AbsensiSiswa edited;
    private boolean readOnly = true;

    public DialogAbsensiViewModel(Context context, AbsensiSiswa supplier, int mode, DialogEditSupplierListener listener) {
        super(context);
        this.siswa = supplier;
        this.mode = mode;
        this.listener = listener;
        edited = new AbsensiSiswa(supplier);
    }

    public boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public AbsensiSiswa getSiswa() {
        return siswa;
    }

    public void afterNamaChanged(Editable nama) {
        edited.setNama_siswa(nama.toString());
    }

    public void onSimpanClick(View v) {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                absenSiswa(false)
                        .subscribe(aBoolean -> {
                            if (aBoolean)
                                listener.onSimpanBerhasil();
                            else listener.onGagalSimpan();
                        }, throwable -> {
                            listener.onGagalSimpan();
                        })
        );
    }

    @IdRes
    public int getCheckedAbsen() {
        switch (siswa.getAbsen_siswa()) {
            case AbsensiSiswa.STATUS_HADIR:
                return R.id.absen_hadir;
            case AbsensiSiswa.STATUS_ALPHA:
                return R.id.absen_alpha;
            case AbsensiSiswa.STATUS_IZIN:
                return R.id.absen_izin;
            case AbsensiSiswa.STATUS_SAKIT:
                return R.id.absen_sakit;

                default: return -1;
        }
    }

    public void onAbsen(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.absen_hadir:
                edited.setAbsen_siswa(AbsensiSiswa.STATUS_HADIR);
                break;
            case R.id.absen_alpha:
                edited.setAbsen_siswa(AbsensiSiswa.STATUS_ALPHA);
                break;
            case R.id.absen_izin:
                edited.setAbsen_siswa(AbsensiSiswa.STATUS_IZIN);
                break;
            case R.id.absen_sakit:
                edited.setAbsen_siswa(AbsensiSiswa.STATUS_SAKIT);
                break;
        }
    }

    private Observable<Boolean> absenSiswa(boolean delete) {

        Map<String, String> param = new HashMap<>();
        param.put("tipe_absen", String.valueOf(edited.getAbsen_siswa()));
        param.put("id_kelas", edited.getKelas().getId_kelas());
        param.put("nisn", edited.getNisn());

        if (delete)
            param.put("delete", String.valueOf(1));


        return Rx2AndroidNetworking.post(ConstantNetwork.CHECK)
                .addBodyParameter(param)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> jsonObject.optBoolean("success"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void onBatalClick(View v) {
        listener.onBatal();
    }

    public interface DialogEditSupplierListener {
        void onSimpanBerhasil();

        void onGagalSimpan();

        void onDelete();

        void onBatal();
    }
}
