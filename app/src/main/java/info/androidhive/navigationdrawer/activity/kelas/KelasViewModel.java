package info.androidhive.navigationdrawer.activity.kelas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.base.BaseViewModel;
import info.androidhive.navigationdrawer.databinding.DialogKelasBinding;
import info.androidhive.navigationdrawer.other.ConstantNetwork;
import info.androidhive.navigationdrawer.other.CustomDialog;
import info.androidhive.navigationdrawer.pojo.Kelas;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static info.androidhive.navigationdrawer.activity.kelas.DialogKelasViewModel.AUTO_GENERATE;

/**
 * Created by Firman on 12/19/2018.
 */
public class KelasViewModel extends BaseViewModel {

    private final KelasListener listener;
    public KelasViewModel(Context context, KelasListener listener) {
        super(context);
        this.listener=listener;
    }

    public void onAdd(View v) {
        DialogKelasBinding binding = DialogKelasBinding.inflate(LayoutInflater.from(getContext()), null);
        CustomDialog dialog = CustomDialog.get(v.getContext())
                .title("Tambah Kelas")
                .addView(binding.getRoot())
                .cancelable(true);

        Kelas kelas = Kelas.add(AUTO_GENERATE, "");

        DialogKelasViewModel viewModel = new DialogKelasViewModel(getContext(),
                kelas, 0, new DialogKelasViewModel.DialogEditSupplierListener() {

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

    public void  refresh(){
        getCompositeDisposable().clear();
        getCompositeDisposable().add(
                getKelas().subscribe(kelas -> {
                    listener.onRefresh(kelas);
                },throwable -> {
                    listener.onError(throwable);
                })
        );

    }

    private Observable<List<Kelas>> getKelas() {

        return Rx2AndroidNetworking.post(ConstantNetwork.KELAS)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    List<Kelas> kelas=new ArrayList<>();

                    JSONArray array=jsonObject.getJSONArray("DataRow");

                    for (int i = 0; i<array.length();i++) {
                        JSONObject ITEM = array.getJSONObject(i);
                        Kelas kls=new Kelas();
                        kls.setId_kelas(ITEM.getString("id_kelas"));
                        kls.setNama_kelas(ITEM.getString("nama_kelas"));
                        kelas.add(kls);
                    }

                    return kelas;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface KelasListener {
        void onRefresh(List<Kelas> kelas);
        void onError (Throwable throwable);
    }
}
