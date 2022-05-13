package com.module.common.network;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 针对每一个Observer，都设置一个对应的AtomicBoolean值，LiveData执行setValue时置为true，执行onChanged后置为false，确保一个value只分发一次。
 *
 * @author BD
 * @date 2022/5/13 15:21
 */
public class SingleLiveData<T> extends MutableLiveData<T> {

    private final HashMap<Observer<? super T>, AtomicBoolean> pendingMap = new HashMap<>();

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        Lifecycle lifecycle = owner.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        pendingMap.put(observer, new AtomicBoolean(false));
        lifecycle.addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_DESTROY) {
                pendingMap.remove(observer);
            }
        });
        super.observe(owner, t -> {
            AtomicBoolean pending = pendingMap.get(observer);
            if (pending != null && pending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });
    }

    @MainThread
    @Override
    public void observeForever(@NonNull Observer<? super T> observer) {
        pendingMap.put(observer, new AtomicBoolean(false));
        super.observeForever(observer);
    }

    @MainThread
    @Override
    public void removeObserver(@NonNull Observer<? super T> observer) {
        pendingMap.remove(observer);
        super.removeObserver(observer);
    }

    @MainThread
    @Override
    public void removeObservers(@NonNull LifecycleOwner owner) {
        pendingMap.clear();
        super.removeObservers(owner);
    }

    @MainThread
    @Override
    public void setValue(T value) {
        for (AtomicBoolean v : pendingMap.values()) {
            v.set(true);
        }
        super.setValue(value);
    }
}
