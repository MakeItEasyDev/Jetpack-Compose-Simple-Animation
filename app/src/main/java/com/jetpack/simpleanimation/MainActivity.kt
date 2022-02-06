package com.jetpack.simpleanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.simpleanimation.ui.theme.SimpleAnimationTheme
import com.jetpack.simpleanimation.ui.theme.Teal200
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleAnimationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Simple Animation",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    ) {
                        SimpleAnimation()
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleAnimation() {
    val dots = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) }
    )

    val waves = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) }
    )
    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(4000, easing = FastOutLinearInEasing),
        repeatMode = RepeatMode.Restart
    )

    waves.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 1000L)
            animatable.animateTo(
                targetValue = 1f, animationSpec = animationSpec
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        dots.forEachIndexed { index, animatable ->
            LaunchedEffect(animatable) {
                delay(index * 100L)
                animatable.animateTo(
                    targetValue = 1f, animationSpec = infiniteRepeatable(
                        animation = keyframes {
                            durationMillis = 2000
                            0.0f at 0 with LinearOutSlowInEasing
                            1.0f at 200 with LinearOutSlowInEasing
                            0.0f at 400 with LinearOutSlowInEasing
                            0.0f at 2000
                        },
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
        }

        val wavesDys = waves.map { it.value }
        val dys = dots.map { it.value }
        val travelDistance = with(LocalDensity.current) { 15.dp.toPx() }

        Row(
            modifier = Modifier
                .align(CenterHorizontally)
        ) {
            dys.forEachIndexed { index, dy ->
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .graphicsLayer {
                            translationY = -dy * travelDistance
                        }
                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color = Teal200, shape = CircleShape))
                }

                if (index != dys.size - 1) {
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        Box(
            modifier = Modifier
                .align(CenterHorizontally)
        ) {
            wavesDys.forEach { dy ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Center)
                        .graphicsLayer {
                            scaleX = dy * 4 + 1
                            scaleY = dy * 4 + 1
                            alpha = 1 - dy
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Teal200, shape = CircleShape)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .align(Center)
                    .background(color = Teal200, shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_mic_24),
                    contentDescription = "Audio",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Center)
                )
            }
        }
    }
}





















