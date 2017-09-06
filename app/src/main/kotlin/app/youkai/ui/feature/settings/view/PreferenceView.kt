package app.youkai.ui.feature.settings.view

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Parcelable
import android.support.annotation.AttrRes
import android.support.annotation.StyleRes
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.RelativeLayout
import android.widget.TextView
import app.youkai.R

class PreferenceView : RelativeLayout {

    private var checkableType: Int = CHECKABLE_TYPE_NONE
    private var initialDescriptionText: CharSequence? = null
    private var initialDisabledDescriptionText: CharSequence? = null
    private var initialTitleText: CharSequence? = null
    private var startEnabled: Boolean = true

    private var checkableView: CompoundButton? = null
    private var descriptionView: TextView? = null
    private var titleView: TextView? = null


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        parseAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        parseAttributes(attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int,
                @StyleRes defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        parseAttributes(attrs)
    }

    var description: CharSequence?
        get() = descriptionView?.text
        set(description) { descriptionView?.text = description }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        val layoutResId = when (checkableType) {
            CHECKABLE_TYPE_NONE -> {
                if (initialDescriptionText.isNullOrBlank()) {
                    R.layout.view_preference
                } else {
                    R.layout.view_preference_with_description
                }
            }
            CHECKABLE_TYPE_CHECK_BOX -> {
                if (initialDescriptionText.isNullOrBlank()) {
                    R.layout.view_checkbox_preference
                } else {
                    R.layout.view_checkbox_preference_with_description
                }
            }
            CHECKABLE_TYPE_SWITCH_COMPAT -> {
                if (initialDescriptionText.isNullOrBlank()) {
                    R.layout.view_switchcompat_preference
                } else {
                    R.layout.view_switchcompat_preference_with_description
                }
            }
            else -> {
                throw RuntimeException("illegal checkableType ($checkableType)")
            }
        }

        LayoutInflater.from(context).inflate(layoutResId, this)
        checkableView = findViewById(R.id.checkableView)
        descriptionView = findViewById(R.id.descriptionView)
        titleView = findViewById(R.id.titleView)

        title = initialTitleText
        description = if (initialDisabledDescriptionText.isNullOrBlank())
            initialDescriptionText else initialDisabledDescriptionText
        isEnabled = startEnabled
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.PreferenceView)
        checkableType = ta.getInt(R.styleable.PreferenceView_preferenceCheckableType, CHECKABLE_TYPE_NONE)
        initialDescriptionText = ta.getText(R.styleable.PreferenceView_preferenceDescriptionText)
        initialDisabledDescriptionText = ta.getText(R.styleable.PreferenceView_preferenceDisabledDescriptionText)
        initialTitleText = ta.getText(R.styleable.PreferenceView_preferenceTitleText)
        startEnabled = ta.getBoolean(R.styleable.PreferenceView_preferenceStartEnabled, true)
        ta.recycle()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        checkableView?.isEnabled = enabled
        descriptionView?.isEnabled = enabled
        titleView?.isEnabled = enabled
    }

    var title: CharSequence?
        get() = titleView?.text
        set(title) { titleView?.text = title }

    var value: Boolean?
        get() = checkableView?.isChecked
        set(value) {
            if (value == true) {
                checkableView?.isChecked = true
                descriptionView?.text = initialDescriptionText
            } else {
                checkableView?.isChecked = false
                descriptionView?.text = if (initialDisabledDescriptionText.isNullOrBlank())
                    initialDescriptionText else initialDisabledDescriptionText
            }
        }

    companion object {
        private const val CHECKABLE_TYPE_NONE = -1
        private const val CHECKABLE_TYPE_CHECK_BOX = 0
        private const val CHECKABLE_TYPE_SWITCH_COMPAT = 1
    }

}
