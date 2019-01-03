package info.androidhive.navigationdrawer.activity.absensi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.activity.siswa.DialogSiswaViewModel;
import info.androidhive.navigationdrawer.base.BaseViewModel;
import info.androidhive.navigationdrawer.databinding.DialogAbsensiSiswaBinding;
import info.androidhive.navigationdrawer.databinding.DialogSiswaBinding;
import info.androidhive.navigationdrawer.other.ConstantNetwork;
import info.androidhive.navigationdrawer.other.CustomDialog;
import info.androidhive.navigationdrawer.pojo.AbsensiSiswa;
import info.androidhive.navigationdrawer.pojo.AbsensiSiswa;
import info.androidhive.navigationdrawer.pojo.Kelas;
import info.androidhive.navigationdrawer.pojo.Siswa;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/19/2018.
 */
public class AbsensiViewModel extends BaseViewModel {

    private final AbsensiListener listener;

    public AbsensiViewModel(Context context, AbsensiListener listener) {
        super(context);
        this.listener = listener;
    }

    public void onAdd(View v) {
        DialogAbsensiSiswaBinding siswaBinding = DialogAbsensiSiswaBinding.inflate(LayoutInflater.from(getContext()), null);
        CustomDialog dialog = CustomDialog.get(v.getContext())
                .title("Absen Siswa")
                .addView(siswaBinding.getRoot())
                .cancelable(true);

        AbsensiSiswa siswa = AbsensiSiswa.add("", "", 0, 0, null);

        DialogAbsensiViewModel viewModel = new DialogAbsensiViewModel(getContext(),
                siswa, 0, new DialogAbsensiViewModel.DialogEditSupplierListener() {

            @Override
            public void onDelete() {
                onBatal();
            }

            @Override
            public void onSimpanBerhasil() {
                onBatal();
                Toast.makeText(v.getContext(), "Sukses menyimpan.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGagalSimpan() {
                Toast.makeText(v.getContext(), "Gagal menyimpan.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBatal() {
                refresh();
                dialog.dismiss();
            }
        });

        siswaBinding.setVm(viewModel);
        siswaBinding.executePendingBindings();

        dialog.show();
    }

    public void refresh() {
        getCompositeDisposable().clear();
        getCompositeDisposable().add(
                getAbsensiSiswa().subscribe(AbsensiSiswa -> {
                    listener.onRefresh(AbsensiSiswa);
                }, throwable -> {
                    listener.onError(throwable);
                })
        );

    }

    private Observable<List<AbsensiSiswa>> getAbsensiSiswa() {

        return Rx2AndroidNetworking.post(ConstantNetwork.CHECKSISWA)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    List<AbsensiSiswa> siswas = new ArrayList<>();

                    JSONArray array = jsonObject.getJSONArray("DataRow");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ITEM = array.getJSONObject(i);
                        AbsensiSiswa siswa = new AbsensiSiswa();
                        siswa.setNisn(ITEM.getString("nisn"));
                        siswa.setNama_siswa(ITEM.getString("nama_siswa"));
                        siswa.setJenis_kelamin(ITEM.getInt("jenis_kelamin"));
                        siswa.setNisn(ITEM.getString("nisn"));
                        siswa.setAbsen_siswa(ITEM.getInt("tipe_absen"));

                        Kelas kls = new Kelas();
                        kls.setId_kelas(ITEM.getString("id_kelas"));
                        kls.setNama_kelas(ITEM.getString("nama_kelas"));
                        siswa.setKelas(kls);

                        siswas.add(siswa);
                    }

                    return siswas;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface AbsensiListener {
        void onRefresh(List<AbsensiSiswa> absen);

        void onError(Throwable throwable);

    }
}
