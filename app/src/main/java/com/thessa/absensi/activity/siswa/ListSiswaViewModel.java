package com.thessa.absensi.activity.siswa;

import com.thessa.absensi.pojo.Siswa;

/**
 * Created by Firman on 12/19/2018.
 */
public class ListSiswaViewModel {

    private final Siswa siswa;

    public ListSiswaViewModel(Siswa siswa) {
        this.siswa = siswa;
    }

    public Siswa getSiswa() {
        return siswa;
    }
}
