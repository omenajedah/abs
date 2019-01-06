package com.thessa.absensi.activity.laporan;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thessa.absensi.activity.laporandetil.LaporanDetailActivity;
import com.thessa.absensi.activity.siswa.DialogSiswaViewModel;
import com.thessa.absensi.activity.siswa.ListSiswaViewModel;
import com.thessa.absensi.base.BaseRecyclerAdapter;
import com.thessa.absensi.base.BaseViewHolder;
import com.thessa.absensi.databinding.DialogSiswaBinding;
import com.thessa.absensi.databinding.ListSiswaBinding;
import com.thessa.absensi.other.CustomDialog;
import com.thessa.absensi.pojo.Siswa;

import java.util.List;

/**
 * Created by Firman on 12/19/2018.
 */
public class LaporanSiswaAdapter extends BaseRecyclerAdapter<Siswa> {

    public LaporanSiswaAdapter(List<Siswa> originalList) {
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

        private ListSiswaViewModel viewModel;

        public KelasViewHolder(ListSiswaBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            viewModel = new ListSiswaViewModel(showList.get(position));
            binding.setVm(viewModel);
            binding.executePendingBindings();

            binding.getRoot().setOnClickListener(v -> {
                LaporanDetailActivity.startThisActivity(binding.getRoot().getContext(), viewModel.getSiswa());
            });
        }
    }
}
