package info.androidhive.navigationdrawer.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import info.androidhive.navigationdrawer.BR;

/**
 * Created by Firman on 12/19/2018.
 */
public class Siswa extends BaseObservable {

    private String nisn;
    private String nama_siswa;
    private int jenis_kelamin;
    private Kelas kelas;

    public static Siswa add(String nisn, String nama_siswa, int jenis_kelamin, Kelas kelas) {
        Siswa siswa = new Siswa();
        siswa.setNisn(nisn);
        siswa.setNama_siswa(nama_siswa);
        siswa.setJenis_kelamin(jenis_kelamin);
        siswa.setKelas(kelas);

        return siswa;
    }

    public Siswa() {}

    public Siswa(Siswa siswa) {
        setNisn(siswa.nisn);
        setNama_siswa(siswa.nama_siswa);
        setJenis_kelamin(siswa.jenis_kelamin);
        setKelas(siswa.kelas);
    }

    @Bindable
    public String getNisn() {
        return nisn;
    }

    @Bindable
    public String getNama_siswa() {
        return nama_siswa;
    }

    @Bindable
    public int getJenis_kelamin() {
        return jenis_kelamin;
    }

    @Bindable
    public Kelas getKelas() {
        return kelas;
    }


    public void setNisn(String nisn) {
        this.nisn = nisn;
        notifyPropertyChanged(BR.nisn);
    }

    public void setNama_siswa(String nama_siswa) {
        this.nama_siswa = nama_siswa;
        notifyPropertyChanged(BR.nama_siswa);
    }

    public void setJenis_kelamin(int jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
        notifyPropertyChanged(BR.jenis_kelamin);
    }

    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
        notifyPropertyChanged(BR.id_kelas);
    }
}
