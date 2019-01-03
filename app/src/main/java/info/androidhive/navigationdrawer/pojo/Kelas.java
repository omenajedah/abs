package info.androidhive.navigationdrawer.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import info.androidhive.navigationdrawer.BR;

/**
 * Created by Firman on 12/19/2018.
 */
public class Kelas extends BaseObservable {

    private String id_kelas;
    private String nama_kelas;

    public static Kelas add(String id_kelas, String nama_kelas) {
        Kelas kelas = new Kelas();
        kelas.setId_kelas(id_kelas);
        kelas.setNama_kelas(nama_kelas);
        return kelas;
    }

    public Kelas() {}

    public Kelas(Kelas ref) {
        setNama_kelas(ref.nama_kelas);
        setId_kelas(ref.id_kelas);
    }

    @Bindable
    public String getId_kelas() {
        return id_kelas;
    }

    @Bindable
    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
        notifyPropertyChanged(BR.id_kelas);
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
        notifyPropertyChanged(BR.nama_kelas);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Kelas))
            return false;
        Kelas kelas = (Kelas) obj;

        return ((Kelas) obj).getId_kelas().equals(id_kelas);
    }
}
