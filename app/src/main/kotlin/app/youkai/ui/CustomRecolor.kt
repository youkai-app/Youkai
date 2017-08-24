/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Copied from TransitionsEverywhere library.
 */

package app.youkai.ui

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Property
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.transitionseverywhere.Transition
import com.transitionseverywhere.TransitionUtils
import com.transitionseverywhere.TransitionValues

import com.transitionseverywhere.utils.IntProperty

/**
 * This transition tracks changes during scene changes to the
 * [background][View.setBackground]
 * property of its target views (when the background is a
 * [ColorDrawable], as well as the
 * [ color][TextView.setTextColor] of the text for target TextViews. If the color changes between
 * scenes, the color change is animated.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class CustomRecolor : Transition {

    constructor() {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    /**
     * Edited
     */
    private fun captureValues(transitionValues: TransitionValues) {
        // custom code for RatingBar support
        if (transitionValues.view is RatingBar) {
            transitionValues.values.put(PROPNAME_BACKGROUND,
                    (transitionValues.view as RatingBar).indeterminateDrawable)
        } else transitionValues.values.put(PROPNAME_BACKGROUND, transitionValues.view.background)

        if (transitionValues.view is TextView) {
            transitionValues.values.put(PROPNAME_TEXT_COLOR,
                    (transitionValues.view as TextView).currentTextColor)
            // supports one drawable per text view
            for (drawable in (transitionValues.view as TextView).compoundDrawables) {
                if (drawable != null) {
                    transitionValues.values.put(PROPNAME_TEXT_DRAWABLE, drawable)
                    break
                }
            }
        }
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun createAnimator(sceneRoot: ViewGroup?, startValues: TransitionValues?,
                                endValues: TransitionValues?): Animator? {
        if (startValues == null || endValues == null) {
            return null
        }
        val view = endValues.view
        val startBackground = startValues.values[PROPNAME_BACKGROUND] as Drawable?
        val endBackground = endValues.values[PROPNAME_BACKGROUND] as Drawable?
        var bgAnimator: ObjectAnimator? = null
        if (startBackground is ColorDrawable && endBackground is ColorDrawable) {
            val startColor = startBackground
            val endColor = endBackground
            if (startColor.color != endColor.color) {
                val finalColor = endColor.color
                endColor.color = startColor.color
                bgAnimator = ObjectAnimator.ofInt(endColor, COLORDRAWABLE_COLOR, startColor.color, finalColor)
                bgAnimator!!.setEvaluator(ArgbEvaluator())
            }
        }
        var textColorAnimator: ObjectAnimator? = null
        val startTextDrawable = startValues.values[PROPNAME_TEXT_DRAWABLE] as Drawable?
        val endTextDrawable = endValues.values[PROPNAME_TEXT_DRAWABLE] as Drawable?
        var textDrawableAnimator: ObjectAnimator? = null
        if (view is TextView) {
            val textView = view
            val start = startValues.values[PROPNAME_TEXT_COLOR] as Int
            val end = endValues.values[PROPNAME_TEXT_COLOR] as Int
            if (start != end) {
                textView.setTextColor(end)
                textColorAnimator = ObjectAnimator.ofInt(textView, TEXTVIEW_TEXT_COLOR, start, end)
                textColorAnimator!!.setEvaluator(ArgbEvaluator())
            }
            if (startTextDrawable is ColorDrawable && endTextDrawable is ColorDrawable) {
                val startColor = startTextDrawable
                val endColor = endTextDrawable
                if (startColor.color != endColor.color) {
                    System.out.println("trying for text drawable")
                    val finalColor = endColor.color
                    endColor.color = startColor.color
                    textDrawableAnimator = ObjectAnimator.ofInt(endColor, COLORDRAWABLE_COLOR, startColor.color, finalColor)
                    textDrawableAnimator!!.setEvaluator(ArgbEvaluator())
                }
            }
        }
        val mergedAnimators = TransitionUtils.mergeAnimators(bgAnimator, textColorAnimator)
        return TransitionUtils.mergeAnimators(mergedAnimators, textDrawableAnimator)
    }

    companion object {

        private val PROPNAME_BACKGROUND = "android:recolor:background"
        private val PROPNAME_TEXT_COLOR = "android:recolor:textColor"
        private val PROPNAME_TEXT_DRAWABLE = "android:recolor:textDrawable"

        val COLORDRAWABLE_COLOR: Property<ColorDrawable, Int>?
        val TEXTVIEW_TEXT_COLOR: Property<TextView, Int>?

        init {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                TEXTVIEW_TEXT_COLOR = object : IntProperty<TextView>() {

                    override fun setValue(textView: TextView, value: Int) {
                        textView.setTextColor(value)
                    }

                    override fun get(textView: TextView?): Int? {
                        return 0
                    }

                }.optimize()
                COLORDRAWABLE_COLOR = object : IntProperty<ColorDrawable>() {
                    override fun setValue(drawable: ColorDrawable, value: Int) {
                        drawable.color = value
                    }

                    override fun get(drawable: ColorDrawable?): Int? {
                        return drawable!!.color
                    }
                }.optimize()
            } else {
                TEXTVIEW_TEXT_COLOR = null
                COLORDRAWABLE_COLOR = null
            }
        }
    }

}