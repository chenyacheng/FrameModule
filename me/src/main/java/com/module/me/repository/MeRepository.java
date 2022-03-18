package com.module.me.repository;

/**
 * @author BD
 */
public class MeRepository {

    public static MeRepository getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }

    private enum SingletonEnum {
        // 枚举对象
        INSTANCE;
        private final MeRepository singleton;

        SingletonEnum() {
            singleton = new MeRepository();
        }

        MeRepository getInstance() {
            return singleton;
        }
    }
}
