package com.module.common.custom;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * 您不需要AppGlideModule为要生成的API 实现任何方法。只要它延伸AppGlideModule并注释，您就可以将该类留空@GlideModule。
 * AppGlideModule实现必须始终使用注释@GlideModule。如果注释不存在，则不会发现该模块，您将在日志中看到一条警告，其中Glide包含指示无法找到该模块的日志标记。
 *
 * @author bd
 * @date 2021/11/19
 */
@GlideModule
public final class MyAppGlideModule extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
