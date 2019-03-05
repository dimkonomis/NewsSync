package com.dk.newssync.presentation.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

/**
 * http://blog.davidmedenjak.com/android/2015/11/10/recyclerview-with-decorations-basic-guide.html
 */

class CustomDividerItemDecoration(context: Context?, @ColorInt color: Int?, heightDp: Float): RecyclerView.ItemDecoration() {

    private val mPaint: Paint = Paint()

    init {
        mPaint.color = color ?: 0
        val thickness = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            heightDp, context?.resources?.displayMetrics
        )
        mPaint.strokeWidth = thickness
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val params = view.layoutParams as RecyclerView.LayoutParams

        // we want to retrieve the position in the list
        val position = params.getViewAdapterPosition()

        // and add a separator to any view but the last one
        if (position < state.getItemCount() - 1) {
            outRect.set(0, 0, 0, mPaint.strokeWidth.toInt()) // left, top, right, bottom
        } else {
            outRect.setEmpty() // 0, 0, 0, 0
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // we set the stroke width before, so as to correctly draw the line we have to offset by width / 2
        val offset = (mPaint.strokeWidth / 2).toInt()

        // this will iterate over every visible view
        for (i in 0 until parent.getChildCount()) {
            // get the view
            val view = parent.getChildAt(i)
            val params = view.getLayoutParams() as RecyclerView.LayoutParams

            // get the position
            val position = params.getViewAdapterPosition()

            // and finally draw the separator
            if (position < state.getItemCount() - 1) {
                c.drawLine(
                    view.getLeft().toFloat(),
                    (view.getBottom() + offset).toFloat(),
                    view.getRight().toFloat(),
                    (view.getBottom() + offset).toFloat(),
                    mPaint
                )
            }
        }
    }
}