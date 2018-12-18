package info.androidhive.navigationdrawer.activity.kelas;

import info.androidhive.navigationdrawer.pojo.Kelas;

/**
 * Created by Firman on 12/19/2018.
 */
public class ListKelasViewModel {

    private final Kelas kelas;

    public ListKelasViewModel(Kelas kelas) {
        this.kelas = kelas;
    }

    public Kelas getKelas() {
        return kelas;
    }
}
