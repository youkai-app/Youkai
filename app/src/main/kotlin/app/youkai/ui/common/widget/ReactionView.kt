package app.youkai.ui.common.widget

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import app.youkai.R
import app.youkai.util.color
import app.youkai.util.ext.getLayoutInflater
import app.youkai.util.ext.toPx

/**
 * A custom view for displaying some text with upvote button and upvotes count
 *
 * See: https://i.imgur.com/Y0Ury0a.png
 */
class ReactionView : LinearLayout {
    private lateinit var upvotesContainer: LinearLayout
    private lateinit var upvoteIcon: ImageView
    private lateinit var upvotesCount: TextView
    private lateinit var author: TextView
    private lateinit var reaction: TextView

    private var upvoted = false

    private var upvoteListener: OnUpvoteChangedListener? = null

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
        context.getLayoutInflater().inflate(R.layout.view_reaction, this, true)

        /* Get view references */
        upvotesContainer = findViewById(R.id.upvotesContainer) as LinearLayout
        upvoteIcon = findViewById(R.id.upvoteIcon) as ImageView
        upvotesCount = findViewById(R.id.upvotesCount) as TextView
        author = findViewById(R.id.author) as TextView
        reaction = findViewById(R.id.reaction) as TextView

        /* Set root layout attributes */
        val padding = 8.toPx(context)
        setPadding(0, padding, 0, padding)
        gravity = Gravity.CENTER_VERTICAL
        orientation = HORIZONTAL

        /* Set up click listener */
        upvotesContainer.setOnClickListener {
            notifyUpvoteChanged(!upvoted)
        }
    }

    fun setUpvotes(upvotes: Int) {
        upvotesCount.text = "${Math.max(0, upvotes)}"
    }

    fun setAuthor(author: String) {
        this.author.text = author
    }

    fun setReaction(text: String) {
        reaction.text = text
    }

    fun setUpvoted(upvoted: Boolean) {
        notifyUpvoteChanged(upvoted)
    }

    fun setOnUpvoteChangeListener(listener: OnUpvoteChangedListener) {
        upvoteListener = listener
    }

    private fun setUpvoteIconTint(color: Int) {
        upvoteIcon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    private fun setUpvotesCountTextColor(color: Int) {
        upvotesCount.setTextColor(color)
    }

    private fun notifyUpvoteChanged(upvoted: Boolean) {
        val accentColor = color(R.color.accent)

        if (upvoted) {
            this.upvoted = true
            setUpvoteIconTint(accentColor)
            setUpvotesCountTextColor(accentColor)
        } else {
            this.upvoted = false
            setUpvoteIconTint(Color.BLACK)
            setUpvotesCountTextColor(Color.BLACK)
        }

        upvoteListener?.onUpvoteChanged?.invoke(upvoted)
    }

    class OnUpvoteChangedListener(val onUpvoteChanged: (upvoted: Boolean) -> Unit)
}