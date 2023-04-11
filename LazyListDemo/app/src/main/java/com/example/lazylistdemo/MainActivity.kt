package com.example.lazylistdemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.lazylistdemo.ui.theme.LazyListDemoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var itemArray: Array<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        itemArray = resources.getStringArray(R.array.car_array)
        super.onCreate(savedInstanceState)
        setContent {
            LazyListDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(itemArray = itemArray as Array<out String>)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(itemArray: Array<out String>) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val displayButton = listState.firstVisibleItemIndex > 5
    val context = LocalContext.current
    val groupedItems = itemArray.groupBy { it.substringBefore(' ') }
    val onListItemClick = { text: String ->
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
    Box {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            groupedItems.forEach { (manufacturer, models) ->
                stickyHeader {
                    Text(
                        text = manufacturer,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color.Gray)
                            .padding(5.dp)
                            .fillMaxWidth()
                    )
                }
                items(models) { model ->
                    MyListItem(item = model, onItemClick = onListItemClick)
                }
            }
        }

        AnimatedVisibility(visible = displayButton, modifier = Modifier.align(Alignment.BottomCenter)) {
            OutlinedButton(
                onClick = {
                    coroutineScope.launch { listState.scrollToItem(0) }
                },
                border = BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.DarkGray
                ),
                modifier = Modifier.padding(5.dp)
            ) {
                Text(text = "Top")
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageLoader(item: String) {
    val url = "https://www.ebookfrenzy.com/book_examples/car_logos/" +
            item.substringBefore(" ") + "_logo.png"

    Image(
        painter = rememberImagePainter(url),
        contentDescription = "car image",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(75.dp)
    )
}

@Composable
fun MyListItem(item: String, onItemClick: (String) -> Unit) {
    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke(item)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ImageLoader(item = item)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val itemArray: Array<String> = arrayOf("Cadillac Eldorado", "Ford FairLane", "Plymouth Fury")
    LazyListDemoTheme {
        MainScreen(itemArray)
    }
}