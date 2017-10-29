package app.youkai.ui.common.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import app.youkai.R
import app.youkai.data.models.Character
import app.youkai.util.ext.getLayoutInflater
import app.youkai.util.ext.toPx
import kotlinx.android.synthetic.main.view_media_characters.view.*

/**
 * A custom view for easy and simple implementation of media page characters.
 *
 * See: https://i.imgur.com/0xzxF4v.png, https://i.imgur.com/nFkpAEl.png
 */
class MediaCharactersView : LinearLayout {
    var clickListener: (position: Int) -> Unit = {}

    constructor(context: Context) : super(context) {
        init(context, null, null, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, null, null)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, null)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int?, defStyleRes: Int?) {
        /* Inflate the layout */
        context.getLayoutInflater().inflate(R.layout.view_media_characters, this, true)

        /* Set root layout attributes */
        orientation = HORIZONTAL

        /* Click listeners */
        char1Container.setOnClickListener {
            clickListener.invoke(0)
        }
        char2Container.setOnClickListener {
            clickListener.invoke(1)
        }
        char3Container.setOnClickListener {
            clickListener.invoke(2)
        }
        char4Container.setOnClickListener {
            clickListener.invoke(3)
        }
        char5Container.setOnClickListener {
            clickListener.invoke(4)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth

        if (width == 0) return

        // TODO: Get back and fix this
        if (isTablet()) {
            val item = width / 5 - 4 * 8.toPx(context)
            setDimensions(item, item, item, item, item)
        } else {
            val first = width / 2 - 4.toPx(context)
            val rest = first / 2 - 4.toPx(context)
            setDimensions(first, rest, rest, rest, rest)
        }
    }

    /**
     * Sets given characters into the tiles.
     */
    fun setCharacters(chars: List<Character?>) {
        for (i in chars.indices) {
            when (i) {
                0 -> char1.setImageURI(chars[i]?.image?.original ?: "")
                1 -> char2.setImageURI(chars[i]?.image?.original ?: "")
                2 -> char3.setImageURI(chars[i]?.image?.original ?: "")
                3 -> char4.setImageURI(chars[i]?.image?.original ?: "")
                4 -> char5.setImageURI(chars[i]?.image?.original ?: "")
            }
        }
    }

    /**
     * Sets the more tile count.
     */
    @SuppressLint("SetTextI18n")
    fun setMoreCount(count: Int) {
        if (count != 0) {
            rootView.moreCount.text = "+$count"
            rootView.moreCount.visibility = VISIBLE
            rootView.char5.alpha = .3f
        } else {
            rootView.char5.alpha = 1f
            rootView.moreCount.visibility = GONE
        }
    }

    /**
     * Shows or hides the more count tile (5th tile).
     */
    private fun showMoreTile(show: Boolean = true) {
        rootView.char5Container.visibility = if (show) VISIBLE else GONE
    }

    /**
     * Sets given dp values as width and height values for the character tiles.
     */
    private fun setDimensions(char1: Int, char2: Int, char3: Int, char4: Int, char5: Int) {
        rootView.char1.layoutParams.width = char1
        rootView.char1.layoutParams.height = char1
        rootView.char2.layoutParams.width = char2
        rootView.char2.layoutParams.height = char2
        rootView.char3.layoutParams.width = char3
        rootView.char3.layoutParams.height = char3
        rootView.char4.layoutParams.width = char4
        rootView.char4.layoutParams.height = char4
        rootView.char5Container.layoutParams.width = char5
        rootView.char5Container.layoutParams.height = char5
    }

    /**
     * Checks whether the current layout of this view is in tablet mode.
     *
     * tabletIndicator is an invisible view which only exists in the sw700dp layout, so will be null
     * on mobile.
     */
    private fun isTablet(): Boolean = rootView.tabletIndicator != null

    interface ClickListener {
        fun onCharacterClick(position: Int)
    }
}