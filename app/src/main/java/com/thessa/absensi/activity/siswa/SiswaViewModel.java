package com.thessa.absensi.activity.siswa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.thessa.absensi.base.BaseViewModel;
import com.thessa.absensi.databinding.DialogSiswaBinding;
import com.thessa.absensi.other.ConstantNetwork;
import com.thessa.absensi.other.CustomDialog;
import com.thessa.absensi.pojo.Kelas;
import com.thessa.absensi.pojo.Siswa;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/19/2018.
 */
public class SiswaViewModel extends BaseViewModel {

    private final SiswaListener listener;

    public SiswaViewModel(Context context, SiswaListener listener) {
        super(context);
        this.listener = listener;
    }

    public void onAdd(View v) {
        DialogSiswaBinding binding = DialogSiswaBinding.inflate(LayoutInflater.from(getContext()), null);
        CustomDialog dialog = CustomDialog.get(v.getContext())
                .title("Tambah Siswa")
                .addView(binding.getRoot())
                .cancelable(true);

        Siswa siswa = Siswa.add("", "", 0, null);

        DialogSiswaViewModel viewModel = new DialogSiswaViewModel(getContext(),
                siswa, 0, new DialogSiswaViewModel.DialogEditSupplierListener() {

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

        binding.setVm(viewModel);
        binding.executePendingBindings();

        dialog.show();
    }

    public void refresh() {
        getCompositeDisposable().clear();
        getCompositeDisposable().add(
                getSiswa().subscribe(Siswa -> {
                    listener.onRefresh(Siswa);
                }, throwable -> {
                    listener.onError(throwable);
                })
        );

    }

    private Observable<List<Siswa>> getSiswa() {

        return Rx2AndroidNetworking.post(ConstantNetwork.SISWA)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    List<Siswa> Siswa = new ArrayList<>();

                    JSONArray array = jsonObject.getJSONArray("DataRow");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ITEM = array.getJSONObject(i);
                        Siswa siswa = new Siswa();
                        siswa.setNisn(ITEM.getString("nisn"));
                        siswa.setNama_siswa(ITEM.getString("nama_siswa"));
                        siswa.setJenis_kelamin(ITEM.getInt("jenis_kelamin"));
                        siswa.setNisn(ITEM.getString("nisn"));

                        Kelas kls = new Kelas();
                        kls.setId_kelas(ITEM.getString("id_kelas"));
                        kls.setNama_kelas(ITEM.getString("nama_kelas"));
                        siswa.setKelas(kls);
                        Siswa.add(siswa);
                    }

                    return Siswa;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public interface SiswaListener {
        void onRefresh(List<Siswa> siswa);

        void onError(Throwable throwable);

    }
}
