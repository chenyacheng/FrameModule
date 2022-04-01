package com.module.common.utils

import android.content.Context
import com.module.common.widget.ProgressDialog
import com.module.network.BaseRequest

/**
 * 网络请求加载框
 *
 * @author bd
 * @date 2021/10/11
 */
class ProgressDialogUtils {

    private var pd: ProgressDialog? = null

    /**
     * 显示
     */
    @JvmOverloads fun showProgress(context: Context, cancelable: Boolean = true) {
        if (pd == null) {
            pd = ProgressDialog(context)
            val message = "请稍候"
            pd!!.showProgress(message)
            pd!!.setCancelable(cancelable)
            if (cancelable) {
                pd!!.setOnCancelListener {
                    BaseRequest.getInstance().cancel()
                    hideProgress()
                }
            }
            if (!pd!!.isShowing) {
                pd!!.show()
            }
        }
    }

    /**
     * 隐藏
     */
    fun hideProgress() {
        if (pd != null) {
            pd!!.dismiss()
            pd = null
        }
    }

    private enum class SingletonEnum {
        // 枚举对象
        INSTANCE;

        val instance: ProgressDialogUtils = ProgressDialogUtils()
    }

    companion object {
        @JvmStatic
        val instance: ProgressDialogUtils
            get() = SingletonEnum.INSTANCE.instance
    }
}