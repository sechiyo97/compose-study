package com.example.animatevisibility

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animatevisibility.ui.theme.AnimateVisibilityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateVisibilityTheme {
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    var boxVisible by remember { mutableStateOf(true) }

    val onClick = { newState : Boolean ->
        boxVisible = newState
    }

    // 자동실행
//    val state = remember { MutableTransitionState(false) }
//    state.apply { targetState = true }

    Column(
        Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Crossfade(targetState = boxVisible, animationSpec = tween(1000)) { visible ->
                when(visible) {
                    false -> CustomButton(text = "Hide", targetState = true, onClick = onClick, bgColor = Color.Red)
                    true -> CustomButton(text = "Show", targetState = false, onClick = onClick, bgColor = Color.Magenta)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        AnimatedVisibility(
            visible = boxVisible,
            enter =  fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000))
        ) {
            Box(
                modifier = Modifier
                    .size(height = 200.dp, width = 200.dp)
                    .background(Color.Blue)
            )
        }
    }
}

@Composable
fun CustomButton(text: String, targetState: Boolean, onClick: (Boolean) -> Unit, bgColor: Color = Color.Blue) {
    Button(
        onClick = {onClick(targetState)},
        colors = ButtonDefaults.buttonColors(backgroundColor = bgColor, contentColor = Color.White)) {
        Text(text)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    AnimateVisibilityTheme {
        MainScreen()
    }
}