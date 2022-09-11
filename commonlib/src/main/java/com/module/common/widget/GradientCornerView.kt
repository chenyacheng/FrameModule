package com.module.common.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue

/**
 * @author BD
 * @date 2022/6/14 14:05
 */
class GradientCornerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0,
) : androidx.appcompat.widget.AppCompatTextView(
    context, attrs, def
) {
    // 外圆角
    private var outRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics)
    // 内圆角
    private var innerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics)
    private var borderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics)
    private val outPath = Path()

    // 是否立即显示
    var showBorder = true
        set(value) {
            field = value
            invalidate()
        }

    // 设置画笔
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private val rect = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 设置区域区域
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
        // 添加渐变顶部 -> 底部渐变
        val a = 0f
        paint.shader = LinearGradient(a, a, a, rect.bottom,
            // 支持多个颜色值
            intArrayOf(
                Color.parseColor("#EE315F"),
                Color.parseColor("#0EB4E1")
            ),null, Shader.TileMode.CLAMP
        )

        val innerPath = Path()
        // 添加外圆角矩形
        outPath.addRoundRect(rect, outRadius, outRadius, Path.Direction.CCW)
        // 缩小矩形区域
        rect.inset(borderWidth, borderWidth)
        // 添加内圆角矩形
        innerPath.addRoundRect(rect, innerRadius, innerRadius, Path.Direction.CCW)
        // 通过path的op操作, 用外圆角矩形区域-内圆角矩形区域 = 圆角矩形环区域
        outPath.op(innerPath, Path.Op.DIFFERENCE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (showBorder) {
            canvas.drawPath(outPath, paint)
        }
    }
}