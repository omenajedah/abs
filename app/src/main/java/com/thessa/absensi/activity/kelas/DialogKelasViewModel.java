package com.thessa.absensi.activity.kelas;

import android.content.Context;
import android.text.Editable;
import android.view.View;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;
import java.util.Map;

import com.thessa.absensi.base.BaseViewModel;
import com.thessa.absensi.other.ConstantNetwork;
import com.thessa.absensi.pojo.Kelas;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/16/2018.
 */
public class DialogKelasViewModel extends BaseViewModel {

    public static final String AUTO_GENERATE = "Auto Generate";

    private final DialogEditSupplierListener listener;
    private final Kelas kelas;
    private Kelas edited;

    private boolean showDelete = true;

    private final int mode;

    public DialogKelasViewModel(Context context, Kelas supplier, int mode, DialogEditSupplierListener listener) {
        super(context);
        this.kelas = supplier;
        this.mode = mode;
        this.listener = listener;
        edited = new Kelas(supplier);
        }

    public boolean getShowDelete() {
        return showDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }

    public Kelas getKelas() {

        return kelas;
    }

    public void afterNamaChanged(Editable nama) {
        edited.setNama_kelas(nama.toString());
    }

    public void onSimpanClick(View v) {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                tambahKelas(false)
                .subscribe(aBoolean -> {
                    if (aBoolean)
                        listener.onSimpanBerhasil();
                    else listener.onGagalSimpan();
                }, throwable -> {
                    listener.onGagalSimpan();
                })
        );
    }

    public void onDelete(View v) {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                tambahKelas(true)
                        .subscribe(aBoolean -> {
                            if (aBoolean)
                                listener.onDelete();
                            else listener.onGagalSimpan();
                        }, throwable -> {
                            listener.onGagalSimpan();
                        })
        );
    }

    private Observable<Boolean> tambahKelas(boolean delete) {

        Map<String, String> param = new HashMap<>();
        param.put("nama_kelas", edited.getNama_kelas());

        if (!kelas.getId_kelas().equals(AUTO_GENERATE)) {
            param.put("id_kelas", edited.getId_kelas());
        }

        if (delete)
            param.put("delete", String.valueOf(1));


        return Rx2AndroidNetworking.post(ConstantNetwork.UBAH_KELAS)
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
