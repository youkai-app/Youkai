package app.youkai.ui.common.widget

import android.content.Context
import android.support.annotation.IntegerRes
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import app.youkai.R
import app.youkai.util.ext.getLayoutInflater
import app.youkai.util.ext.toPx

/**
 * A custom view for displaying key info in an eye-catching style with an icon.
 *
 * See: http://i.imgur.com/U1N3u2J.png
 */
class CatchyInfoView : LinearLayout {
    lateinit var iconView: ImageView
    lateinit var textView: TextView
    lateinit var chevronView: View

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
        context.getLayoutInflater().inflate(R.layout.view_catchy_info, this, true)

        /* Get view references */
        iconView = findViewById(R.id.icon) as ImageView
        textView = findViewById(R.id.text) as TextView
        chevronView = findViewById(R.id.chevron)

        /* Set root layout attributes */
        val padding = 16.toPx(context)
        setPadding(padding, padding, padding, padding)
        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        /* Get values from the layout */
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.CatchyInfoView,
                defStyleAttr ?: 0,
                defStyleRes ?: 0
        )

        val icon: Int
        val text: String
        val rounded: Boolean

        try {
            icon = a.getResourceId(R.styleable.CatchyInfoView_civ_icon, R.drawable.ic_catchy_info_view_default_icon)
            text = a.getString(R.styleable.CatchyInfoView_civ_text) ?: ""
            rounded = a.getBoolean(R.styleable.CatchyInfoView_civ_roundedCorners, false)
        } finally {
            a.recycle()
        }

        /* Use the values */
        setIcon(icon)
        setText(text)
        setRoundedCorners(rounded)

    }

    fun setIcon(@IntegerRes resource: Int) {
        iconView.setImageResource(resource)
    }

    fun setText(text: String) {
        textView.text = text
    }

    fun setRoundedCorners(rounded: Boolean = true) {
        rootView.setBackgroundResource(
                if (rounded) {
                    R.drawable.background_catchy_info_view_rounded
                } else {
                    R.drawable.background_catchy_info_view
                }
        )
    }

    private fun showChevron(show: Boolean = true) {
        chevronView.visibility = if (show) VISIBLE else GONE
    }

    override fun setOnClickListener(l: OnClickListener?) {
        showChevron(l != null)
        super.setOnClickListener(l)
    }
}