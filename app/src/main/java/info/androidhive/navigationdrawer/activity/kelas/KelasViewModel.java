package info.androidhive.navigationdrawer.activity.kelas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import info.androidhive.navigationdrawer.base.BaseViewModel;
import info.androidhive.navigationdrawer.databinding.DialogKelasBinding;
import info.androidhive.navigationdrawer.other.CustomDialog;
import info.androidhive.navigationdrawer.pojo.Kelas;

import static info.androidhive.navigationdrawer.activity.kelas.DialogKelasViewModel.AUTO_GENERATE;

/**
 * Created by Firman on 12/19/2018.
 */
public class KelasViewModel extends BaseViewModel {

    private final KelasListener listener;
    public KelasViewModel(Context context, KelasListener listener) {
        super(context);
        this.listener=listener;
    }

    public void onAdd(View v) {
        DialogKelasBinding binding = DialogKelasBinding.inflate(LayoutInflater.from(getContext()), null);
        CustomDialog dialog = CustomDialog.get(v.getContext())
                .title("Tambah Kelas")
                .addView(binding.getRoot())
                .cancelable(true);

        Kelas kelas = Kelas.add(AUTO_GENERATE, "");

        DialogKelasViewModel viewModel = new DialogKelasViewModel(getContext(),
                kelas, 0, new DialogKelasViewModel.DialogEditSupplierListener() {

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
