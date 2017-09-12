package app.youkai.ui.feature.library_update

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Rect
import android.os.Build
import android.support.annotation.ColorRes
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.TextView
import app.youkai.R
import app.youkai.progressview.ProgressView
import app.youkai.ui.CustomRecolor
import app.youkai.util.ext.editText
import app.youkai.util.ext.getColorCompat
import app.youkai.util.ext.maxView
import app.youkai.util.ext.setStatefulTintCompat
import com.transitionseverywhere.Transition
import com.transitionseverywhere.TransitionManager
import com.transitionseverywhere.TransitionSet
import kotlinx.android.synthetic.main.library_update.view.*
import kotlinx.android.synthetic.main.library_update_progress_anime.view.*
import kotlinx.android.synthetic.main.library_update_progress_manga.view.*

class AestheticsDelegate(private val rootView: View, private val context: Context) {
    private val isLollipopOrGreater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    private val resources: Resources = context.resources

    private val titleColorLight = R.color.bottom_sheet_title_light
    private val titleColorDark = R.color.bottom_sheet_title_dark
    private val labelColorLight = R.color.bottom_sheet_label_light
    private val labelColorDark = R.color.bottom_sheet_label_dark
    private val itemColorLight = R.color.bottom_sheet_item_light
    private val itemColorDark = R.color.bottom_sheet_item_dark

    private var privacyRippleInset: Float? = null
    private var privacyAnimator: Animator? = null
    private val animationDuration: Long = 320

    @SuppressLint("NewApi")
    fun  setPrivateBackground(isPrivate: Boolean) {
        val privacySwitch = rootView.privacySwitch
        val privateBackground = rootView.privateBackground

        if (privacyAnimator != null && privacyAnimator!!.isRunning) privacyAnimator!!.end()

        if (isLollipopOrGreater) {
            if (privacyRippleInset == null) privacyRippleInset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)
            val startX: Int = (privacySwitch.right - privacyRippleInset!!).toInt()
            val startY: Int = (privacySwitch.bottom + privacySwitch.top).div(2)
            val startRadius = 0
            val endRadius = Math.hypot(privateBackground.width.toDouble(), privateBackground.height.toDouble())
            // set animator to expand or contract depending on the resultant state
            privacyAnimator = ViewAnimationUtils.createCircularReveal(privateBackground, startX, startY,
                    if (isPrivate) startRadius.toFloat() else endRadius.toFloat(),
                    if (isPrivate) endRadius.toFloat() else startRadius.toFloat())
            privacyAnimator!!.duration = animationDuration
        }

        val epicentreRect = Rect()
        privacySwitch.getGlobalVisibleRect(epicentreRect)

        val changeColorsWithEpicenter = TransitionSet()
                .addTransition(CustomRecolor().setDuration(animationDuration))
                .setEpicenterCallback(object: Transition.EpicenterCallback() {
                    override fun onGetEpicenter(transition: Transition?): Rect {
                        return epicentreRect
                    }
                })
                .excludeTarget(privateBackground, true)
                .setDuration(animationDuration)

        TransitionManager.beginDelayedTransition(rootView.libraryUpdateRelativeLayout, changeColorsWithEpicenter)

        // set background and views to match privacy option
        if (!isPrivate) {
            // if going from private to public (circle getting smaller)
            if (isLollipopOrGreater) {
                privacyAnimator!!.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        privateBackground.visibility = View.INVISIBLE
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        super.onAnimationCancel(animation)
                        privateBackground.visibility = View.INVISIBLE
                    }
                })
            } else {
                privateBackground.visibility = View.INVISIBLE
            }
            applyLightColors()
            // set colors
        } else {
            privateBackground.visibility = View.VISIBLE
            applyDarkColors()
        }

        // start the animation
        if (isLollipopOrGreater) privacyAnimator!!.start()
    }

    @SuppressLint("NewApi")
    private fun setColors(
            @ColorRes titleColorRes: Int,
            @ColorRes labelColorRes: Int,
            @ColorRes itemColorRes: Int,
            @ColorRes spinnerBackgroundColorRes: Int,
            @ColorRes buttonDrawableColorRes: Int,
            @ColorRes editTextBackgroundColorRes: Int) {
        val titleColor = getColor(titleColorRes)
        val labelColor = getColor(labelColorRes)
        val itemColor = getColor(itemColorRes)

        rootView.title.setTextColor(titleColor)
        rootView.privacySwitch.setTextColor(labelColor)
        rootView.statusLabel.setTextColor(labelColor)
        setSpinnerSelectedColors(itemColorRes, buttonDrawableColorRes, spinnerBackgroundColorRes)
        rootView.progressLabel.setTextColor(labelColor)
        if (rootView.progressContainer.episodesProgressView != null) {
            setProgressViewColors(rootView.progressContainer.episodesProgressView, itemColorRes, editTextBackgroundColorRes)
        } else if (rootView.progressContainer.chaptersProgressView != null && rootView.progressContainer.volumesProgressView != null) {
            setProgressViewColors(rootView.progressContainer.chaptersProgressView, itemColorRes, editTextBackgroundColorRes)
            setProgressViewColors(rootView.progressContainer.volumesProgressView, itemColorRes, editTextBackgroundColorRes)
        }
        rootView.reconsumedLabel.setTextColor(labelColor)
        setProgressViewColors(rootView.reconsumedProgressView, itemColorRes, editTextBackgroundColorRes)
        rootView.ratingLabel.setTextColor(labelColor)
        rootView.ratingBar.progressBackgroundTintList = ColorStateList.valueOf(titleColor)
        rootView.ratingBar.indeterminateTintList = ColorStateList.valueOf(titleColor)
        rootView.notesLabel.setTextColor(labelColor)
        rootView.notesInputEdit.setHintTextColor(titleColor)
        rootView.notesInputEdit.setTextColor(itemColor)
        rootView.removeButton.setTextColor(titleColor)
        rootView.removeButton.compoundDrawables.forEach {
            it?.setStatefulTintCompat(resources, buttonDrawableColorRes)
        }
        rootView.notesInputEdit.background.setStatefulTintCompat(resources, editTextBackgroundColorRes)
        if (rootView.progressContainer.chapters != null && rootView.progressContainer.volumes != null ) {
            rootView.progressContainer.chapters.setTextColor(labelColor)
            rootView.progressContainer.volumes.setTextColor(labelColor)
        }
    }

    /**
     * [backgroundColorRes] is currently unused as there is no easy way to set the text color on the dropdown.
     */
    private fun setSpinnerSelectedColors(@ColorRes textColorRes: Int, @ColorRes dropdownColorRes: Int, @ColorRes backgroundColorRes: Int) {
        val statusText = rootView.statusSpinner.getChildAt(0) as TextView?
        statusText?.setTextColor(getColor(textColorRes))
        if (statusText != null) rootView.statusSpinner.background.setStatefulTintCompat(resources, dropdownColorRes)
        //retintDrawable(statusSpinner.popupBackground, backgroundColorRes)
    }

    fun setSpinnerSelectedDark() = setSpinnerSelectedColors(itemColorDark, R.color.library_update_button_drawable_dark, R.color.dropdown_dark)

    fun setSpinnerSelectedLight() = setSpinnerSelectedColors(itemColorLight, R.color.library_update_button_drawable_light, R.color.dropdown_light)

    fun setProgressViewColors(progressView: ProgressView, @ColorRes textColorRes: Int, @ColorRes editTextBackgroundColorRes: Int) {
        val textColor = getColor(textColorRes)
        val editText = progressView.editText()
        editText?.setTextColor(textColor)
        editText?.setHintTextColor(textColor)
        editText?.background?.setStatefulTintCompat(resources, editTextBackgroundColorRes)
        progressView.maxView()?.setTextColor(textColor)
    }

    fun applyLightColors() = setColors(
            titleColorLight,
            labelColorLight,
            itemColorLight,
            R.color.dropdown_light,
            R.color.library_update_button_drawable_light,
            R.color.library_update_edittext_light
    )

    fun applyDarkColors() = setColors(
            titleColorDark,
            labelColorDark,
            itemColorDark,
            R.color.dropdown_dark,
            R.color.library_update_button_drawable_dark,
            R.color.library_update_edittext_dark
    )

    private fun getColor(@ColorRes colorRes: Int) = context.getColorCompat(colorRes)

}