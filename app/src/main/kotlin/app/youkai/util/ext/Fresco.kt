package app.youkai.util.ext

import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.graphics.Palette
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.BasePostprocessor
import com.facebook.imagepipeline.request.ImageRequestBuilder

/**
 * Fresco extensions
 */

/**
 * Builds a new DraweeController with a new ImageRequest with a new Postprocessor to obtain a Bitmap
 * of the image that is loaded. The bitmap is then used to obtain a Palette instance. The resulting
 * Palette instance is passed to tha lambda parameter.
 *
 * If the resulting bitmap is null, the lambda expression will have a null argument.
 */
fun SimpleDraweeView.loadWithPalette(url: String?, listener: (Palette?) -> Unit) {
    controller = Fresco.newDraweeControllerBuilder()
            .setImageRequest(
                    ImageRequestBuilder.newBuilderWithSource(Uri.parse(url ?: ""))
                            .setPostprocessor(object : BasePostprocessor() {
                                override fun process(bitmap: Bitmap?) {
                                    if (bitmap != null) {
                                        Palette.from(bitmap).generate {
                                            listener(it)
                                        }
                                    } else {
                                        listener(null)
                                    }
                                }
                            })
                            .build()
            )
            .build()
}

/**
 * Loads an image with a new controller that automatically plays animations.
 */
fun SimpleDraweeView.loadAnimated(url: String?) {
    controller = Fresco.newDraweeControllerBuilder()
            .setUri(url)
            .setAutoPlayAnimations(true)
            .build()
}
