package info.androidhive.navigationdrawer.activity.siswa;

import android.databinding.ObservableField;
import android.text.Spannable;

import info.androidhive.navigationdrawer.pojo.Siswa;

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
