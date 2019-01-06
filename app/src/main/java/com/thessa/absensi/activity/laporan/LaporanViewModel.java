package com.thessa.absensi.activity.laporan;

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
import com.thessa.absensi.base.BaseViewModel;
import com.thessa.absensi.other.ConstantNetwork;
import com.thessa.absensi.pojo.AbsensiSiswa;
import com.thessa.absensi.pojo.Kelas;
import com.thessa.absensi.pojo.Siswa;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/19/2018.
 */
public class LaporanViewModel extends BaseViewModel {

    private final KelasListener listener;
    private final ArrayAdapter<Kelas> kelasAdapter;

    private List<Kelas> kelasList = new ArrayList<>();
    private Kelas kelas;

    public LaporanViewModel(Context context, KelasListener listener) {
        super(context);
        this.listener=listener;
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
                    getSiswa().subscribe(AbsensiSiswa -> {
                        listener.onRefresh(AbsensiSiswa);
                    }, throwable -> {
                        listener.onError(throwable);
                    })
            );

    }

    private Observable<List<Siswa>> getKelasList() {
        return Rx2AndroidNetworking.post(ConstantNetwork.KELAS)
                .build()
                .getJSONObjectObservable()
                .flatMap((Function<JSONObject, ObservableSource<List<Siswa>>>) jsonObject -> {
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
                    return getSiswa();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<Siswa>> getSiswa() {

        return Rx2AndroidNetworking.post(ConstantNetwork.SISWA)
                .addBodyParameter("id_kelas", kelas.getId_kelas())
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

    public interface KelasListener {
        void onRefresh(List<Siswa> siswas);
        void onError(Throwable error);
    }
}
