package com.thessa.absensi.activity.absensi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import com.thessa.absensi.base.BaseRecyclerAdapter;
import com.thessa.absensi.base.BaseViewHolder;
import com.thessa.absensi.databinding.DialogAbsensiSiswaBinding;
import com.thessa.absensi.databinding.ListAbsensiBinding;
import com.thessa.absensi.other.CustomDialog;
import com.thessa.absensi.pojo.AbsensiSiswa;

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

        private ListAbsensiViewModel vm;

        public AbsenViewHolder(ListAbsensiBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            vm = new ListAbsensiViewModel(showList.get(position), binding.getRoot().getContext());
            binding.setVm(vm);
            binding.executePendingBindings();

//            if (vm.getSiswa().getAbsen_siswa() == -1)
            binding.getRoot().setOnClickListener(v -> {
                DialogAbsensiSiswaBinding siswaBinding = DialogAbsensiSiswaBinding.inflate(LayoutInflater.from(v.getContext()), null);
                CustomDialog dialog = CustomDialog.get(v.getContext())
                        .title("Absen Siswa")
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
                        if (baseRecyclerOnSuccessListener != null)
                            baseRecyclerOnSuccessListener.onSuccess();
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

                if (vm.getSiswa().getAbsen_siswa()==-1)
                    viewModel.setReadOnly(false);

                    siswaBinding.setVm(viewModel);
                siswaBinding.executePendingBindings();

                dialog.show();
            });
        }
    }
}
