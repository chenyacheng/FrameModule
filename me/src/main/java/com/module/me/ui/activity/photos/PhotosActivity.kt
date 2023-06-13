package com.module.me.ui.activity.photos

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.module.arch.base.BaseActivity
import com.module.common.constant.RouterConstant
import com.module.me.databinding.MeActivityPhotosBinding
import java.io.File

/**
 * 对拍照界面没有自定义要求，可以调起系统相机的方式快速完成拍照需求
 * 不需要读写权限进行读写操作的方案，通过 Intent 启动系统的 Activity 让用户进行操作
 *
 * @author vya
 * @date 2023/3/23 上午9:28
 */
@Route(path = RouterConstant.PATH_ME_PHOTOS_ACTIVITY)
class PhotosActivity : BaseActivity<MeActivityPhotosBinding>() {

    private var launcher: ActivityResultLauncher<Uri>? = null

    override fun getViewBinding(): MeActivityPhotosBinding {
        return MeActivityPhotosBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
    }

    override fun init() {
        var uri: Uri? = null
        launcher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
                if (it) {
                    uri?.let { it1 ->
                        val bitmap = getBitmapFromUri(it1)
                        binding.imageView.setImageBitmap(bitmap)
                    }
                }
            }
        binding.btn1.setOnClickListener {
            val picture = File(externalCacheDir?.path + "/picture/" + System.currentTimeMillis() + ".jpg")
            uri = FileProvider.getUriForFile(this, "${packageName}.fileProvider", picture)
            launcher!!.launch(uri)
        }
    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver.openFileDescriptor(uri, "r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }
}