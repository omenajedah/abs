package info.androidhive.navigationdrawer.activity.absensi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import info.androidhive.navigationdrawer.base.BaseRecyclerAdapter;
import info.androidhive.navigationdrawer.base.BaseViewHolder;
import info.androidhive.navigationdrawer.databinding.DialogAbsensiSiswaBinding;
import info.androidhive.navigationdrawer.databinding.ListAbsensiBinding;
import info.androidhive.navigationdrawer.other.CustomDialog;
import info.androidhive.navigationdrawer.pojo.AbsensiSiswa;

/**
 * Created by Firman on 12/19/2018.
 */
public class AbsensiAdapter extends BaseRecyclerAdapter<AbsensiSiswa> {

    public AbsensiAdapter(List<AbsensiSiswa> originalList) {
        super(originalList);
    }

    @Override
    public boolean onSearch(String filter, AbsensiSiswa absensiSiswa) throws Exception {
        return false;
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListAbsensiBinding binding = ListAbsensiBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        return new AbsenViewHolder(binding);
    }


    class AbsenViewHolder extends BaseViewHolder<ListAbsensiBinding> {

        public AbsenViewHolder(ListAbsensiBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            binding.setVm(new ListAbsensiViewModel(showList.get(position), binding.getRoot().getContext()));
            binding.executePendingBindings();

            binding.getRoot().setOnClickListener(v -> {
                DialogAbsensiSiswaBinding siswaBinding = DialogAbsensiSiswaBinding.inflate(LayoutInflater.from(v.getContext()), null);
                CustomDialog dialog = CustomDialog.get(v.getContext())
                        .title("Tambah Kelas")
                        .addView(siswaBinding.getRoot())
                        .cancelable(true);


                DialogAbsensiViewModel viewModel = new DialogAbsensiViewModel(v.getContext(),
                        showList.get(position), 0, new DialogAbsensiViewModel.DialogEditSupplierListener() {

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
            });
        }
    }
}
