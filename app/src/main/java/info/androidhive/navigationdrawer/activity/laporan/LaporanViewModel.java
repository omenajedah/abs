package info.androidhive.navigationdrawer.activity.laporan;

import android.content.Context;

import info.androidhive.navigationdrawer.base.BaseViewModel;

/**
 * Created by Firman on 12/19/2018.
 */
public class LaporanViewModel extends BaseViewModel {

    private final KelasListener listener;
    public LaporanViewModel(Context context, KelasListener listener) {
        super(context);
        this.listener=listener;
    }

    public interface KelasListener {

    }
}
