package com.example.animatestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animatestate.ui.theme.AnimateStateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateStateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MotionDemo()
                }
            }
        }
    }
}

enum class BoxColor {
    Red, Magenta
}

enum class BoxPosition {
    Start, End
}

@Composable
fun RotationDemo() {
    var rotated by remember { mutableStateOf(false) }

    val angle by animateFloatAsState(
        targetValue = if (rotated) 360f else 0f,
        animationSpec = tween(durationMillis = 2500, easing = LinearEasing),
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "fan",
            colorFilter = ColorFilter.tint(Color.Red),
            modifier = Modifier
                .rotate(angle)
                .padding(10.dp)
                .size(300.dp)
        )
        Button(
            onClick = { rotated = !rotated },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Rotate Propeller")
        }
    }
}

@Composable
fun ColorChangeDemo() {
    var colorState by remember {
        mutableStateOf(BoxColor.Red)
    }
    val animatedColor by animateColorAsState(
        targetValue = when (colorState) {
            BoxColor.Red -> Color.Red
            BoxColor.Magenta -> Color.Magenta
        },
        animationSpec = tween(2000)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .size(200.dp)
                .background(animatedColor)
        )
        Button(
            onClick = {
                colorState = when ( colorState) {
                    BoxColor.Red -> BoxColor.Magenta
                    BoxColor.Magenta -> BoxColor.Red
                }
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text("Change Color")
        }
    }
}

@Composable
fun MotionDemo() {
    val screenWidth = (LocalConfiguration.current.screenWidthDp.dp)
    var boxState by remember {
        mutableStateOf(BoxPosition.Start)
    }
    val boxSideLength = 70.dp
    val animatedColor: Color by animateColorAsState(
        targetValue = when(boxState) {
            BoxPosition.Start -> Color.Red
            BoxPosition.End -> Color.Magenta
        },
        animationSpec = tween(1000)
    )
    val animatedOffset by animateDpAsState(
        targetValue = when (boxState) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - boxSideLength
        },
        animationSpec = tween(1000)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = 20.dp)
                .size(boxSideLength)
                .background(animatedColor)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                boxState = when ( boxState) {
                    BoxPosition.Start -> BoxPosition.End
                    BoxPosition.End -> BoxPosition.Start
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Move Box")
        }
    }
}

@Composable
fun TransitionDemo() {
    var boxState by remember { mutableStateOf(BoxPosition.Start) }
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val boxSideLength = 70.dp
    val transition = updateTransition(
        targetState = boxState,
        label = "Color and Motion"
    )
    val animatedColor: Color by transition.animateColor(
        transitionSpec = {
            tween(4000)
        }
    ) { state ->
        when(state) {
            BoxPosition.Start -> Color.Red
            BoxPosition.End -> Color.Magenta
        }
    }

    val animatedOffset: Dp by transition.animateDp(
        transitionSpec = {
            tween(4000)
        }
    ) { state ->
        when(state) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - boxSideLength
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = 20.dp)
                .size(boxSideLength)
                .background(animatedColor)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                boxState = when ( boxState) {
                    BoxPosition.Start -> BoxPosition.End
                    BoxPosition.End -> BoxPosition.Start
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Start Animation")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimateStateTheme {
        RotationDemo()
    }
}

@Preview(showBackground = true)
@Composable
fun ColorChangePreview() {
    AnimateStateTheme {
        ColorChangeDemo()
    }
}

@Preview(showBackground = true)
@Composable
fun MotionDemoPreview() {
    AnimateStateTheme {
        MotionDemo()
    }
}

@Preview(showBackground = true)
@Composable
fun TransitionDemoPreview() {
    AnimateStateTheme {
        TransitionDemo()
    }
}