package com.example.lemonade

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap.Companion.Square
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LemonadeTheme {
                makeLemonade()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun makeLemonade() {
    process(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun process(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
    val imageResource = when (result) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    val action = when (result) {
        1 -> R.string.tapTree
        2 -> R.string.tapLemon
        3 -> R.string.tapLemonade
        else -> R.string.tapGlass
    }
    var clickCount by remember { mutableStateOf(1) }
    var rand by remember { mutableStateOf(0) }

    if (result == 2 && rand == 0) {
        // Set rand only when transitioning to the lemon image
        rand = (2..4).random()
    }

    Box (
        Modifier
            .background(color = Color.Yellow)
            .fillMaxWidth()
    ){
        Text(
            text = "",
            modifier = Modifier
                .padding(
                    start = 165.dp,
                    top = 90.dp
                ),
        )
        Text(
            text = "Lemonade",
            modifier = Modifier
                .padding(
                    start = 150.dp,
                    top = 70.dp
                ),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            fontSize = 18.sp
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                if (result == 4) {
                    result = 1
                } else if (result == 2) {
                    if (clickCount == rand) {
                        result += 1
                        clickCount = 1
                        rand = 0 // Reset rand after transitioning to the next image
                    } else {
                        clickCount += 1
                    }
                } else {
                    result += 1
                }
            }
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(action),
            fontSize = 16.sp
        )
        if (result == 2 && rand != 0) {
            val squeezesRequired = rand
            Text(
                text = "Squeezes required for lemonade: $squeezesRequired",
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
