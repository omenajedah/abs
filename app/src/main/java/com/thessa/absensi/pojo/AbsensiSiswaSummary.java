package com.thessa.absensi.pojo;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.thessa.absensi.BR;

/**
 * Created by Firman on 1/6/2019.
 */
public class AbsensiSiswaSummary extends Siswa implements Observable {

    private int total_hari_bulan;
    private int total_hari_absen;
    private int total_absen_hadir;
    private int total_absen_izin;
    private int total_absen_sakit;
    private int total_absen_alpha;
    private int total_persentase;

    @Bindable
    public int getTotal_hari_bulan() {
        return total_hari_bulan;
    }

    public void setTotal_hari_bulan(int total_hari_bulan) {
        this.total_hari_bulan = total_hari_bulan;
        notifyPropertyChanged(BR.total_hari_bulan);
    }

    @Bindable
    public int getTotal_hari_absen() {
        return total_hari_absen;
    }

    public void setTotal_hari_absen(int total_hari_absen) {
        this.total_hari_absen = total_hari_absen;
        notifyPropertyChanged(BR.total_hari_absen);
    }

    @Bindable
    public int getTotal_absen_hadir() {
        return total_absen_hadir;
    }

    public void setTotal_absen_hadir(int total_absen_hadir) {
        this.total_absen_hadir = total_absen_hadir;
        notifyPropertyChanged(BR.total_absen_hadir);
    }

    @Bindable
    public int getTotal_absen_izin() {
        return total_absen_izin;
    }

    public void setTotal_absen_izin(int total_absen_izin) {
        this.total_absen_izin = total_absen_izin;
        notifyPropertyChanged(BR.total_absen_izin);
    }

    @Bindable
    public int getTotal_absen_sakit() {
        return total_absen_sakit;
    }

    public void setTotal_absen_sakit(int total_absen_sakit) {
        this.total_absen_sakit = total_absen_sakit;
        notifyPropertyChanged(BR.total_absen_sakit);
    }

    @Bindable
    public int getTotal_absen_alpha() {
        return total_absen_alpha;
    }

    public void setTotal_absen_alpha(int total_absen_alpha) {
        this.total_absen_alpha = total_absen_alpha;
        notifyPropertyChanged(BR.total_absen_alpha);
    }

    @Bindable
    public int getTotal_persentase() {
        return total_persentase;
    }

    public void setTotal_persentase(int total_persentase) {
        this.total_persentase = total_persentase;
        notifyPropertyChanged(BR.total_persentase);
    }

    
}
