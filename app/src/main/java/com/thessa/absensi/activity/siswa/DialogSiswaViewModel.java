package com.thessa.absensi.activity.siswa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thessa.absensi.R;
import com.thessa.absensi.base.BaseViewModel;
import com.thessa.absensi.other.ConstantNetwork;
import com.thessa.absensi.pojo.Kelas;
import com.thessa.absensi.pojo.Siswa;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/16/2018.
 */
public class DialogSiswaViewModel extends BaseViewModel {

    public static final String AUTO_GENERATE = "Auto Generate";

    private final DialogEditSupplierListener listener;
    private final Siswa siswa;
    private Siswa edited;

    private List<Kelas> kelas=new ArrayList<>();
    private ArrayAdapter<Kelas> adapter;


    private boolean showDelete = true;

    private final int mode;

    public DialogSiswaViewModel(Context context, Siswa siswa, int mode, DialogEditSupplierListener listener) {
        super(context);
        this.siswa = siswa;
        this.mode = mode;
        this.listener = listener;
        edited = new Siswa(siswa);

        adapter= new ArrayAdapter<Kelas>(context, android.R.layout.simple_list_item_1, kelas) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView==null)
                    convertView= LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

                ((TextView)convertView.findViewById(android.R.id.text1)).setText(getItem(position).getNama_kelas());

                return convertView;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView==null)
                    convertView= LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

                ((TextView)convertView.findViewById(android.R.id.text1)).setText(getItem(position).getNama_kelas());

                return convertView;
            }
        };

        getCompositeDisposable().add(
                downloadKelas().subscribe(kelas -> {
                    this.kelas.addAll(kelas);
                    adapter.notifyDataSetChanged();
                },throwable -> {
                    throwable.printStackTrace();
                })
        );
    }

    public ArrayAdapter<Kelas> getAdapter() {
        return adapter;
    }

    public List<Kelas> getKelas() {
        return kelas;
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

    public void afterNisnChanged(Editable nisn) {
        edited.setNisn(nisn.toString());
    }

    public void onSimpanClick(View v) {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(
                tambahSiswa(false)
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


    public void onKelasChanged(AdapterView<?> parent, View view, int position, long id)  {
        edited.setKelas(kelas.get(position));
    }

    public void onGenderChange(RadioGroup group, int checkedId) {
        if (checkedId== R.id.laki_laki)
            edited.setJenis_kelamin(1);

        else
            edited.setJenis_kelamin(0);
    }


    private Observable<List<Kelas>> downloadKelas() {

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

    private Observable<Boolean> tambahSiswa(boolean delete) {

        Map<String, String> param = new HashMap<>();
        param.put("nama_siswa", edited.getNama_siswa());
        param.put("jenis_kelamin", String.valueOf(edited.getJenis_kelamin()));
        param.put("id_kelas", edited.getKelas().getId_kelas());


        if (!siswa.getNisn().equals(AUTO_GENERATE)) {
            param.put("nisn", edited.getNisn());
        }

        if (delete)
            param.put("delete", String.valueOf(1));


        return Rx2AndroidNetworking.post(ConstantNetwork.UBAH_SISWA)
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
