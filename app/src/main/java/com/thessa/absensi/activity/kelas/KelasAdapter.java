package com.thessa.absensi.activity.kelas;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import com.thessa.absensi.base.BaseRecyclerAdapter;
import com.thessa.absensi.base.BaseViewHolder;
import com.thessa.absensi.databinding.DialogKelasBinding;
import com.thessa.absensi.databinding.ListKelasBinding;
import com.thessa.absensi.other.CustomDialog;
import com.thessa.absensi.pojo.Kelas;

/**
 * Created by Firman on 12/19/2018.
 */
public class KelasAdapter extends BaseRecyclerAdapter<Kelas> {

    public KelasAdapter(List<Kelas> originalList) {
        super(originalList);
    }

    @Override
    public boolean onSearch(String filter, Kelas kelas) throws Exception {
        return false;
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListKelasBinding binding = ListKelasBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new KelasViewHolder(binding);
    }

    class KelasViewHolder extends BaseViewHolder<ListKelasBinding> {

        public KelasViewHolder(ListKelasBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            binding.setVm(new ListKelasViewModel(showList.get(position)));
            binding.executePendingBindings();

            binding.rootClickable.setOnClickListener(v -> {
                DialogKelasBinding kelasBinding = DialogKelasBinding.inflate(LayoutInflater.from(v.getContext()), null);
                CustomDialog dialog = CustomDialog.get(v.getContext())
                        .title("Ubah Kelas")
                        .addView(kelasBinding.getRoot())
                        .cancelable(true);

                DialogKelasViewModel viewModel = new DialogKelasViewModel(kelasBinding.getRoot().getContext(),
                        showList.get(position), 0, new DialogKelasViewModel.DialogEditSupplierListener() {

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

                kelasBinding.setVm(viewModel);
                kelasBinding.executePendingBindings();

                dialog.show();
            });

        }
    }
}
