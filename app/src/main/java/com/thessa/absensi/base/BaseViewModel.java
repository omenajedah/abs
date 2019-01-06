package com.thessa.absensi.base;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.content.Context;



import java.lang.ref.WeakReference;

import com.thessa.absensi.other.SessionHandler;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {

    protected ProgressDialog progressDialog;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<Context> mContext;
    private final SessionHandler sessionHandler;

    public BaseViewModel(Context context) {
        this.mContext = new WeakReference<>(context);
        this.progressDialog = new ProgressDialog(context);
        this.mCompositeDisposable = new CompositeDisposable();
        sessionHandler = new SessionHandler(context);
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }


    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    protected Context getContext() {
        return this.mContext.get();
    }

    public SessionHandler getSessionHandler() {
        return sessionHandler;
    }

}
