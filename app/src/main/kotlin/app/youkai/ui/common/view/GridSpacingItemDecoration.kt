package app.youkai.ui.common.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * ItemDecoration implementation for grid spacing.
 *
 * Taken from https://stackoverflow.com/a/30701422/984061
 */
class GridSpacingItemDecoration(val spanCount: Int, val spacing: Int, val includeEdges: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        if (includeEdges) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}