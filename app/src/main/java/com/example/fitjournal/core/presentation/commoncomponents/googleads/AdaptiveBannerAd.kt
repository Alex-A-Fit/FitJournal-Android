package com.example.fitjournal.core.presentation.commoncomponents.googleads

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdaptiveBannerAd(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val windowMetrics: WindowMetrics = WindowMetricsCalculator
                .getOrCreate()
                .computeCurrentWindowMetrics(context)
            val bounds = windowMetrics.bounds

            var adWidthPixels: Float = context.resources.displayMetrics.widthPixels.toFloat()

            if (adWidthPixels == 0f) {
                adWidthPixels = bounds.width().toFloat()
            }

            val density: Float = context.resources.displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()

            AdView(context).apply {
                setAdSize(
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        adWidth
                    )
                )
                // not actual prod id
                adUnitId = "ca-app-pub-3940256099942544/9214589741"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
