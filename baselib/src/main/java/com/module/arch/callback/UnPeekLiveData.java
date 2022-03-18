package com.module.arch.callback;

/**
 * 拷贝 —> https://github.com/KunMinX/UnPeek-LiveData 里的源码
 *
 * @author bd
 * @date 2021/10/8
 */
public class UnPeekLiveData<T> extends ProtectedUnPeekLiveData<T> {

    public UnPeekLiveData() {
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }

    @Override
    public void postValue(T value) {
        super.postValue(value);
    }

    public static class Builder<T> {
        private boolean isAllowNullValue;

        public Builder() {
        }

        public Builder<T> setAllowNullValue(boolean allowNullValue) {
            this.isAllowNullValue = allowNullValue;
            return this;
        }

        public UnPeekLiveData<T> create() {
            UnPeekLiveData<T> liveData = new UnPeekLiveData<>();
            liveData.isAllowNullValue = this.isAllowNullValue;
            return liveData;
        }
    }
}