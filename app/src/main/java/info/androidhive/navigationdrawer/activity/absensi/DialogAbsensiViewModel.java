package info.androidhive.navigationdrawer.activity.absensi;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.RadioGroup;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;
import java.util.Map;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.base.BaseViewModel;
import info.androidhive.navigationdrawer.other.ConstantNetwork;
import info.androidhive.navigationdrawer.pojo.AbsensiSiswa;
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
    private AbsensiSiswa edited;

    private boolean showDelete = true;

    private final int mode;

    public DialogAbsensiViewModel(Context context, AbsensiSiswa supplier, int mode, DialogEditSupplierListener listener) {
        super(context);
        this.siswa = supplier;
        this.mode = mode;
        this.listener = listener;
        edited = new AbsensiSiswa(supplier);
    }

    public boolean getShowDelete() {
        return showDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
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

    public void onDelete(View v) {

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
