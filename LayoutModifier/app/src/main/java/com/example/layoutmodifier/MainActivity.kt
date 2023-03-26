package com.example.layoutmodifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.layoutmodifier.ui.theme.LayoutModifierTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutModifierTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.size(120.dp, 80.dp)) {
        Column {
            ColorBox(
                modifier = Modifier.exampleLayout(0f).background(Color.Blue)
            )
            ColorBox(
                modifier = Modifier.exampleLayout(0.25f).background(Color.Green)
            )
            ColorBox(
                modifier = Modifier.exampleLayout(0.5f).background(Color.Yellow)
            )
            ColorBox(
                modifier = Modifier.exampleLayout(0.25f).background(Color.Red)
            )
            ColorBox(
                modifier = Modifier.exampleLayout(0f).background(Color.Magenta)
            )
        }
    }
}

@Composable
fun ColorBox(modifier: Modifier) {
    Box(
        Modifier
            .padding(1.dp)
            .size(width = 50.dp, height = 10.dp)
            .then(modifier))
}

fun Modifier.exampleLayout(
    fraction: Float
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    val x = -(placeable.width * fraction).roundToInt()
    layout(placeable.width, placeable.height){
        placeable.placeRelative(x = x, y = 0)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LayoutModifierTheme {
        MainScreen()
    }
}