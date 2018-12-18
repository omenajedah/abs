package info.androidhive.navigationdrawer.activity.siswa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import info.androidhive.navigationdrawer.activity.kelas.DialogKelasViewModel;
import info.androidhive.navigationdrawer.base.BaseViewModel;
import info.androidhive.navigationdrawer.databinding.DialogKelasBinding;
import info.androidhive.navigationdrawer.databinding.DialogSiswaBinding;
import info.androidhive.navigationdrawer.other.CustomDialog;
import info.androidhive.navigationdrawer.pojo.Kelas;
import info.androidhive.navigationdrawer.pojo.Siswa;

import static info.androidhive.navigationdrawer.activity.kelas.DialogKelasViewModel.AUTO_GENERATE;

/**
 * Created by Firman on 12/19/2018.
 */
public class SiswaViewModel extends BaseViewModel {

    private final KelasListener listener;
    public SiswaViewModel(Context context, KelasListener listener) {
        super(context);
        this.listener=listener;
    }

    public void onAdd(View v) {
        DialogSiswaBinding binding = DialogSiswaBinding.inflate(LayoutInflater.from(getContext()), null);
        CustomDialog dialog = CustomDialog.get(v.getContext())
                .title("Tambah Siswa")
                .addView(binding.getRoot())
                .cancelable(true);

        Siswa siswa = Siswa.add("", "", 0, null);

        DialogSiswaViewModel viewModel = new DialogSiswaViewModel(getContext(),
                siswa, 0, new DialogSiswaViewModel.DialogEditSupplierListener() {

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

        binding.setVm(viewModel);
        binding.executePendingBindings();

        dialog.show();
    }

    public interface KelasListener {

    }
}
