package com.module.common.utils

import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import coil.target.ImageViewTarget
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.luck.picture.lib.listener.OnImageCompleteCallback
import com.luck.picture.lib.tools.MediaUtils
import com.luck.picture.lib.widget.longimage.ImageSource
import com.luck.picture.lib.widget.longimage.ImageViewState
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView
import com.module.common.R

/**
 * ImageView 图片加载
 *
 * @author BD
 * @date 2022/4/8 10:26
 */
class ImageViewDisplayUtils private constructor() {

    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun imageViewNotPlaceholderDisplay(imageView: ImageView, url: String) {
            imageView.load(url)
        }

        fun imageViewDisplay(imageView: ImageView, url: String) {
            imageView.load(url) {
                placeholder(R.drawable.default_picture)
                error(R.drawable.default_picture)
            }
        }

        fun imageViewDisplay(imageView: ImageView, url: String, roundedCorners: Float) {
            imageView.load(url) {
                placeholder(R.drawable.default_picture)
                error(R.drawable.default_picture)
                transformations(RoundedCornersTransformation(roundedCorners))
            }
        }

        fun imageViewCircleCropDisplay(imageView: ImageView, url: String) {
            imageView.load(url) {
                placeholder(R.drawable.default_picture)
                error(R.drawable.default_picture)
                transformations(CircleCropTransformation())
            }
        }

        fun imageViewCircleCropDisplay(imageView: ImageView, url: String, drawableResId: Int) {
            imageView.load(url) {
                placeholder(drawableResId)
                error(drawableResId)
                transformations(CircleCropTransformation())
            }
        }

        fun imageViewDisplay1(imageView: ImageView, url: String, drawableResId: Int) {
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.load(url) {
                placeholder(drawableResId)
                size(200)
            }
        }

        fun imageViewDisplay2(
            imageView: ImageView,
            url: String,
            drawableResId: Int,
            roundedCorners: Float
        ) {
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.load(url) {
                placeholder(drawableResId)
                size(180)
                transformations(RoundedCornersTransformation(roundedCorners))
            }
        }

        fun imageViewDisplay3(
            imageView: ImageView,
            url: String,
            longImageView: SubsamplingScaleImageView,
            callback: OnImageCompleteCallback?
        ) {
            val imageLoader = imageView.context.imageLoader
            val request = ImageRequest.Builder(imageView.context)
                .data(url)
                .target(object : ImageViewTarget(imageView) {
                    override fun onStart(placeholder: Drawable?) {
                        super.onStart(placeholder)
                        callback?.onShowLoading()
                    }

                    override fun onSuccess(result: Drawable) {
                        super.onSuccess(result)
                        callback?.onHideLoading()
                        val eqLongImage = MediaUtils.isLongImg(result.toBitmap().width, result.toBitmap().height)
                        longImageView.visibility = if (eqLongImage) View.VISIBLE else View.GONE
                        imageView.visibility = if (eqLongImage) View.GONE else View.VISIBLE
                        if (eqLongImage) {
                            // 加载长图
                            longImageView.isQuickScaleEnabled = true
                            longImageView.isZoomEnabled = true
                            longImageView.setDoubleTapZoomDuration(100)
                            longImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                            longImageView.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
                            longImageView.setImage(
                                ImageSource.cachedBitmap(result.toBitmap()),
                                ImageViewState(0f, PointF(0f, 0f), 0)
                            )
                        } else {
                            // 普通图片
                            imageView.setImageBitmap(result.toBitmap())
                        }
                    }

                    override fun onError(error: Drawable?) {
                        super.onError(error)
                        callback?.onHideLoading()
                    }
                })
                .build()
            imageLoader.enqueue(request)
        }

        fun imageViewDisplay4(
            imageView: ImageView,
            url: String
        ) {
            val imageLoader = imageView.context.imageLoader
            val request = ImageRequest.Builder(imageView.context)
                .data(url)
                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
                .target(object : ImageViewTarget(imageView) {
                    override fun onSuccess(result: Drawable) {
                        super.onSuccess(result)
                        callback?.widthAndHeight(result.toBitmap().width, result.toBitmap().height)
                    }
                })
                .build()
            imageLoader.enqueue(request)
        }

        var callback: CallbackListener? = null

        fun setCallbackListener(listener: CallbackListener) {
            callback = listener
        }

        interface CallbackListener {
            fun widthAndHeight(width: Int, height: Int)
        }
    }
}