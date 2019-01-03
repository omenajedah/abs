package info.androidhive.navigationdrawer.activity.siswa;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import info.androidhive.navigationdrawer.base.BaseRecyclerAdapter;
import info.androidhive.navigationdrawer.base.BaseViewHolder;
import info.androidhive.navigationdrawer.databinding.DialogSiswaBinding;
import info.androidhive.navigationdrawer.databinding.ListSiswaBinding;
import info.androidhive.navigationdrawer.other.CustomDialog;
import info.androidhive.navigationdrawer.pojo.Siswa;

/**
 * Created by Firman on 12/19/2018.
 */
public class SiswaAdapter extends BaseRecyclerAdapter<Siswa> {

    public SiswaAdapter(List<Siswa> originalList) {
        super(originalList);
    }

    @Override
    public boolean onSearch(String filter, Siswa kelas) throws Exception {
        return false;
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListSiswaBinding binding = ListSiswaBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new KelasViewHolder(binding);
    }

    class KelasViewHolder extends BaseViewHolder<ListSiswaBinding> {

        public KelasViewHolder(ListSiswaBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            binding.setVm(new ListSiswaViewModel(showList.get(position)));
            binding.executePendingBindings();

            binding.getRoot().setOnClickListener(v -> {
                DialogSiswaBinding siswaBinding = DialogSiswaBinding.inflate(LayoutInflater.from(v.getContext()), null);
                CustomDialog dialog = CustomDialog.get(v.getContext())
                        .title("Ubah Siswa")
                        .addView(siswaBinding.getRoot())
                        .cancelable(true);


                DialogSiswaViewModel viewModel = new DialogSiswaViewModel(v.getContext(),
                        showList.get(position), 0, new DialogSiswaViewModel.DialogEditSupplierListener() {

                    @Override
                    public void onDelete() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onSimpanBerhasil() {
                        if (baseRecyclerOnSuccessListener != null)
                            baseRecyclerOnSuccessListener.onSuccess();
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
