package info.androidhive.navigationdrawer.activity.kelas;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;
import java.util.Map;

import info.androidhive.navigationdrawer.base.BaseViewModel;
import info.androidhive.navigationdrawer.pojo.Kelas;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
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
