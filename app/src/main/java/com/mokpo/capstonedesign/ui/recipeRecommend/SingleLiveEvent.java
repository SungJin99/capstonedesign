package com.mokpo.capstonedesign.ui.recipeRecommend;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;
import android.util.Log;

public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private final AtomicBoolean pending = new AtomicBoolean(false);
    private static final String TAG = "SingleLiveEvent";

    @MainThread
    public void observe(LifecycleOwner owner, final Observer<? super T> observer) {

        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }

        // Observe the internal MutableLiveData
        super.observe(owner, t -> {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });
    }

    @MainThread
    public void setValue(@Nullable T t) {
        pending.set(true);
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        setValue(null);
    }
}

