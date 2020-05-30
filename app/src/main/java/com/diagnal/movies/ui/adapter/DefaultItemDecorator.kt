package com.diagnal.movies.ui.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DefaultItemDecorator(private val horizontalSpacing: Int, private val verticalSpacing: Int, private val spanCount: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        when {
            parent.getChildLayoutPosition(view) % spanCount == spanCount - 1 -> {
                outRect.left = horizontalSpacing / 2
                outRect.right = horizontalSpacing
            }
            parent.getChildLayoutPosition(view) % spanCount == 0 -> {
                outRect.left = horizontalSpacing
                outRect.right = horizontalSpacing / 2
            }
            parent.getChildLayoutPosition(view) % spanCount < spanCount -> {
                outRect.left = horizontalSpacing / 2
                outRect.right = horizontalSpacing / 2
            }
        }
        //Vertical Gap
        if (parent.getChildLayoutPosition(view) < spanCount) {
            outRect.top = verticalSpacing
        }
        outRect.bottom = verticalSpacing
    }
}