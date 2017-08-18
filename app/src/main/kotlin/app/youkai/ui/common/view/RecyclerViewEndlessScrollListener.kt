package app.youkai.ui.common.view

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * A RecyclerView scroll listener that watches for the end of content and invokes a given lambda
 * function.
 */
class RecyclerViewEndlessScrollListener(
        private val layoutManager: RecyclerView.LayoutManager,
        private val loadMore: () -> Unit
) : RecyclerView.OnScrollListener() {
    private var firstVisibleItemPosition = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = layoutManager.childCount;
            totalItemCount = layoutManager.itemCount;
            firstVisibleItemPosition = when (layoutManager) {
                is LinearLayoutManager -> layoutManager.findFirstVisibleItemPosition()
                is GridLayoutManager -> layoutManager.findFirstVisibleItemPosition()
                else -> 0
            }

            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                loadMore.invoke()
            }
        }
    }
}