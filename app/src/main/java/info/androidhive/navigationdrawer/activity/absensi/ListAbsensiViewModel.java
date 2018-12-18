package info.androidhive.navigationdrawer.activity.absensi;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.Spannable;
import android.text.Spanned;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.other.Utils;
import info.androidhive.navigationdrawer.pojo.AbsensiSiswa;
import info.androidhive.navigationdrawer.pojo.Siswa;

import static info.androidhive.navigationdrawer.pojo.AbsensiSiswa.STATUS_ALPHA;
import static info.androidhive.navigationdrawer.pojo.AbsensiSiswa.STATUS_BELUM_ABSEN;
import static info.androidhive.navigationdrawer.pojo.AbsensiSiswa.STATUS_HADIR;
import static info.androidhive.navigationdrawer.pojo.AbsensiSiswa.STATUS_IZIN;
import static info.androidhive.navigationdrawer.pojo.AbsensiSiswa.STATUS_SAKIT;

/**
 * Created by Firman on 12/19/2018.
 */
public class ListAbsensiViewModel {

    private final AbsensiSiswa siswa;
    private ObservableField<Spanned> absenSiswa = new ObservableField<>();
    private final Context context;

    public ListAbsensiViewModel(AbsensiSiswa siswa, Context context) {
        this.siswa = siswa;
        this.context=context;

        checkAbsen();
    }

    private void checkAbsen() {
        switch (siswa.getAbsen_siswa()) {
            case STATUS_BELUM_ABSEN:
                absenSiswa.set(Utils.fromHTML(context.getString(R.string.absen_siswa)));
                break;
            case STATUS_HADIR:
                absenSiswa.set(Utils.fromHTML(context.getString(R.string.status_siswa_hadir)));
                break;
            case STATUS_IZIN:
                absenSiswa.set(Utils.fromHTML(context.getString(R.string.status_siswa_izin)));
                break;
            case STATUS_SAKIT:
                absenSiswa.set(Utils.fromHTML(context.getString(R.string.status_siswa_sakit)));
                break;
            case STATUS_ALPHA:
                absenSiswa.set(Utils.fromHTML(context.getString(R.string.status_siswa_alpha)));
                break;
        }
    }

    public Siswa getSiswa() {
        return siswa;
    }

    public ObservableField<Spanned> getAbsenSiswa() {
        return absenSiswa;
    }
}
