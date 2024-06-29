package util

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter

/**
 * @Author: longkd
 * @Since: 11:59 - 29/06/2024
 */
class DoubleConverter : TwoWayConverter<Double, AnimationVector1D> {
    override val convertFromVector: (AnimationVector1D) -> Double = {
        it.value.toDouble()
    }

    override val convertToVector: (Double) -> AnimationVector1D = {
        AnimationVector1D(it.toFloat())
    }
}