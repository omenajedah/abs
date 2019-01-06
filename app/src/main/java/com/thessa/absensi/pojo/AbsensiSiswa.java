package com.thessa.absensi.pojo;

import android.databinding.Bindable;

import com.thessa.absensi.BR;

/**
 * Created by Firman on 12/19/2018.
 */
public class AbsensiSiswa extends Siswa {

    public static final int STATUS_BELUM_ABSEN = -1;
    public static final int STATUS_HADIR = 1;
    public static final int STATUS_IZIN = 2;
    public static final int STATUS_SAKIT = 3;
    public static final int STATUS_ALPHA = 4;


    private int absen_siswa;

    public static AbsensiSiswa add(String nisn, String nama_siswa, int jenis_kelamin, int absen_siswa, Kelas kelas) {
        AbsensiSiswa siswa = new AbsensiSiswa();
        siswa.setNisn(nisn);
        siswa.setNama_siswa(nama_siswa);
        siswa.setJenis_kelamin(jenis_kelamin);
        siswa.setAbsen_siswa(absen_siswa);
        siswa.setKelas(kelas);

        return siswa;
    }

    public AbsensiSiswa() {}

    public AbsensiSiswa(AbsensiSiswa siswa) {
        setNisn(siswa.getNisn());
        setNama_siswa(siswa.getNama_siswa());
        setJenis_kelamin(siswa.getJenis_kelamin());
        setKelas(siswa.getKelas());
        setAbsen_siswa(siswa.absen_siswa);
    }

    @Bindable
    public int getAbsen_siswa() {
        return absen_siswa;
    }

    public void setAbsen_siswa(int absen_siswa) {
        this.absen_siswa = absen_siswa;
        notifyPropertyChanged(BR.absen_siswa);
    }
}
