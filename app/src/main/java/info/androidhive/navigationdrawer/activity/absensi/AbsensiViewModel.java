package info.androidhive.navigationdrawer.activity.absensi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import info.androidhive.navigationdrawer.activity.siswa.DialogSiswaViewModel;
import info.androidhive.navigationdrawer.base.BaseViewModel;
import info.androidhive.navigationdrawer.databinding.DialogAbsensiSiswaBinding;
import info.androidhive.navigationdrawer.databinding.DialogSiswaBinding;
import info.androidhive.navigationdrawer.other.CustomDialog;
import info.androidhive.navigationdrawer.pojo.AbsensiSiswa;
import info.androidhive.navigationdrawer.pojo.Siswa;

/**
 * Created by Firman on 12/19/2018.
 */
public class AbsensiViewModel extends BaseViewModel {

    private final AbsensiListener listener;
    public AbsensiViewModel(Context context, AbsensiListener listener) {
        super(context);
        this.listener=listener;
    }

    public void onAdd(View v) {
        DialogAbsensiSiswaBinding siswaBinding = DialogAbsensiSiswaBinding.inflate(LayoutInflater.from(getContext()), null);
        CustomDialog dialog = CustomDialog.get(v.getContext())
                .title("Tambah Kelas")
                .addView(siswaBinding.getRoot())
                .cancelable(true);

        AbsensiSiswa siswa = AbsensiSiswa.add("", "", 0, 0, null);

        DialogAbsensiViewModel viewModel = new DialogAbsensiViewModel(getContext(),
                siswa, 0, new DialogAbsensiViewModel.DialogEditSupplierListener() {

            @Override
            public void onDelete() {
                dialog.dismiss();
            }

            @Override
            public void onSimpanBerhasil() {
                dialog.dismiss();
                Toast.makeText(v.getContext(), "Sukses menyimpan.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGagalSimpan() {
                Toast.makeText(v.getContext(), "Gagal menyimpan.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBatal() {
                dialog.dismiss();
            }
        });

        siswaBinding.setVm(viewModel);
        siswaBinding.executePendingBindings();

        dialog.show();
    }

    public interface AbsensiListener {

    }
}
