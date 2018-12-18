package info.androidhive.navigationdrawer.activity.absensi;

import android.content.Context;
import android.text.Editable;
import android.view.View;

import info.androidhive.navigationdrawer.base.BaseViewModel;
import info.androidhive.navigationdrawer.pojo.AbsensiSiswa;
import info.androidhive.navigationdrawer.pojo.Kelas;

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

    }

    public void onDelete(View v) {

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
