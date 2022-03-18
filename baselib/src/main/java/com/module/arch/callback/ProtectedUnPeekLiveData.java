package com.module.arch.callback;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 拷贝 —> https://github.com/KunMinX/UnPeek-LiveData 里的源码
 *
 * @author bd
 * @date 2021/10/8
 */
public class ProtectedUnPeekLiveData<T> extends LiveData<T> {

    private final AtomicInteger mCurrentVersion = new AtomicInteger(-1);
    protected boolean isAllowNullValue;

    public ProtectedUnPeekLiveData() {
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, this.createObserverWrapper(observer, this.mCurrentVersion.get()));
    }

    @Override
    public void observeForever(@NonNull Observer<? super T> observer) {
        super.observeForever(this.createObserverWrapper(observer, this.mCurrentVersion.get()));
    }

    public void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        super.observe(owner, this.createObserverWrapper(observer, -1));
    }

    public void observeStickyForever(@NonNull Observer<? super T> observer) {
        super.observeForever(this.createObserverWrapper(observer, -1));
    }

    @Override
    protected void setValue(T value) {
        this.mCurrentVersion.getAndIncrement();
        super.setValue(value);
    }

    @Override
    public void removeObserver(@NonNull Observer<? super T> observer) {
        if (observer.getClass().isAssignableFrom(ObserverWrapper.class)) {
            super.removeObserver(observer);
        } else {
            super.removeObserver(this.createObserverWrapper(observer, -1));
        }
    }

    private ObserverWrapper createObserverWrapper(@NonNull Observer<? super T> observer, int version) {
        return new ObserverWrapper(observer, version);
    }

    public void clear() {
        super.setValue(null);
    }

    class ObserverWrapper implements Observer<T> {
        private final Observer<? super T> mObserver;
        private final int mVersion;

        public ObserverWrapper(@NonNull Observer<? super T> observer, int version) {
            this.mObserver = observer;
            this.mVersion = version;
        }

        @Override
        public void onChanged(T t) {
            if (ProtectedUnPeekLiveData.this.mCurrentVersion.get() > this.mVersion && (t != null || ProtectedUnPeekLiveData.this.isAllowNullValue)) {
                this.mObserver.onChanged(t);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                ObserverWrapper that = (ObserverWrapper)o;
                return Objects.equals(this.mObserver, that.mObserver);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.mObserver);
        }
    }
}