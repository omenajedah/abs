package info.androidhive.navigationdrawer.activity.siswa;

import android.content.Context;
import android.text.Editable;
import android.view.View;

import info.androidhive.navigationdrawer.base.BaseViewModel;
import info.androidhive.navigationdrawer.pojo.Kelas;
import info.androidhive.navigationdrawer.pojo.Siswa;

/**
 * Created by Firman on 12/16/2018.
 */
public class DialogSiswaViewModel extends BaseViewModel {

    public static final String AUTO_GENERATE = "Auto Generate";

    private final DialogEditSupplierListener listener;
    private final Siswa siswa;
    private Siswa edited;

    private boolean showDelete = true;

    private final int mode;

    public DialogSiswaViewModel(Context context, Siswa siswa, int mode, DialogEditSupplierListener listener) {
        super(context);
        this.siswa = siswa;
        this.mode = mode;
        this.listener = listener;
        edited = new Siswa(siswa);
    }

    public boolean getShowDelete() {
        return showDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }

    public Siswa getSiswa() {
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
