package com.thessa.absensi.activity.absensi;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.thessa.absensi.base.BaseViewModel;
import com.thessa.absensi.other.ConstantNetwork;
import com.thessa.absensi.pojo.AbsensiSiswa;
import com.thessa.absensi.pojo.Kelas;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/19/2018.
 */
public class AbsensiViewModel extends BaseViewModel {

    private final AbsensiListener listener;
    private final ArrayAdapter<Kelas> kelasAdapter;

    private List<Kelas> kelasList = new ArrayList<>();
    private Kelas kelas;

    public AbsensiViewModel(Context context, AbsensiListener listener) {
        super(context);
        this.listener = listener;
        kelasAdapter = new ArrayAdapter<Kelas>(context, android.R.layout.simple_list_item_1, kelasList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView==null)
                    convertView= LayoutInflater.from(getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);

                ((TextView)convertView.findViewById(android.R.id.text1)).setText(getItem(position).getNama_kelas());

                return convertView;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView==null)
                    convertView= LayoutInflater.from(getContext())
                            .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

                ((TextView)convertView.findViewById(android.R.id.text1)).setText(getItem(position).getNama_kelas());

                return convertView;
            }
        };
    }

    public ArrayAdapter<Kelas> getKelasAdapter() {
        return kelasAdapter;
    }

    public void onKelasChanged(AdapterView<?> parent, View view, int position, long id)  {
        kelas = kelasList.get(position);
        refresh();
    }

    public void refresh() {
        getCompositeDisposable().clear();
        
        if (kelasList.size() == 0)
            getCompositeDisposable().add(
                    getKelasList().subscribe(AbsensiSiswa -> {
                        listener.onRefresh(AbsensiSiswa);
                    }, throwable -> {
                        listener.onError(throwable);
                    })
            );
        else 
            getCompositeDisposable().add(
                getAbsensiSiswa().subscribe(AbsensiSiswa -> {
                    listener.onRefresh(AbsensiSiswa);
                }, throwable -> {
                    listener.onError(throwable);
                })
        );

    }

    private Observable<List<AbsensiSiswa>> getKelasList() {
        return Rx2AndroidNetworking.post(ConstantNetwork.KELAS)
                .build()
                .getJSONObjectObservable()
                .flatMap((Function<JSONObject, ObservableSource<List<AbsensiSiswa>>>) jsonObject -> {
                    JSONArray array=jsonObject.getJSONArray("DataRow");

                    for (int i = 0; i<array.length();i++) {
                        JSONObject ITEM = array.getJSONObject(i);
                        Kelas kls=new Kelas();
                        kls.setId_kelas(ITEM.getString("id_kelas"));
                        kls.setNama_kelas(ITEM.getString("nama_kelas"));
                        kelasList.add(kls);

                        if (kelas == null)
                            kelas = kls;
                    }
                    new Handler(Looper.getMainLooper()).post(kelasAdapter::notifyDataSetChanged);
                    return getAbsensiSiswa();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Observable<List<AbsensiSiswa>> getAbsensiSiswa() {

        return Rx2AndroidNetworking.post(ConstantNetwork.CHECKSISWA)
                .addBodyParameter("id_kelas", kelas.getId_kelas())
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
